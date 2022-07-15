package server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public abstract class SimpleServer implements ServerInterface {

	private ByteBuffer buffer =  ByteBuffer.allocateDirect(1024);
	
	@Override
	public void start() {
		try {
			Selector selector = Selector.open();
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			while (true) {
				selector.select();

				Set<SelectionKey> selectKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectKeys.iterator();
				while (it.hasNext()) {
					SelectionKey key = it.next();
					if(key.isAcceptable()) {
						ServerSocketChannel channel = (ServerSocketChannel)key.channel();
						SocketChannel socketChannel = channel.accept();
						onConnected(socketChannel);
					}
					
					if(key.isReadable()) {
						SocketChannel socketChannel = (SocketChannel)key.channel();
						buffer.clear();
						int count = socketChannel.read(buffer);
						if(count > 0) {
							onRead(socketChannel,(ByteBuffer)buffer.flip());
						}
						else {
							key.cancel();
							onDisconnected(socketChannel);
						}
							
					}
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

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
