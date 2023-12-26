package com.test.BookStore.entities;

import com.test.BookStore.models.BookSalesModel;

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
@Table(name = "book_sales")
@Data
@ToString
@NoArgsConstructor
public class BookSales {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	private Book book;
	
	private int amountSold;
	
	private double revenue;

	public BookSales(Book book, int amountSold, double revenue) {
		super();
		this.book = book;
		this.amountSold = amountSold;
		this.revenue = revenue;
	}
	public BookSalesModel getBookSalesModel() {
		return  new BookSalesModel(id,book.getBookModel(),amountSold,revenue);
	}
	
}
