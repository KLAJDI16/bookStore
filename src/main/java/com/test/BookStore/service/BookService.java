package com.test.BookStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.BookStore.models.*;
import com.test.BookStore.dto.RequestDTO;
import com.test.BookStore.entities.Book;
import com.test.BookStore.filters.SearchFilters;
import com.test.BookStore.repo.BookRepository;
import com.test.BookStore.repo.LibraryBookRepository;
import com.test.BookStore.repo.LibraryRepository;
import com.test.BookStore.repo.StudentRepository;
@Service
public class BookService implements BookStore{
	@Autowired
	private  BookRepository bookRepository;

	@Autowired
	private  StudentRepository studentRepository;
	@Autowired
	private  LibraryRepository libraryRepository;
	@Autowired
	private  LibraryBookRepository libraryBookRepository;
	@Autowired
	private SearchFilters<Book> booksFilter;
	@Autowired
	private ProgramService programService;
	


	@Override
 	public List<BookModel> retrieveBooks() {
		List<Book> books = bookRepository.findAll();
		List<BookModel> model = books.stream().map(e -> e.getBookModel()).toList();
		return model;

	}

	@Override
	public BookModel addNewBook(Book book) {
		
		Book book2 = 	bookRepository.save(book);
		
		BookModel model = book2.getBookModel();
		return model;
		
	}

	@Override
	public BookModel updateBook(int id,Book book) {
		
		Book book2 = bookRepository.findById(id).get();
		
	
		book2.setAuthor(book.getAuthor());
		
		book2.setPublicationYear(book.getPublicationYear());
		
		
		bookRepository.save(book2);
		
		BookModel model = book2.getBookModel();
		
		
		return model;
		
	}

	@Override
	public String deleteBook(int id) {
		// TODO Auto-generated method stub
		bookRepository.delete(bookRepository.findById(id).get());
		
		
		return "Book with id : "+id+" deleted ";
	}

	@Override
	public String getBookDetails(int id) {
		Book book = bookRepository.findById(id).get();
		return book.toString();
	}
	
	public BookModel getBook(int id) {
		Book 	book  =  bookRepository.findById(id).get();
		
		BookModel model = book.getBookModel();
		
		return model;
	}
	


	public String deleteAllBooks() {
		
			 	bookRepository.deleteAll();
			 	
			 	return "Books deleted All";
	}

	public List<BookModel> getBookByTitle(String title) {
		List<Book> books =  bookRepository.findByTitleIgnoreCase(title);
		
		List<BookModel> model = books.stream().map(e -> e.getBookModel()).toList();
		return model;
		
	}
	


	
	public List<BookModel> filterBook(RequestDTO requestDTO) {
		
		return bookRepository.findAll(booksFilter.getSpecification(requestDTO)).stream().map(e -> e.getBookModel()).toList();
	}
}
