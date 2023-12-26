package com.test.BookStore.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentBook {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne()
	@JoinColumn(name = "book_id")
	private Book book;
	private int quantity;
	private int moneySpent;
	public StudentBook(Book book, int quantity, int moneySpent) {
		super();
		this.book = book;
		this.quantity = quantity;
		this.moneySpent = moneySpent;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
