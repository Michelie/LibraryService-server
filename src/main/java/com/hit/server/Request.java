package com.hit.server;

import java.io.PrintWriter;

import com.google.gson.Gson;

public class Request {
	Headers headers;
	Body body;
	Gson gson;
	PrintWriter output;
	StringBuilder jsonString;

	public Request(String action, String name, String author, int selectedAlgo) {
		headers = new Headers(action);
		body = new Body(name, author, selectedAlgo);
		gson = null;

	}

	public Request(String action, String name, int selectedAlgo) {
		headers = new Headers(action);
		body = new Body(name, selectedAlgo);
		gson = null;
	}

}

class Headers {
	String action;

	public Headers(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}
}

class Body {
	String name;
	String author;
	int selectedAlgo;

	public Body(String name, String author, int selectedAlgo) {
		this.name = name;
		this.author = author;
		this.selectedAlgo = selectedAlgo;
	}

	public Body(String name, int selectedAlgo) {
		this.name = name;
		this.selectedAlgo = selectedAlgo;
		this.author = null;
	}

	public String getName() {
		return name;
	}

	public String getAuthor() {
		return author;
	}

	public int getSelectedAlgo() {
		return selectedAlgo;
	}
}