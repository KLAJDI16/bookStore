package com.test.BookStore.models;

import java.io.Serializable;

import com.test.BookStore.entities.Book;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class BookSalesModel implements Serializable{

	public int id;
	
	
	public BookModel book;
	
	public int amountSold;
	
	public double revenue;

	public BookSalesModel(int id,BookModel book, int amountSold, double revenue) {
		super();
		this.id = id;
		this.book = book;
		this.amountSold = amountSold;
		this.revenue = revenue;
	}
	
	
}
