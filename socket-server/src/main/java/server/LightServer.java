package server;

import java.nio.channels.SocketChannel;

public class LightServer extends SimpleServer {
	InstructionTask insTask;
	
	public LightServer(int port) {
		super(port);
	}

	public static void main(String[] args) {
		LightServer server = new LightServer(14332);
		server.start();
	}

	public void start() {
		insTask = new InstructionTask();
		insTask.start();
		
		super.start();
	}
	
	@Override
	public void onConnected(SocketChannel sc) {
		socketMap.put(sc.socket().getInetAddress().getHostAddress(),sc);
//		logger.info("有新的连接:" + sc.socket().getInetAddress().getHostAddress());
		System.out.println("有新的连接:" + sc.socket().getInetAddress().getHostAddress());
	}

	@Override
	public void onRead(SocketChannel sc, String data) {
		String[] dataArray = data.split(" ");
		if (dataArray.length == 2 && dataArray[0].equalsIgnoreCase("imei")) {
			String imei = dataArray[1];
			socketMap.put(imei,sc);
			System.out.println(imei);
		}else if(dataArray.length >2 && dataArray[0].equalsIgnoreCase("???")) {//如果收到重置调度命令即删除调度再添加
			insTask.reset();
		}else {
			System.out.println(data);
		}
	}

	@Override
	public void onDisconnected(SocketChannel disConnectSc) {
		for(String imei:socketMap.keySet()) {
			if(socketMap.get(imei).equals(disConnectSc)) {
				socketMap.remove(imei);
			}
		}
	}
}
