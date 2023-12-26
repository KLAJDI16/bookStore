package com.test.BookStore.entities;

import java.util.List;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.constraints.Length;

import com.test.BookStore.models.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.Id;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Book {
	//id, title, author, publicationYear, price, and stock
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
		
	private String publicationYear;
	private String title;
	private int pages;
	private Genre genre;
	
//	@ManyToOne
//	@JoinColumn(name = "library_id")
//	public Library library;
    
	@ManyToOne
	@JoinColumn(name = "author_id")
	private Author author;

//    @OneToMany(mappedBy = "book")
//	public List<LibraryBook> library_book;

	
	@Override
	public String toString() {
		return  "Book with id : "+id+" Author : "+author+"  Publication Year : "+publicationYear +"of genre"+""
				+ genre+" with "+pages+" pages";
	}
	
	public BookModel getBookModel() {
	return new BookModel( title, publicationYear, pages,genre);
	}
	
	public Book(String publicationYear, String title, int pages, Genre genre, Author author) {
		super();
		this.publicationYear = publicationYear;
		this.title = title;
		this.pages = pages;
		this.genre = genre;
		this.author = author;
	}

	public enum Genre{
		ADVENTURE,HORROR,COMEDY,ROMANCE,ACTION,DRAMA
	}
}
