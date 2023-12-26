package com.test.BookStore.models;

import java.time.LocalDateTime;
import java.util.List;

import com.test.BookStore.entities.BookSales;
import com.test.BookStore.entities.Library;
import com.test.BookStore.entities.LibrarySales;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class LibrarySalesModel {
	
	public int id;
	public LocalDateTime  localDateTime;

	public LibraryModel library;
	
	public List<BookSalesModel> bookSales;

	public LibrarySalesModel(int id,LocalDateTime localDateTime, LibraryModel library, List<BookSalesModel> bookSales) {
		super();
		this.id = id;
		this.localDateTime = localDateTime;
		this.library = library;
		this.bookSales = bookSales;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
