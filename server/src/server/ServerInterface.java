package server;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public interface ServerInterface {
	
	public void start();

	// �豸����
	public void onConnected(SocketChannel sc);

	// ��ȡ����
	public void onRead(SocketChannel sc, ByteBuffer temp);

	// �Ͽ�����
	public void onDisconnected(SocketChannel sc);
}
