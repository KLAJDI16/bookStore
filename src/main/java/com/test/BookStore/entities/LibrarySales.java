package com.test.BookStore.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.test.BookStore.models.LibrarySalesModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "library_sales")
@Data
@ToString
public class LibrarySales {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private LocalDateTime  localDateTime;
	
	@ManyToOne
	@JoinColumn(name = "library_id")
	private Library library;
	
	@OneToMany
	private List<BookSales> bookSales;

	public LibrarySales(LocalDateTime localDateTime, Library library, List<BookSales> bookSales) {
		super();
		this.localDateTime = LocalDateTime.now();
		this.library = library;
		this.bookSales = bookSales;
	}
	
	public LibrarySales() {
		this.localDateTime = LocalDateTime.now();
	}
	
	public LibrarySalesModel getLibrarySalesModel() {
		
		return new LibrarySalesModel(id,localDateTime,library.getLibraryModel(),bookSales.stream().map(e -> e.getBookSalesModel()).toList());
	}
	

}
