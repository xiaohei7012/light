package server;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public abstract class SimpleServer implements ServerInterface {

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(SocketChannel sc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRead(SocketChannel sc, ByteBuffer temp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDisconnected(SocketChannel sc) {
		// TODO Auto-generated method stub

	}

}
