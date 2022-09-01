package service;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.light.dao.DeviceDao;
import com.light.dao.DeviceDaoInterface;
import com.light.dao.GroupDao;
import com.light.dao.GroupDaoInterface;
import com.light.dao.HistoryDao;
import com.light.dao.HistoryDaoInterface;
import com.light.dao.PlanDao;
import com.light.dao.PlanDaoInterface;
import com.light.model.Device;
import com.light.model.Group;
import com.light.model.Plan;

import server.LightServer;

public class LightService {
	private static LightServer server = LightServer.getInstance();

	private static DeviceDaoInterface deviceDao = new DeviceDao();
	private static PlanDaoInterface planDao = new PlanDao();
	private static GroupDaoInterface groupDao = new GroupDao();
	private static HistoryDaoInterface historyDao = new HistoryDao();

	public final static String TURNON_INSTRUCTION = "SET";

	public void signIn(String[] dataArray, SocketChannel sc) {
		String imei = dataArray[1];
		if (dataArray.length >= 6) {
			String lon = dataArray[4];// 经度
			String lat = dataArray[5];// 纬度
			deviceDao.setOnline(imei, Double.parseDouble(lon), Double.parseDouble(lat));
		} else {
			deviceDao.setOnline(imei);
		}

		//补发，带确认的
	}

	// 补发
	public void resend(String imei) {
		Plan plan = planDao.getPlanByImei(imei);
		if (isInTime(plan)) {
			deviceDao.setPlan(imei, plan);
			sendData(imei, plan);
		}
	}

	// 补发
	public void resend4Plan(Plan plan) {
		boolean isInTime = isInTime(plan);
		List<Group> groups = groupDao.getGroupByPid(plan.getId());
		for(Group g:groups) {
			List<Device> devices = deviceDao.getByGroupId(g.getId());
			for(Device d:devices) {
				if(isInTime) {
					deviceDao.setPlan(d, plan);
					LightService.sendData(d.getImei(), plan);
				}else {
					deviceDao.turnOff(d);
					server.sendData(d.getImei(),LightService.parseInstruction(d));
				}
			}
		}
//			deviceDao.setPlan(imei, plan);
//			sendData(imei, plan);
	}

	public void uploadStatus(String[] dataArray) {
		if (dataArray.length < 11) {
			return;
		}
		String imei = dataArray[1];
		String l1 = dataArray[3];
		String l2 = dataArray[4];
		String l3 = dataArray[5];
		String l4 = dataArray[6];
		String l5 = dataArray[7];
		String l6 = dataArray[8];
		String fan = dataArray[9];
		double temp = Double.parseDouble(dataArray[10]);
		historyDao.create(imei, l1, l2, l3, l4, l5, l6, fan, temp);

		// 如果跟设备状态不一样还得改
	}

	public void confirm() {

	}

	public void manual(String[] dataArray) {
		if (dataArray.length < 11) {
			return;
		}
		String imei = dataArray[1];
		String l1 = dataArray[3];
		String l2 = dataArray[4];
		String l3 = dataArray[5];
		String l4 = dataArray[6];
		String l5 = dataArray[7];
		String l6 = dataArray[8];
		String fan = dataArray[9];
		double temp = Double.parseDouble(dataArray[10]);
		deviceDao.setStatus(imei, l1, l2, l3, l4, l5, l6, fan, temp);
	}

	public static String parseInstruction(Device device) {
		StringBuilder result = new StringBuilder();
		result.append(LightServer.HEAD);
		result.append(LightServer.SPLITWORD);
		result.append(device.getImei().toUpperCase());
		result.append(LightServer.SPLITWORD);
		result.append(TURNON_INSTRUCTION);
		result.append(LightServer.SPLITWORD);
		result.append(device.getL1());
		result.append(LightServer.SPLITWORD);
		result.append(device.getL2());
		result.append(LightServer.SPLITWORD);
		result.append(device.getL3());
		result.append(LightServer.SPLITWORD);
		result.append(device.getL4());
		result.append(LightServer.SPLITWORD);
		result.append(device.getL5());
		result.append(LightServer.SPLITWORD);
		result.append(device.getL6());
		result.append(LightServer.SPLITWORD);
		result.append(device.getFan());
		result.append(LightServer.SPLITWORD);
		result.append(LightServer.SPLITLINE);
		return result.toString();
	}

	public static void sendData(String imei, Plan plan) {
		StringBuilder result = new StringBuilder();
		result.append(LightServer.HEAD);
		result.append(LightServer.SPLITWORD);
		result.append(imei.toUpperCase());
		result.append(LightServer.SPLITWORD);
		result.append(TURNON_INSTRUCTION);
		result.append(LightServer.SPLITWORD);
		result.append(plan.getInstruction());
		result.append(LightServer.SPLITWORD);
		result.append(LightServer.SPLITLINE);
		server.sendData(imei, result.toString());
	}

	public void initStatus(String[] dataArray) {
		if (dataArray.length < 11) {
			return;
		}
		String imei = dataArray[1];
		String l1 = dataArray[3];
		String l2 = dataArray[4];
		String l3 = dataArray[5];
		String l4 = dataArray[6];
		String l5 = dataArray[7];
		String l6 = dataArray[8];
		String fan = dataArray[9];
		double temp = Double.parseDouble(dataArray[10]);
		deviceDao.setStatus(imei, l1, l2, l3, l4, l5, l6, fan, temp);
		
		resend(imei);
	}

	public void offline(String imei) {
		deviceDao.setOffline(imei);
	}

	public static String getImei(String data) {
		ObjectMapper mapper = new ObjectMapper();
		String result = "";
		try {
			JsonNode node = mapper.readTree(data);
			JsonNode imei = node.get("imei");
			result = imei.asText();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private boolean isInTime(Plan plan) {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMdd ");
		try {
			Date startTime = sdf.parse(sdfs.format(now) + plan.getStartTime() + ":00");
			Date endTime = sdf.parse(sdfs.format(now) + plan.getEndTime() + ":00");

			if (now.after(startTime) && now.before(endTime)) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return false;
	}
}
