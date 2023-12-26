package com.test.BookStore.entities;

import com.test.BookStore.models.LibraryBookModel;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@Table(name = "library_books_table")
public class LibraryBook {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;
	
	@ManyToOne
    @JoinColumn(name = "library_id")
	private Library library;
	private String libraryName ;
	private String bookTitle ;
	private int quantity;
	
	@Embedded
	private BookInfo bookInfo;
	
	public void addQuantity(int c) {
		quantity+=c;
	}
	public void removeQuantity(int c) {
		quantity-=c;
	}

	public LibraryBook(Book book, Library library, int quantity) {
		this.book = book;
		this.library = library;
		this.libraryName = library.getName();
		this.bookTitle = book.getTitle();
		this.quantity = quantity;
		 bookInfo = new BookInfo();
	}
	public LibraryBook(Book book, Library library, int quantity,BookInfo bookInfo) {
		this.book = book;
		this.library = library;
		this.libraryName = library.getName();
		this.bookTitle = book.getTitle();
		this.quantity = quantity;
		this.bookInfo=bookInfo;
	}

	public LibraryBookModel getLibraryBookModel() {
		return new LibraryBookModel( libraryName,  bookTitle,  quantity, bookInfo );
	}

}
