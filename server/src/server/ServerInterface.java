package server;

import java.nio.channels.SocketChannel;

public interface ServerInterface {
	
	public void start();

	public void onConnected(SocketChannel sc);

	public void onRead(SocketChannel sc, String data);

	public void onDisconnected(SocketChannel sc);
}
