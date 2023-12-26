package com.test.BookStore.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
////
import org.springframework.web.server.ResponseStatusException;

import com.test.BookStore.dto.RequestDTO;
import com.test.BookStore.dto.SearchRequestDTO;
import com.test.BookStore.entities.Author;
import com.test.BookStore.entities.Book;
import com.test.BookStore.entities.LibraryBook;
import com.test.BookStore.dto.BookOptionRequest;
import com.test.BookStore.dto.BookQuantity;
import com.test.BookStore.dto.BooksTransferModel;
import com.test.BookStore.dto.LibraryBookInfo;
import com.test.BookStore.dto.ManagerRequest;
//import com.test.BookStore.dto.BookLibraryRequestDTO;
import com.test.BookStore.dto.BookListToLibraryRequest;
import com.test.BookStore.dto.RegisterModel;
import com.test.BookStore.dto.RequestDTO;
import com.test.BookStore.models.*;
import com.test.BookStore.dto.SearchRequestDTO;
import com.test.BookStore.service.AuthorService;
import com.test.BookStore.service.BookService;
import com.test.BookStore.service.ManagerService;
import com.test.BookStore.service.ProgramService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import com.test.BookStore.entities.Student;


@RestController
//@ComponentScan(basePackages = {"service","entities","repository"})
public class BookStoreController {

	@Autowired
   public ProgramService programService;
	
	@Autowired
	public BookService bookService;
	
	@Autowired
	public AuthorService authorService;
	@Autowired
	public ManagerService managerService;

	@GetMapping("/books")
//	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public List<BookModel> getRetrieveBooks(){
	
		List<BookModel> books=  bookService.retrieveBooks();
		
//		if(books.size()==0) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"There are no books stores " );
//		}
		return books;	
	}
	@PostMapping("/books")
//	@ResponseStatus(value = HttpStatus.CREATED)
  public BookModel postNewBook(@RequestBody Book book) {
		
//		if(book==null) throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,"You should post a book" ); 
		return bookService.addNewBook(book);
	}
	
	@PutMapping("/books/{id}")
	//@ResponseStatus(value = HttpStatus.OK)
      public BookModel putUpdateBook(@PathVariable("id") int id,@RequestBody Book book) {
		
		return bookService.updateBook(id, book);
		
	}
	
	@DeleteMapping("/books/{id}")
	
	public String deleteBookById(@PathVariable("id") int id) {

		return bookService.deleteBook(id);

	}
	
	@GetMapping("/books/{id}")
	public String getBooksDetails(@PathVariable("id") int id) {
		// TODO Auto-generated method stub
		return bookService.getBookDetails(id);
	}
	@GetMapping("/api")
	public String getString() {
		return "Hello world";
	}


@GetMapping("/author_books/{id}")
   public List<BookModel> getAuthorsBook(@PathVariable("id") int id){	
	return authorService.authorsBook(id);
}

@PostMapping("/author")
public String postAuthor(@RequestBody Author author){	
	return authorService.createAuthor(author);
}
@GetMapping("/author")
public List<AuthorModel> getAllAuthors(){	
	return authorService.getAuthors();
}

@GetMapping("/author/{id}")
	public String getAuthorByID(@PathVariable("id") int id){	
		return authorService.getAuthorById(id);
	}

@GetMapping("/author_name/{name}")
public String getAuthorByIs(@PathVariable("name") String name){	
	return (String) authorService.getAuthorByName(name);
}

@GetMapping("/delete_books")
public String delete_booksAll(){	
	return bookService.deleteAllBooks();
}

@GetMapping("/book_title/{title}")
public List<BookModel> getBookByTitle(@PathVariable("title") String title) {
	return bookService.getBookByTitle(title);
}

@DeleteMapping("author/{id}")
public String deleteAuthorById(@PathVariable("id") int id) {
return	authorService.deleteAuthor(id);
}

@PutMapping("author/{id}")
public String putupdateAuthor(@PathVariable("id") int id,@RequestBody Author author) {

return authorService.updateAuthor(id, author);
}

@PutMapping("author_update_book/{authorId}/{bookId}")
public String updateAuthorsBook(@PathVariable("authorId") int authorId,@PathVariable("bookId") int bookId,@RequestBody Book book) {
	return authorService.updateAuthorsBook(authorId, bookId, book);
}

@PostMapping("author_add_book/{authorId}")
public String addAuthorsBook(@PathVariable("authorId") int authorId,@RequestBody BookModel book) {
	return authorService.addBookToAuthor(authorId, book);
}
@PostMapping("author_add_books/{authorId}")
public List<BookModel> addAuthorsBooks(@PathVariable("authorId") int authorId,@RequestBody List<BookModel> listBooks) {
	return authorService.addBooksToAuthor(authorId, listBooks);
}

@DeleteMapping("/authors_deleteAll")
public String deleteAllAuthors() {
	return authorService.deleteAllAuthors();
}

public List<AuthorModel> filterAuthor(@RequestBody RequestDTO response) {
	
	return authorService.filterAuthor(response);
 	}

public List<BookModel> filterBook(@RequestBody RequestDTO response) {

return bookService.filterBook(response);
	}

@GetMapping("/student")
//@ResponseStatus(value = HttpStatus.ACCEPTED)
public List<StudentModel> Student(@RequestBody RegisterModel registerModel){
	return programService.retrieveAllStudent(registerModel);	
}
@PostMapping("/student")
public String postStudent(@RequestBody Student student) {	
	return programService.registerStudent(student);
}

//@PostMapping("/library_addBook")
//public List<BookModel> addBookToLibrary(@RequestBody BookLibraryRequestDTO bookToLibraryRequest) {
//return programService.addBookToLibrary(bookToLibraryRequest);
//}
@PostMapping("/library_addMultipleBooks")
public List<BookQuantity<BookModel>> addMultipleBooksToLibrary(@RequestBody BooksTransferModel request){
	return managerService.addMultipleBooksToLibrary(request);
}
@PostMapping("/library_updateBooksInfo")
public LibraryBookModel updateLibrariesBookInfo(@RequestBody ManagerRequest<LibraryBookInfo> managerRequest) {
	return managerService.updateLibrariesBookInfo(managerRequest);
}
//public updateLibrariesBookInfo()
		
@PostMapping("/library_removeMultipleBooks")
public List<BookQuantity<BookModel>> removeBooksFromLibrary(@RequestBody BooksTransferModel request){
	return managerService.removeBooksFromLibrary(request);
}
@PostMapping("/library_transferMultipleBooks")
public String transferBooksToLibrary(@RequestBody BooksTransferModel request){
	return managerService.transferBooksToLibrary(request);
}
@GetMapping("/library/{name}")
public List<BookQuantity<BookModel>> retrieveLibraryBooks(@PathVariable("name") String name){
	return programService.retrieveLibraryBooks(name);
}
//@PostMapping("/student_get_book/{student_id}")
//public String studentAddBookFromLibrary(@PathVariable("student_id") int student_id,@RequestBody BookLibraryRequestDTO request) {
//	return programService.studentAddBook(student_id,request);
//}
/*@GetMapping("/student/{id}/books")
public List<BookQuantity<BookModel>> studentsBooks(@PathVariable("id") int id){
	return programService.getStudentsBooks(id);
}
*/
@GetMapping("/student/{student_id}/library")
public List<LibraryBookModel> studentViewBooksAtLibrary(@PathVariable("student_id") int student_id,@RequestBody BookOptionRequest bookOptionRequest ) {
	return programService.studentViewBooksAtLibrary(student_id, bookOptionRequest);
}
@PostMapping("/student/{student_id}/library")
public FaturaModel studentGetBookFromLibrary(@PathVariable("student_id") int student_id,@RequestBody BookOptionRequest bookOptionRequest ) {
	return programService.studentGetBookFromLibrary(student_id,  bookOptionRequest);
}
@PostMapping("/manager/addCombination")
public String addSpecialBooksCombination(@RequestBody ManagerRequest<CombinationModel<String>> managerRequest) {
	return managerService.addSpecialBooksCombination(managerRequest);
}
@PostMapping("/manager/removeCombination")
public String removeSpecialBooksCombination(@RequestBody ManagerRequest<Integer> managerRequest) {
	return managerService.removeSpecialBooksCombination(managerRequest);
}
@GetMapping("/specialCombinations/{name}")
public List<CombinationModel<BookModel>> retrieveAllLibrariesSpecialOffers(@PathVariable("name") String libraryName){
return programService.retrieveAllLibrariesSpecialOffers(libraryName);
}
@GetMapping("/manager/retrieveTransactions")
public List<FaturaModel> getTransactionsByFilter(@RequestBody ManagerRequest<RequestDTO> managerRequest){
	return managerService.getTransactionsByFilter(managerRequest);
}
@GetMapping("/manager/retrieveLibrarySales")
public List<LibrarySalesModel> retrieveAllLibrarySales(@RequestBody ManagerRequest<RequestDTO> managerRequest){
return managerService.retrieveAllLibrarySales(managerRequest);
}
@GetMapping("/student/libraryBooks/{libraryName}")
public List<BookModel> studentRetrieveAllLibraryBooks(@PathVariable String libraryName){
return programService.studentRetrieveAllLibraryBooks(libraryName);
}
@GetMapping("/manager/libraryBooks")
public List<LibraryBookModel> studentRetrieveAllLibraryBooks(@RequestBody ManagerRequest<String> managerRequest){
return managerService.managerRetrieveAllLibraryBooks(managerRequest);
}
}

