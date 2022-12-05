package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SimpleServer implements ServerInterface {
	private ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected int port;
	protected Map<String, SocketChannel> socketMap = new HashMap<String, SocketChannel>();
//	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public SimpleServer(int port) {
		this.port = port;
	}

	@Override
	public void start() {
		try {
			Selector selector = Selector.open();
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.bind(new InetSocketAddress(port));
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			while (true) {
				try {
					selector.select();

					Set<SelectionKey> selectKeys = selector.selectedKeys();
					Iterator<SelectionKey> it = selectKeys.iterator();
					while (it.hasNext()) {
						SelectionKey key = it.next();
						try {
							if (key.isAcceptable()) {
								ServerSocketChannel channel = (ServerSocketChannel) key.channel();
								SocketChannel socketChannel = channel.accept();
								socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);//SO_KEEPALIVE
								socketChannel.configureBlocking(false);
								socketChannel.register(selector, SelectionKey.OP_READ);
								onConnected(socketChannel);
							}

							if (key.isReadable()) {
								SocketChannel socketChannel = (SocketChannel) key.channel();
								buffer.clear();
								int count = socketChannel.read(buffer);
								if (count > 0) {
									String data = bufferRead(buffer);
									onRead(socketChannel, data);
								} else {
									key.cancel();
									onDisconnected(socketChannel);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();// 进入处理后错误
						}
						it.remove();

					}
				} catch (Exception e) {
					e.printStackTrace();// 进入选择后错误
				}
			}

		} catch (IOException e) {
			e.printStackTrace();// 启动时出错
		}

	}

	public String bufferRead(ByteBuffer buffer) {
		buffer.flip();
		StringBuffer sb = new StringBuffer();
		while (buffer.hasRemaining()) {
			sb.append((char) buffer.get());
		}
		return sb.toString();
	}

	public void sendData(String imei, String data) {
		try {
			SocketChannel s = socketMap.get(imei);
			if (s != null) {
				s.write(ByteBuffer.wrap(data.toUpperCase().getBytes()));
				logger.info("send:" + data.toUpperCase());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void statusTimer(int period) {
		new Timer().schedule(new StatusnTimerTask(period), 5 * 1000, period * 60 * 1000);
	}
}
