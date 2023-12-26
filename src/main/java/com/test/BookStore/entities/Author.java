package com.test.BookStore.entities;

import java.util.List;

import com.test.BookStore.models.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.Id;
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Author {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String email;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "author")
	private List<Book> books;

	public AuthorModel getAuthorModel() {
		
		return new AuthorModel(id,name,email);
	}
	public void addBook(Book book) {
		books.add(book);
	}
	public void removeBook(Book book) {
		books.remove(book);
	}
	

	public Author(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}
}
