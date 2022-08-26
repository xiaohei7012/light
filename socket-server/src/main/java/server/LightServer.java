package server;

import java.nio.channels.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.LightService;

public class LightServer extends SimpleServer {
	private static LightServer instance;
	private static LightService service;
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public final static String HEAD = "YTE";
	public final static String SPLITWORD = " ";

	public static enum INSTRUCTION_TYPE {
		ZWXD, DATA, ACK, IRYK, RESET, ADD, UNKNWON;
	}

	public static final int port = 14334;
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
			instance = new LightServer(port);
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
//		socketMap.put(sc.socket().getInetAddress().getHostAddress() + " " + sc.socket().getPort(), sc);
//		logger.info("有新的连接:" + sc.socket().getInetAddress().getHostAddress());
		System.out.println("有新的连接:" + sc.socket().getInetAddress().getHostAddress());
		logger.info("有新的连接:" + sc.socket().getInetAddress().getHostAddress());
	}

	@Override
	public void onRead(SocketChannel sc, String data) {
		try {
			String[] dataArray = data.split(SPLITWORD);
			switch (getInsType(dataArray)) {
			case ZWXD:
				String imei = dataArray[1];
				socketMap.put(imei, sc);
				service.signIn(dataArray, sc);
				break;
			case DATA:
				service.uploadStatus(dataArray);
				break;
			case ACK://确认
				service.confirm();
				break;
			case IRYK://遥控
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
		} catch (Exception e) {
			e.printStackTrace();//处理数据时发生错误
		}
		System.out.println(data);
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
			} else if (dataArray[2].equalsIgnoreCase("IRYK ")) {
				return INSTRUCTION_TYPE.IRYK;
			}
		} else if (dataArray.length == 2) {
			if (dataArray[0].equalsIgnoreCase("RESET")) {// 如果收到重置调度命令即删除调度再添加
				return INSTRUCTION_TYPE.RESET;
			} else if (dataArray[0].equalsIgnoreCase("ADD")) {
				return INSTRUCTION_TYPE.ADD;
			}
		}
		return INSTRUCTION_TYPE.UNKNWON;
	}

	@Override
	public void onDisconnected(SocketChannel disConnectSc) {
		for (String imei : socketMap.keySet()) {
			if (socketMap.get(imei).equals(disConnectSc)) {
				socketMap.remove(imei);
			}
		}
	}

}
