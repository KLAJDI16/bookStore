package com.test.BookStore.models;

import com.test.BookStore.entities.BookInfo;

public class LibraryBookModel {
	public String libraryName ;
	public String bookTitle ;
	public int quantity;
	//public int offerQuantity;
	//public double discountPercent;
	//public int price;
	public BookInfo bookInfo;
	public LibraryBookModel(String libraryName, String bookTitle, int quantity) {
		super();
		this.libraryName = libraryName;
		this.bookTitle = bookTitle;
		this.quantity = quantity;

	}
	public LibraryBookModel(String libraryName, String bookTitle, int quantity, BookInfo bookInfo) {
		super();
		this.libraryName = libraryName;
		this.bookTitle = bookTitle;
		this.quantity = quantity;
		this.bookInfo = bookInfo;
	}
	
	
}
