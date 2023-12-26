package com.test.BookStore.service;

import java.util.List;

import com.test.BookStore.entities.Book;
import com.test.BookStore.models.*;
public interface BookStore {

	public List<BookModel> retrieveBooks();
	public BookModel addNewBook(Book book);
	public BookModel updateBook(int id,Book book);
	public String deleteBook(int id);
	public String getBookDetails(int id); 


}
/*
The main routes will be:
•	GET /books: Retrieve all books.
•	POST /books: Add a new book.
•	PUT /books/{id}: Update the details of a book.
•	DELETE /books/{id}: Delete a book.
•	GET /books/{id}: Get details of a specific book.

*/