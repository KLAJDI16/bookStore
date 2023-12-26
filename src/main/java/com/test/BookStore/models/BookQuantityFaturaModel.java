package com.test.BookStore.models;

import com.test.BookStore.dto.BookQuantity;
import com.test.BookStore.entities.Book;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@NoArgsConstructor

public class BookQuantityFaturaModel {
	public BookModel bookModel;
	public int quantity;
	public double pricePerBook;
	public double totalPrice;
	public double offerQuantity;
	public double discount;
	public double cost;
	public BookQuantityFaturaModel(BookModel bookModel, int quantity, double pricePerBook, double totalPrice,
			double offerQuantity, double discount, double cost) {
		super();
		this.bookModel = bookModel;
		this.quantity = quantity;
		this.pricePerBook = pricePerBook;
		this.totalPrice = totalPrice;
		this.offerQuantity = offerQuantity;
		this.discount = discount;
		this.cost = cost;
	}
	
}
