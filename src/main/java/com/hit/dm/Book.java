package com.hit.dm;

import java.io.Serializable;

public class Book implements Serializable { //Class for Book data model
	private String name;
	private String author;
	private int aisle;

	public Book(String name, String author, int aisle) {
		this.name = name;
		this.author = author;
		this.aisle = aisle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Book{" + "name='" + name + '\'' + ", author='" + author + '\'' + ", aisle=" + aisle + '}';
	}

	public int getAisle() {
		return aisle;
	}

	public void setAisle(int aisle) {
		this.aisle = aisle;
	}
}
