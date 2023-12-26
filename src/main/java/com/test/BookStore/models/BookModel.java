package com.test.BookStore.models;

import com.test.BookStore.entities.Book.Genre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@NoArgsConstructor
public class BookModel {
	public String publicationYear;
	public String title;
	public int pages;
	public Genre genre;
	public BookModel(String title, String publicationYear, int pages,Genre genre) {
		super();
		this.publicationYear = publicationYear;
		this.title = title;
		this.pages = pages;
		this.genre = genre;

		}

	@Override
	public String toString() {
		return "Book With  title : " + title + " publicationYear  : " + publicationYear + 
				", Pages time : "+pages+" genre : "+genre;
	}
	
	
	
	
	
}
