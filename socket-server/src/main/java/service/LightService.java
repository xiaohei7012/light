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
import com.light.model.History;
import com.light.model.Instruction;
import com.light.model.Plan;

import server.LightServer;

public class LightService {
	private static LightServer server = LightServer.getInstance();

	private static DeviceDaoInterface deviceDao = new DeviceDao();
	private static PlanDaoInterface planDao = new PlanDao();
	private static GroupDaoInterface groupDao = new GroupDao();
	private static HistoryDaoInterface historyDao = new HistoryDao();

	public final static String TURN_ON_INSTRUCTION = "SET";

	public void signIn(String[] dataArray, SocketChannel sc) throws Exception {
		String imei = dataArray[1];

		// 如果没有该设备 自动添加
		autoCreate(imei);

		if (dataArray.length >= 6) {
			String lon = dataArray[4];// 经度
			String lat = dataArray[5];// 纬度
			deviceDao.setOnline(imei, Double.parseDouble(lon), Double.parseDouble(lat));
		} else {
			deviceDao.setOnline(imei);
		}

		if (dataArray.length >= 7) {// 有风速
			Double rpm = Double.parseDouble(dataArray[4]);
			deviceDao.setFanRpm(imei, rpm);
		}

		// 补发，带确认的
	}

	private void autoCreate(String imei) {
		Device device = deviceDao.getByImei(imei);
		if (device == null) {
			try {
				deviceDao.create(imei);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
		for (Group g : groups) {
			List<Device> devices = deviceDao.getByGroupId(g.getId());
			for (Device d : devices) {
				if (isInTime) {
					deviceDao.setPlan(d, plan);
					LightService.sendData(d.getImei(), plan);
				} else {
					deviceDao.turnOff(d);
					server.sendData(d.getImei(), LightService.parseInstruction(d));
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

		// 保存
		History history = new History();
		history.setDate(new Date());
		history.setCtime(new Date());
		history.setImei(dataArray[1]);
		history.setL1(dataArray[3]);
		history.setL2(dataArray[4]);
		history.setL3(dataArray[5]);
		history.setL4(dataArray[6]);
		history.setL5(dataArray[7]);
		history.setL6(dataArray[8]);
		history.setFan(dataArray[9]);
		history.setTemperature(Double.parseDouble(dataArray[10]));

		if (dataArray.length >= 12) {
			history.setRpm(dataArray[11]);
		}
		try {
			historyDao.create(history);
		} catch (Exception e) {
			e.printStackTrace();
		}

		autoCreate(dataArray[1]);

		// 如果跟设备状态不一样还得改
		deviceDao.setStatus(dataArray[1], dataArray[3], dataArray[4], dataArray[5], dataArray[6], dataArray[7],
				dataArray[8], dataArray[9], Double.parseDouble(dataArray[10]));

		Device device = deviceDao.getByImei(dataArray[1]);
		if (device != null) {
			device.setRpm(Double.parseDouble(dataArray[11]));
			device.setLastupdateTime(new Date());
			deviceDao.update(device);
		}

		// 设备寿命+5分钟 6个灯
		deviceDao.increaseUseTime(dataArray[1], dataArray[3], dataArray[4], dataArray[5], dataArray[6], dataArray[7],
				dataArray[8]);
	}

	public void confirm(String[] dataArray) {
		String imei = dataArray[1];
		LightServer.getInstance().getReceiveCache().put(imei,"ACK");
	}

	public void manual(String[] dataArray) {
		if (dataArray.length >= 1) {
			autoCreate(dataArray[1]);
		}

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
		if (dataArray.length >= 12) {
			Double rpm = Double.parseDouble(dataArray[11]);
			deviceDao.setFanRpm(imei, rpm);
		}

	}

	public static String parseInstruction(Device device) {
		StringBuilder result = new StringBuilder();
		result.append(LightServer.HEAD);
		result.append(LightServer.SPLITWORD);
		result.append(device.getImei().toUpperCase());
		result.append(LightServer.SPLITWORD);
		result.append(TURN_ON_INSTRUCTION);
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

	private static Instruction parseInstruction(String[] dataArray) throws ParseException {
		if (dataArray.length < 10) {
			throw new ParseException("转换出错", 0);
		}
		String imei = dataArray[1];
		String l1 = dataArray[3];
		String l2 = dataArray[4];
		String l3 = dataArray[5];
		String l4 = dataArray[6];
		String l5 = dataArray[7];
		String l6 = dataArray[8];
		String fan = dataArray[9];
		Instruction ins = new Instruction(imei, l1, l2, l3, l4, l5, l6, fan);
		return ins;
	}

	public static void sendData(String imei, Plan plan) {
		sendData(imei, plan.getInstruction());
	}

	// 如果是send命令，则多开一个线程序，看在3秒内有没有ACK,有ACK的话则作为返回也发送一个ACK给http服务器
	public void sendData(String[] dataArray, SocketChannel sc) throws ParseException {
		Instruction ins = parseInstruction(dataArray);
		String readySend = ins.getL1() + LightServer.SPLITWORD + ins.getL2() + LightServer.SPLITWORD + ins.getL3()
				+ LightServer.SPLITWORD + ins.getL4() + LightServer.SPLITWORD + ins.getL5() + LightServer.SPLITWORD
				+ ins.getL6() + LightServer.SPLITWORD + ins.getFan();

		sendData(ins.getImei(), readySend, sc);
	}

	public static void sendData(String imei, String instruction) {
		StringBuilder result = new StringBuilder();
		result.append(LightServer.HEAD);
		result.append(LightServer.SPLITWORD);
		result.append(imei.toUpperCase());
		result.append(LightServer.SPLITWORD);
		result.append(TURN_ON_INSTRUCTION);
		result.append(LightServer.SPLITWORD);
		result.append(instruction);
		result.append(LightServer.SPLITLINE);
		server.sendData(imei, result.toString());
	}
	
	public static void sendData(String imei, String instruction, SocketChannel sc) {
		StringBuilder result = new StringBuilder();
		result.append(LightServer.HEAD);
		result.append(LightServer.SPLITWORD);
		result.append(imei.toUpperCase());
		result.append(LightServer.SPLITWORD);
		result.append(TURN_ON_INSTRUCTION);
		result.append(LightServer.SPLITWORD);
		result.append(instruction);
		result.append(LightServer.SPLITLINE);
//		server.sendData(imei, result.toString());
		server.sendData4Ack(imei, result.toString(), sc);
	}

	public void initStatus(String[] dataArray) throws ParseException {
		if (dataArray.length >= 1) {
			autoCreate(dataArray[1]);
		}
		Instruction ins = parseInstruction(dataArray);
		Double temp = Double.parseDouble(dataArray[10]);
		deviceDao.setStatus(ins.getImei(), ins.getL1(), ins.getL2(), ins.getL3(), ins.getL4(), ins.getL5(), ins.getL6(),
				ins.getFan(), temp);

		if (dataArray.length >= 12) {
			// 转速
			Device device = deviceDao.getByImei(dataArray[1]);
			if (device != null) {
				device.setRpm(Double.parseDouble(dataArray[11]));
				deviceDao.update(device);
			}
		}

		resend(ins.getImei());
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
