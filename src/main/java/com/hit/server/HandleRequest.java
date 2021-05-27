package com.hit.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;
import com.hit.service.Controller;

public class HandleRequest<T> implements Runnable {

	Gson gson;
	Controller controller;
	Request request;
	Socket someClient;
	DataInputStream input;
	T messageIn;
	Response response;
	DataOutputStream output;
	Socket myServer;

	public HandleRequest(Socket someClient) throws IOException, ClassNotFoundException {
		gson = new Gson();
		this.someClient = someClient;
		input = null;
		output = null;
	}

	@Override
	public synchronized void run() {
		try {
			input = new DataInputStream(new BufferedInputStream(someClient.getInputStream())); // Read the request from
																								// the client
			output = new DataOutputStream(someClient.getOutputStream());
			messageIn = (T) input.readUTF();

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		request = gson.fromJson(messageIn.toString(), Request.class); // Client's Request
		try {

			controller = new Controller<String, String, String, Integer>(request.headers.action, request.body.name,
					request.body.author, request.body.selectedAlgo);
			if (request.headers.action.equals("SEARCH")) { // Get response form controller
				response = new Response(controller.answerFromSearch());
			} else { // Get response form controller
				response = new Response(controller.answer(), request.headers.action, request.body.name,
						request.body.author);
			}

			output.writeUTF(gson.toJson(response)); // Send Json response to client

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
