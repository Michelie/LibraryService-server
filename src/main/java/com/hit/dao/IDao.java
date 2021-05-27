package com.hit.dao;

import java.io.IOException;
import java.util.ArrayList;

import com.hit.dm.Book;


public interface IDao <T> {

    public void removeBook(ArrayList<T> bookToRemove) throws IOException;
    public ArrayList<T> createArrayList() throws ClassNotFoundException;
	public void addBook(T newBook) throws IOException;
}
