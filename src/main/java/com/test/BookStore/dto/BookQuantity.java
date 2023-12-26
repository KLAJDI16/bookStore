package com.test.BookStore.dto;

public class BookQuantity<T> {
	public T book;
	public int quantity;
	public BookQuantity(T book, int quantity) {
		super();
		this.book = book;
		this.quantity = quantity;
	}
	public BookQuantity() {
	}
}
