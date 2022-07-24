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

	}

	@Override
	public void onRead(SocketChannel sc, String data) {
		System.out.println(data);
	}

	@Override
	public void onDisconnected(SocketChannel sc) {

	}
}
