package server;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public interface ServerInterface {
	
	public void start();

	// 设备连接
	public void onConnected(SocketChannel sc);

	// 读取数据
	public void onRead(SocketChannel sc, ByteBuffer temp);

	// 断开连接
	public void onDisconnected(SocketChannel sc);
}
