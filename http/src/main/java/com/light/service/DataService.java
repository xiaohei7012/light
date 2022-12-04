package com.light.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.light.util.Util;

public class DataService {
	public static boolean sendData4Ack(String data) {
		Socket socket = null;
		int port = Util.port;
		try {
//			socket = new Socket("47.106.159.201", port);
			socket = new Socket("localhost", port);
			OutputStream out = socket.getOutputStream();
			out.write(data.getBytes());

			// 是否有ACK
			Thread.sleep(2 * 1000);
			InputStream in = socket.getInputStream();
			byte[] receive = new byte[128];
			String receiveAck = "";
			int len = 0;
			while ((len =(in.read(receive))) != -1) {
				receiveAck = new String(receive,0,len);
			}
			if(receiveAck.equalsIgnoreCase("ACK")) {
				System.out.println("有ACK,"+receiveAck);
				return true; 
			}
			System.out.println("无ACK,"+receiveAck);
			return false;
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return false;
	}
	
	public static void sendData(String data) {
		Socket socket = null;
		int port = Util.port;
		try {
//			socket = new Socket("47.106.159.201", port);
			socket = new Socket("localhost", port);
			OutputStream out = socket.getOutputStream();
			out.write(data.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
