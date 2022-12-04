package server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

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

	private Map<String, String> receiveCache = new HashMap<String, String>();

	public static enum INSTRUCTION_TYPE {
		ZWXD, // 上电
		INIT, // 上电确认状态
		DATA, // 状态
		ACK, // 确认
		IRYK, // 遥控
		RESET, // 重置
		ADD, // 马上启动方案
		SEND, // 单独控制
		SETUSETIME, // 使用时间置0
		UNKNWON; // 未知
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
		logger.info(data);
		
		try {
			String[] lineArray = data.split(SPLITLINE);
			for (String line : lineArray) {
				String[] dataArray = line.split(SPLITWORD);
				switch (getInsType(dataArray)) {
				case ZWXD:
					String imei = "";
					if (dataArray.length <= 1) {
						imei = LightService.getImei(dataArray[0]);
						dataArray = new String[3];
						dataArray[0] = "YTE";
						dataArray[1] = imei;
						dataArray[2] = INSTRUCTION_TYPE.ZWXD.toString();
					} else {
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
					service.confirm(dataArray);
					break;
				case IRYK:
					service.manual(dataArray);
					break;
				// 以下三个为http端发送，SEND命令要把socket作为参数传递，开一个线程，如果有ACK返回要发送ACK返回给http
				case RESET:
					insTask.resetPlan(dataArray[1]);
					break;
				case ADD:
					insTask.addPlan(dataArray[1]);
					break;
				case SEND:
					service.sendData(dataArray, sc);
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();// 处理数据时发生错误
		}
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
			} else if (dataArray[2].equalsIgnoreCase("SEND")) {
				return INSTRUCTION_TYPE.SEND;
			}
		} else if (dataArray.length == 2) {
			if (dataArray[0].equalsIgnoreCase("RESET")) {// 如果收到重置调度命令即删除调度再添加
				return INSTRUCTION_TYPE.RESET;
			} else if (dataArray[0].equalsIgnoreCase("ADD")) {
				return INSTRUCTION_TYPE.ADD;
			}
		} else {
			// 如果是json
			String imei = LightService.getImei(dataArray[0]);
			if (!imei.isEmpty()) {
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

	// sc为http服务器交互的连接
	public void sendData4Ack(String imei, String data, SocketChannel sc) {
		try {
			SocketChannel s = socketMap.get(imei);
			if (s != null) {
				logger.info("send:" + data.toUpperCase());
				s.write(ByteBuffer.wrap(data.toUpperCase().getBytes()));

				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(2 * 1000);
							String ack = receiveCache.get(imei);
							if (ack != null) {
								sc.write(ByteBuffer.wrap("ACK".getBytes()));
								receiveCache.put(imei, null);
							} else {
								sc.write(ByteBuffer.wrap("ERROR".getBytes()));
							}
							sc.close();
						} catch (InterruptedException | IOException e) {
							e.printStackTrace();
						}
					}

				}).start();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<String, String> getReceiveCache() {
		return receiveCache;
	}

	public void setReceiveCache(Map<String, String> receiveCache) {
		this.receiveCache = receiveCache;
	}

}
