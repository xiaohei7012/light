package com.light.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.light.util.Util;

public class DataService {
	public static void sendData(String data) {
		Socket socket = null;
		int port = Util.port;
		try {
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
