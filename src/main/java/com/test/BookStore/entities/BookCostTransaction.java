package com.test.BookStore.entities;

import com.test.BookStore.dto.BookQuantity;
import com.test.BookStore.models.BookModel;
import com.test.BookStore.models.BookQuantityFaturaModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "book_cost")
@Data
@ToString
@NoArgsConstructor
public class BookCostTransaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	private Book book;
	private int quantity;
	private double pricePerBook;
	private double totalPrice;
	private double offerQuantity;
	private double discount;
	private double cost;
	
	public BookQuantityFaturaModel getBookQuantityFaturaModel() {
		return new BookQuantityFaturaModel( book.getBookModel(),quantity,pricePerBook,totalPrice,offerQuantity,discount,cost);
	}
}
