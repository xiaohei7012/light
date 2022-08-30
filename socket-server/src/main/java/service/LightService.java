package service;

import java.nio.channels.SocketChannel;

import com.light.dao.DeviceDao;
import com.light.dao.DeviceDaoInterface;
import com.light.dao.HistoryDao;
import com.light.dao.HistoryDaoInterface;
import com.light.model.Device;

import server.LightServer;

public class LightService {
	private static DeviceDaoInterface deviceDao = new DeviceDao();
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
	}

	public void offline(String imei) {
		deviceDao.setOffline(imei);

	}
}
