package com.hit.service;

import java.io.IOException;
import java.util.ArrayList;

import com.hit.algorithm.IAlgoStringMatching;
import com.hit.dm.Book;
import com.hit.server.Response;

public class Controller<T, U, V, S> {
	IAlgoStringMatching myAlgo;
	LibraryService libraryService;
	Boolean ans;
	T action;
	U name;
	V author;
	S selectedAlgo;
	ArrayList<Book> myBooks;

	public Controller(T action, U name, V author, S selectedAlgo) throws ClassNotFoundException, IOException {

		libraryService = new LibraryService(myAlgo);
		this.action = action;
		this.name = name;
		this.author = author;
		this.selectedAlgo = selectedAlgo;
		organizeActions();
	}

	private void organizeActions() throws ClassNotFoundException, IOException {
		switch (action.toString()) {
		case "ADD":
			ans = libraryService.addBook(name.toString(), author.toString());
			answer();
			break;

		case "SEARCH":
			myBooks = libraryService.search(name.toString(), ((int) selectedAlgo));
			if (myBooks != null) {
				for (Book book : myBooks) {
					System.out.println("Search: " + book.toString());
				}
			}
			answerFromSearch();
			System.out.println("from Search in Controller");
			break;

		case "DELETE":
			ans = libraryService.removeBook(name.toString(), ((int) selectedAlgo));
			break;
		}

	}

	public ArrayList<Book> answerFromSearch() {
		return myBooks; // Can be a null value

	}

	public Boolean answer() {
		return ans;
	}

}