package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public abstract class SimpleServer implements ServerInterface {
	private ByteBuffer buffer =  ByteBuffer.allocateDirect(1024);
	
	protected int port;
	protected Map<String,SocketChannel> socketMap = new HashMap<String,SocketChannel>();
//	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public SimpleServer(int port){
		this.port = port;
	}
	
	public static SimpleServer getInstance() {
		// TODO Auto-generated method stub
		return null;
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
				selector.select();

				Set<SelectionKey> selectKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectKeys.iterator();
				while (it.hasNext()) {
					SelectionKey key = it.next();
					if(key.isAcceptable()) {
						ServerSocketChannel channel = (ServerSocketChannel)key.channel();
						SocketChannel socketChannel = channel.accept();
						socketChannel.configureBlocking(false);
						socketChannel.register(selector, SelectionKey.OP_READ);
						onConnected(socketChannel);
					}
					
					if(key.isReadable()) {
						SocketChannel socketChannel = (SocketChannel)key.channel();
						buffer.clear();
						int count = socketChannel.read(buffer);
						if(count > 0) {
							String data = bufferRead(buffer);
							onRead(socketChannel,data);
						}
						else {
							key.cancel();
							onDisconnected(socketChannel);
						}
					}
					
					it.remove();
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String bufferRead(ByteBuffer buffer) {
		buffer.flip();
		StringBuffer sb = new StringBuffer();
		while(buffer.hasRemaining()){
			sb.append((char) buffer.get());
		  }
		return sb.toString();
	}

}
