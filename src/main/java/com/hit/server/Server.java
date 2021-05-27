package com.hit.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;

public class Server implements Runnable {
	ServerSocket server;
	Socket someClient;
	Socket handle;
	DataOutputStream output;
	HandleRequest handleRequest;
	DataInputStream input = null;
	Gson gson;
	private Boolean serverIsRunning;
	private int port;
	ExecutorService fixedPool;

	Response handleResponse;
	String messageIn;

	public Server(int port) throws IOException {
		server = new ServerSocket(port);
		someClient = null;
		serverIsRunning = true;
		gson = new Gson();
	}

	public void run() {
		while (serverIsRunning) {
			try {
				someClient = server.accept(); // Waiting for client
				fixedPool = Executors.newFixedThreadPool(8);

				handleRequest = new HandleRequest<String>(someClient);
				fixedPool.execute(handleRequest);

				disableWarning();
			} catch (IOException | ClassNotFoundException e) {

				e.printStackTrace();
			}

		}
		try {
			someClient.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		((ExecutorService) fixedPool).shutdown();
	}

	
	//Those are warnings we get when using Gson. It seems to be something familiar 
	//that started happening to users since using Java 11/Java 12... But everything works as it should
	public static void disableWarning() {
		System.err.close();
		System.setErr(System.out);
	}

}
