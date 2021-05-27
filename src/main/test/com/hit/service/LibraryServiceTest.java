package com.hit.service;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;
import com.hit.algorithm.IAlgoStringMatching;
import com.hit.dm.Book;

public class LibraryServiceTest {

	@Test
	public void search1() throws IOException, ClassNotFoundException {
        IAlgoStringMatching myAlgo = null;
        LibraryService lb = new LibraryService(myAlgo);
        ArrayList<Book> array = lb.search("Harry Potter",1);
        String actual = (array.get(0)).getName();
        String expected = "Harry Potter";
        Assert.assertEquals(expected ,actual);
    }
	
	@Test
	public void search2() throws IOException, ClassNotFoundException {
        IAlgoStringMatching myAlgo = null;
        LibraryService lb = new LibraryService(myAlgo);
        ArrayList<Book> array = lb.search("There is no book with this name" ,2);
        Boolean actual = array.isEmpty();
        Assert.assertTrue(actual);
    }
	
	@Test
	public void removeBook1() throws IOException, ClassNotFoundException {
        IAlgoStringMatching myAlgo = null;
        LibraryService lb = new LibraryService(myAlgo);
        boolean actual = lb.removeBook("There is no book with this name" ,1);
        boolean expected = false;
        Assert.assertEquals(expected, actual);
    }
	
	@Test
	public void removeBook2() throws IOException, ClassNotFoundException {
        IAlgoStringMatching myAlgo = null;
        LibraryService lb = new LibraryService(myAlgo);
        boolean actual = lb.removeBook("Percy Jackson" ,2);
        boolean expected = true;
        Assert.assertEquals(expected, actual);
    }
	
	@Test
	public void addBook() throws IOException, ClassNotFoundException {
        IAlgoStringMatching myAlgo = null;
        LibraryService lb = new LibraryService(myAlgo);
        Boolean actual = lb.addBook("The Maze Runner", "James Dashner");
        Assert.assertTrue(actual);
    }

}
