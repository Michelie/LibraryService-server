package com.hit.server;

import java.util.ArrayList;

import com.hit.dm.Book;

public class Response {
	String Header;
	String Body;

	public Response(Boolean answer, String action, String name, String author) {
		StringBuilder strBuilder = new StringBuilder();
		Header = action;
		
		if (action.equals("ADD")) {
			strBuilder.append("Book " + name + " by " + author + " was ");
			if (answer) {
				strBuilder.append("added successfully");
			} else {
				strBuilder.append("not added successfully");
			}
		} else {
			String newNameString = name.substring(6);
			//System.out.println(newNameString);//
			
			strBuilder.append("Book " + newNameString + " was ");
			if (answer) {
				strBuilder.append("deleted successfully");
			} else {
				strBuilder.append("not deleted successfully");
			}
		}
		Body = strBuilder.toString();
	}

	public Response(ArrayList<Book> booksFound) {
		StringBuilder strBuilder = new StringBuilder();
		Header = "SEARCH";
		if (booksFound != null) {
			for (Book book : booksFound) {
				strBuilder.append("Book "+book.getName() + " by " + book.getAuthor() + " found in aisle "
						+ book.getAisle() + ','+'\n' );
			}
		}
		Body = strBuilder.toString();
	}

	
	public String getHeader() {
		return Header;
	}
	
	public String getBody() {
		return Body;
	}
}
