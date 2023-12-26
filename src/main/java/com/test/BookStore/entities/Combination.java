package com.test.BookStore.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Entity
@Table(name="combination_list_table")
public class Combination {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@ManyToMany
	@JoinTable(name = "combination_books",
	joinColumns = {@JoinColumn(name="combination_id")},
	inverseJoinColumns = {@JoinColumn(name="book_id")}
	)
	private List<Book> books;
	@ManyToOne
	private Library library;
	public Combination(List<Book> specialBooks) {
		super();
		this.books = specialBooks;
	}
	private int specialDiscount;
	public Combination(List<Book> specialBooks, int specialDiscount) {
		super();
		this.books = specialBooks;
		this.specialDiscount = specialDiscount;
	}	
}