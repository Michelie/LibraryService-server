package com.hit.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.hit.dm.Book;

public class DaoFileImpl implements IDao<Book> {
	ObjectOutputStream oos;
	static String pathFile;
	static ArrayList<Book> books;
	File f;
	StringBuilder strAll;
	static Path booksPath;

	public DaoFileImpl(String pathFile) throws IOException, ClassNotFoundException { // C-tor
		books = new ArrayList<Book>();
		this.pathFile = pathFile;
		strAll = new StringBuilder();
		booksPath = Paths.get(System.getProperty("user.dir") + pathFile);
		createArrayList();
		if (books.isEmpty()) {
			// Singleton
			DatabaseInitializedSingleton databaseInitializedSingleton = DatabaseInitializedSingleton.getInstance();
		}
		insertToFile();
	}

	@Override
	public void addBook(Book newBook) throws IOException {
		try {
			createArrayList();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		books.add((Book) newBook);
		try {
			insertToFile();
		} catch (ClassNotFoundException | IOException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void removeBook(ArrayList<Book> allBooksArr) throws IOException {
		books = allBooksArr;
		try {
			insertToFile();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Book> createArrayList() throws ClassNotFoundException {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(new File(booksPath.toAbsolutePath().toString())));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			System.out.println("Initilaizing the first time");
		}
		try {
			if (ois != null) {
				books = (ArrayList<Book>) ois.readObject();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return books;
	}

	// insert the Books to the file
	private void insertToFile() throws IOException, ClassNotFoundException {
		oos = new ObjectOutputStream(new FileOutputStream(new File(booksPath.toAbsolutePath().toString())));
		oos.writeObject(books);
		oos.flush();
		oos.close();
	}

	public static class DatabaseInitializedSingleton {
		private static DatabaseInitializedSingleton instance;

		private DatabaseInitializedSingleton() throws FileNotFoundException, IOException {
			books.add(new Book("Percy Jackson", "Rick Riordan", 1));
			books.add(new Book("Diary of a wimpy kid", "Jeff Kinney", 2));
			books.add(new Book("Spiderman", "Stan Lee", 3));
			books.add(new Book("Happiness for humans", "P Z Reizin", 4));
			books.add(new Book("Harry Potter", "J K Rowling", 5));
			books.add(new Book("A Song of Ice and Fire", "George R R Martin", 2));
			books.add(new Book("The Dark tower", "Stephen King", 2));
		}

		static {
			try {
				instance = new DatabaseInitializedSingleton();
			} catch (Exception e) {
				throw new RuntimeException("Exception occured in creating singleton instance");
			}
		}

		public static DatabaseInitializedSingleton getInstance() {
			return instance;
		}
	}

}
