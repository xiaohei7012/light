package server;

import java.nio.channels.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.light.util.Util;

import service.LightService;

public class LightServer extends SimpleServer {
	private static LightServer instance;
	private static LightService service = new LightService();

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public final static String HEAD = "YTE";
	public final static String SPLITWORD = " ";
	public final static String SPLITLINE = "\r\n";

	public static enum INSTRUCTION_TYPE {
		// 上电
		ZWXD,
		// 状态
		DATA,
		// 确认
		ACK,
		// 遥控
		IRYK,
		// 重置
		RESET,
		// 马上启动方案
		ADD,
		// 上电确认状态
		INIT,
		// 未知
		UNKNWON;
	}

	InstructionTask insTask;

	private LightServer(int port) {
		super(port);
	}

	public static void main(String[] args) {
		LightServer server = LightServer.getInstance();
		server.start();
	}

	public static LightServer getInstance() {
		if (instance == null) {
			instance = new LightServer(Util.port);
		}
		return instance;
	}

	public void start() {
		insTask = new InstructionTask();
		insTask.start();

		super.start();
	}

	@Override
	public void onConnected(SocketChannel sc) {
		logger.info("有新的连接:" + sc.socket().getInetAddress().getHostAddress());
	}

	@Override
	public void onRead(SocketChannel sc, String data) {
		try {
			String[] lineArray = data.split(SPLITLINE);
			for (String line : lineArray) {
				String[] dataArray = line.split(SPLITWORD);
				switch (getInsType(dataArray)) {
				case ZWXD:
					String imei = "";
					if(dataArray.length<=1) {
						imei = LightService.getImei(dataArray[0]);
						dataArray = new String[3];
						dataArray[0] = "YTE";
						dataArray[1] = imei;
						dataArray[2] = INSTRUCTION_TYPE.ZWXD.toString();
					}else {
						imei = dataArray[1];
					}
					socketMap.put(imei, sc);
					service.signIn(dataArray, sc);
					break;
				case INIT:
					service.initStatus(dataArray);
					break;
				case DATA:
					service.uploadStatus(dataArray);
					break;
				case ACK:
					service.confirm();
					break;
				case IRYK:
					service.manual(dataArray);
					break;
				case RESET:
					insTask.resetPlan(dataArray[1]);
					break;
				case ADD:
					insTask.addPlan(dataArray[1]);
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();// 处理数据时发生错误
		}
		logger.info(data);
	}

	private INSTRUCTION_TYPE getInsType(String[] dataArray) {
		if (dataArray.length >= 3 && dataArray[0].equalsIgnoreCase(HEAD)) {
			if (dataArray[2].equalsIgnoreCase("ZWXD")) {
				return INSTRUCTION_TYPE.ZWXD;
			} else if (dataArray[2].equalsIgnoreCase("DATA")) {
				return INSTRUCTION_TYPE.DATA;
			} else if (dataArray[2].equalsIgnoreCase("ACK")) {
				return INSTRUCTION_TYPE.ACK;
			} else if (dataArray[2].equalsIgnoreCase("IRYK")) {
				return INSTRUCTION_TYPE.IRYK;
			} else if (dataArray[2].equalsIgnoreCase("INIT")) {
				return INSTRUCTION_TYPE.INIT;
			}
		} else if (dataArray.length == 2) {
			if (dataArray[0].equalsIgnoreCase("RESET")) {// 如果收到重置调度命令即删除调度再添加
				return INSTRUCTION_TYPE.RESET;
			} else if (dataArray[0].equalsIgnoreCase("ADD")) {
				return INSTRUCTION_TYPE.ADD;
			}
		}else {
			//如果是json
			String imei = LightService.getImei(dataArray[0]);
			if(!imei.isEmpty()) {
				return INSTRUCTION_TYPE.ZWXD; 
			}
		}
		return INSTRUCTION_TYPE.UNKNWON;
	}

	@Override
	public void onDisconnected(SocketChannel disConnectSc) {
		for (String imei : socketMap.keySet()) {
			if (socketMap.get(imei).equals(disConnectSc)) {
				socketMap.remove(imei);
				service.offline(imei);
			}
		}
	}

}
