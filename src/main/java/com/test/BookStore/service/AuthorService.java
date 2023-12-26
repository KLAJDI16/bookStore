package com.test.BookStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.BookStore.models.*;
import com.test.BookStore.dto.RequestDTO;
import com.test.BookStore.entities.Author;
import com.test.BookStore.entities.Book;
import com.test.BookStore.entities.Book.Genre;
import com.test.BookStore.filters.SearchFilters;
import com.test.BookStore.repo.AuthorRepository;
import com.test.BookStore.repo.BookRepository;

@Service
public class AuthorService {

	@Autowired
	private  AuthorRepository authorRepository;
	@Autowired
	private  BookRepository bookRepository;
	
	@Autowired
	private SearchFilters<Author> authorsFilter;
	
	public List<BookModel> authorsBook(int id){
		
		Author author = authorRepository.findById(id).get();
		
		List<Book> books = author.getBooks();
		List<BookModel> model = books.stream().map(e -> e.getBookModel()).toList();
		
		return model;
	}

	public String  createAuthor(Author author) {
		 authorRepository.save(author);
			return "Author created sucessfully";
	}
	public List<AuthorModel>  getAuthors(){
		List<Author>  authors =  authorRepository.findAll();
		
		
		List<AuthorModel> model = authors.stream().map(e -> new AuthorModel(e.getId(),e.getName(),e.getEmail())).toList();
		
		return model;
	}
	public String getAuthorById(int id) {
	return 	authorRepository.findById(id).get().toString();
	}
	
	public String getAuthorByName(String name) {
		return 	authorRepository.findByName(name).toString();
		}
	public String deleteAuthor(int id) {
		authorRepository.deleteById(id);
		return "Author Deleted Succesfully";
	}
	
	public String updateAuthor(int id,Author author) {
		Author author2 =authorRepository.findById(id).get();	
		author2.setBooks(author.getBooks());
		author2.setEmail(author.getEmail());
		author2.setName(author.getEmail());
		return "Author updatet";
	} 
	
	public String addBookFromAuthor(int id,Book book) {
		Author author =authorRepository.findById(id).get();	
       author.getBooks().add(book);
       authorRepository.save(author);
       bookRepository.save(book);
   
       return "Book addedd to Author with id "+id;
		
	}

	public String updateAuthorsBook(int authorId,int bookId, Book book) {
		Author author =authorRepository.findById(authorId).get();	
		Book book2 = new Book();
		int index=-1;
		for(int i=0;i<author.getBooks().size();i++) {
			if(author.getBooks().get(i).getId()==bookId) {
				book2 = author.getBooks().get(i);
				index=i;
			}
		}
		book2.setId(bookId);
		book2.setTitle(book.getTitle());
		
		book2.setPublicationYear(book.getPublicationYear());
		
		
		author.getBooks().remove(index);
		
		author.getBooks().add(index, book2);
		
		
		bookRepository.save(book2);		
		
		 
		authorRepository.save(author);
		
		return "Book with id "+book2.getId()+" and PublicationYear : "+book2.getPublicationYear()+" Title : "+book2.getTitle();

	}
public String addBookToAuthor(int authorId,BookModel bookModel) {
		
		Author author = authorRepository.findById(authorId).get();
		Book book=new Book();
		book.setPublicationYear(bookModel.publicationYear);
	    book.setTitle(bookModel.title);
		book.setPages(bookModel.pages);
		book.setAuthor(author);
		
		
		author.addBook(book);
		
		bookRepository.save(book);
		
		authorRepository.save(author);

		return bookModel.toString();

	}

public List<BookModel> addBooksToAuthor(int authorId,List<BookModel> listBooks){
	Author author = authorRepository.findById(authorId).get();
	for(BookModel bookModel: listBooks) {
		Book b = new Book(bookModel.publicationYear, bookModel.title, bookModel.pages,bookModel.genre, author); 
		author.addBook(b);
		bookRepository.save(b);
		authorRepository.save(author);

	}
	return listBooks;
}
	public String deleteAllAuthors() {
		authorRepository.deleteAll();
		
		return "All the authors are deleted";
	}
	
	public List<AuthorModel> filterAuthor(RequestDTO requestDTO) {
		return authorRepository.findAll(authorsFilter.getSpecification(requestDTO)).stream().map(e -> new AuthorModel(e.getId(),e.getName(),e.getEmail())).toList();
		
	}
}
