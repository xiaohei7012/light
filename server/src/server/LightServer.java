package server;

import java.nio.channels.SocketChannel;

public class LightServer extends SimpleServer {
	
	public LightServer(int port) {
		super(port);
	}

	public static void main(String[] args) {
		LightServer server = new LightServer(14332);
		server.start();
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
		}else if(dataArray.length >2 && dataArray[0].equalsIgnoreCase("???")) {
			
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
