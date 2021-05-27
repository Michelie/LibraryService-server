package com.hit.service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.hit.algorithm.IAlgoStringMatching;
import com.hit.algorithm.KMPAlgo;
import com.hit.algorithm.NaivePatternSearchAlgo;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.Book;

public class LibraryService {
	private IAlgoStringMatching myAlgo;
	private IDao<Book> dao;
	private String pathFile = "/src/main/resources/books.txt";
	private ArrayList<Book> allBooksArr;
	private StringBuilder allBooksStr;
	
	public LibraryService(IAlgoStringMatching myAlgo) throws IOException, ClassNotFoundException {
		this.myAlgo = myAlgo;
		dao = new DaoFileImpl(pathFile);
		allBooksArr = dao.createArrayList();
		allBooksStr = createStringFromBooks(allBooksArr);
	}

	public Boolean addBook(String newBookName, String newBookAuthor) throws IOException, ClassNotFoundException {
		Random rand = new Random(); // Creating random aisle number
		int randNum = rand.nextInt(7) + 1;
		Book newBook = new Book(newBookName, newBookAuthor, randNum); // Creating book object
		dao.addBook(newBook);
		allBooksArr = dao.createArrayList(); // Updating the list of books
		return true;
	}

	public ArrayList<Book> search(String bookName, int selectedAlgo) throws ClassNotFoundException {
		String resultSearch = new String();
		String[] foundIndexes;
		ArrayList<Book> booksFound = new ArrayList<Book>();

		allBooksArr = dao.createArrayList();
		allBooksStr = createStringFromBooks(allBooksArr);
		if (selectedAlgo == 1) {
			myAlgo = new KMPAlgo();
			resultSearch = (myAlgo.search((allBooksStr.toString()), bookName)).toString();

		} else if (selectedAlgo == 2) {
			myAlgo = new NaivePatternSearchAlgo();
			resultSearch = (myAlgo.search((allBooksStr.toString()), bookName)).toString();

		}
		if (!resultSearch.isEmpty()) { // There are found book
			foundIndexes = isolateIndexes(resultSearch);
			fillBooksFound(booksFound, foundIndexes);
			return booksFound;

		}
		return booksFound; // There are no found book

	}

	public boolean removeBook(String bookToRemove, int selectedAlgo) throws IOException, ClassNotFoundException {
		ArrayList<Book> booksToRemove;
		booksToRemove = search(bookToRemove, selectedAlgo);
		if (booksToRemove == null) { // There are no found book
			return false;
		}
		if (booksToRemove.isEmpty()) {
			return false;
		}
		for (Book current : booksToRemove) { // Remove from ArrayList
			allBooksArr.remove(current);
		}
		dao.removeBook(allBooksArr);
		return true;
	}

	private StringBuilder createStringFromBooks(ArrayList<Book> booksToString) { // Create stringBuilder from the array
																					// list of all books in file
		StringBuilder strAll = new StringBuilder();
		for (Book b : booksToString) {
			strAll.append(b.toString());
			strAll.append("\n");
		}
		return strAll;
	}

	private String[] isolateIndexes(String searchResult) {
		String[] firstSplitStr = searchResult.split("Pattern found at index ");
		StringBuilder s = new StringBuilder();
		for (String current : firstSplitStr) { // connecting all the strings
			s.append(current.toString());
		}
		String[] indexesStr = s.toString().split("\n"); // removes all \n
		return indexesStr;
	}

	private void fillBooksFound(ArrayList<Book> booksFound, String[] foundIndexes) {
		int counter;
		for (int i = 0; i < foundIndexes.length; i++) { // find \n before the wanted object
			counter = 0;
			for (int j = 0; j < Integer.parseInt(foundIndexes[i]); j++) {
				if (allBooksStr.charAt(j) == '\n') {
					counter++;
				}
			}
			booksFound.add(allBooksArr.get(counter));
		}
	}

}
