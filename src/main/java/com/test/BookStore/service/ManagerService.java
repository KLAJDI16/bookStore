package com.test.BookStore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.test.BookStore.dto.BookListToLibraryRequest;
import com.test.BookStore.dto.BookQuantity;
import com.test.BookStore.models.*;
import com.test.BookStore.dto.BooksTransferModel;
import com.test.BookStore.dto.LibraryBookInfo;
import com.test.BookStore.dto.ManagerRequest;
import com.test.BookStore.dto.RegisterModel;
import com.test.BookStore.dto.RequestDTO;
import com.test.BookStore.entities.Book;
import com.test.BookStore.entities.Combination;
import com.test.BookStore.entities.Library;
import com.test.BookStore.entities.LibraryBook;
import com.test.BookStore.entities.LibrarySales;
import com.test.BookStore.entities.Student;
import com.test.BookStore.entities.Transaction;
import com.test.BookStore.filters.SearchFilters;
import com.test.BookStore.repo.BookRepository;
import com.test.BookStore.repo.CombinationRepository;
import com.test.BookStore.repo.LibraryBookRepository;
import com.test.BookStore.repo.LibraryRepository;
import com.test.BookStore.repo.LibrarySalesRepository;
import com.test.BookStore.repo.RoleRepository;
import com.test.BookStore.repo.StudentRepository;
import com.test.BookStore.repo.TransactionRepository;
@Service
public class ManagerService {
	@Autowired
	private  BookRepository bookRepository;
	@Autowired
	private  StudentRepository studentRepository;
	@Autowired
	private  LibraryRepository libraryRepository;
	@Autowired
	private  LibraryBookRepository libraryBookRepository;	
	@Autowired
	private MethodService methodService;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private SearchFilters<Transaction> transactionFilter;
	@Autowired
	private SearchFilters<LibrarySales> librarySalesFilter;
	@Autowired
	private JavaMailSender javaMailSender;	
//	@Autowired
//	private SpecialBooksCombinationRepository specialBooksCombinationRepository;
	@Autowired
	private LibrarySalesRepository librarySalesRepository;
	
	@Autowired
	private CombinationRepository combinationRepository;
	
	public List<BookQuantity<BookModel>> addMultipleBooksToLibrary(BooksTransferModel booksTransferModel){
		boolean validation = validateManager(booksTransferModel.registerModel);
		if(!validation) { 
			methodService.throwResponseException(HttpStatus.BAD_REQUEST, "Validation Failed");
		}
		BookListToLibraryRequest request = booksTransferModel.bookListToLibraryRequest;

			Library library =(Library) methodService.getElementFromList(libraryRepository.findByNameIgnoreCase(request.toLibrary), HttpStatus.NO_CONTENT, "There is no library with this name");
			
			for(BookQuantity<String> bookQuantity : request.bookQuantity) {
				Book book = (Book) methodService.getElementFromList(bookRepository.findByTitleIgnoreCase(bookQuantity.book), HttpStatus.NO_CONTENT, "There is no Book with name : "+bookQuantity.book);
				
				List<LibraryBook> libraryBooks = libraryBookRepository.findByLibraryAndBook(library, book);
				if(libraryBooks.isEmpty()) {
					libraryBookRepository.save(new LibraryBook(book, library,bookQuantity.quantity));
					libraryRepository.save(library);
				}
				else {
					LibraryBook libraryBook = libraryBooks.get(0);
					libraryBook.setQuantity(libraryBook.getQuantity()+bookQuantity.quantity);
					libraryBookRepository.save(libraryBook);
				}
			}
			return libraryBookRepository.findByLibrary(library).stream().map(e -> new BookQuantity<BookModel>(e.getBook().getBookModel(),e.getQuantity())).toList();
		}
		
	public List<BookQuantity<BookModel>> removeBooksFromLibrary(BooksTransferModel booksTransferModel){
		boolean validation = validateManager(booksTransferModel.registerModel);
		if(!validation) {
			methodService.throwResponseException(HttpStatus.NOT_FOUND, "Validation Failed"); 
		}
		
	Library library = (Library) 
	methodService.getElementFromList(libraryRepository.findByNameIgnoreCase
	(booksTransferModel.bookListToLibraryRequest.fromLibrary), 
	 HttpStatus.BAD_REQUEST, "There is no library with this name");
		
for(BookQuantity<String> bookQuantity : booksTransferModel.bookListToLibraryRequest.bookQuantity) {

Book book = methodService.getElementFromList(bookRepository.findByTitleIgnoreCase
		(bookQuantity.book),HttpStatus.BAD_REQUEST,
		"There is no Book with name : "+bookQuantity.book);
					
			
List<LibraryBook> libraryBooks = libraryBookRepository.findByLibraryAndBook(library, book);
		if(libraryBooks.isEmpty()) {
methodService.throwResponseException(HttpStatus.BAD_REQUEST, "There is no libraryBook with name : "+bookQuantity.book);		
			}
			else {
				LibraryBook libraryBook = libraryBooks.get(0);
				int count=libraryBook.getQuantity()-bookQuantity.quantity;
				if(count<0) {
methodService.throwResponseException(HttpStatus.BAD_REQUEST, "Negative quantity" );						
				}
				else if(count == 0 ) {
					for(Student st : studentRepository.findAll()) {
	//					if(st.getRole() == Role.MANAGER)
					methodService.sendMail(st.getEmail(), "Book Information", "The quantity of the book "+bookQuantity.book+" is 0 in library "+libraryBook.getLibrary().getName());
				}
				}
				libraryBook.setQuantity(count);
				libraryBookRepository.save(libraryBook);
			}
		}
		return libraryBookRepository.findByLibrary(library).stream().map(e -> new BookQuantity<BookModel>(e.getBook().getBookModel(),e.getQuantity())).toList();
	}

	public String transferBooksToLibrary(BooksTransferModel booksTransferModel) {
		
		boolean validation = validateManager(booksTransferModel.registerModel);
		if(!validation) return "Validation of the User failed";
		
		List<Library> libraries = libraryRepository.findByNameIgnoreCase(booksTransferModel.bookListToLibraryRequest.fromLibrary);
		if(libraries.isEmpty()) {
methodService.throwResponseException(HttpStatus.NOT_FOUND, "There was no libary found" );					
		}
		LibraryBook libraryBook = libraryBookRepository.findByLibrary(libraries.get(0)).get(0);
		BookListToLibraryRequest request = new BookListToLibraryRequest();
//		request.libraryName = booksTransferModel.fromLibrary;
//		request.bookQuantity = booksTransferModel.bookListToLibraryRequest.getBookQuantity();
		removeBooksFromLibrary(booksTransferModel);
		addMultipleBooksToLibrary(booksTransferModel);
		return "Transfer completed sucessfully";
	}
	
	public LibraryBookModel updateLibrariesBookInfo(ManagerRequest<LibraryBookInfo> managerRequest) {
		if(!validateManager(managerRequest.registerModel)) {
methodService.throwResponseException(HttpStatus.BAD_REQUEST, "Wrong Email or Password " );					
		}
		
		LibraryBook libraryBook = libraryBookRepository.findById(managerRequest.request.libraryBookId).orElse(null);
		if(libraryBook==null) {
methodService.throwResponseException(HttpStatus.BAD_REQUEST, "Wrong id" );					
		}
		libraryBook.setBookInfo(managerRequest.request.bookInfo);
		libraryBookRepository.save(libraryBook);
		return libraryBook.getLibraryBookModel();
	}
	public boolean validateManager(RegisterModel registerModel) {
Student user = methodService.getElementFromList(studentRepository.findByEmailAndPassword
(registerModel.email, registerModel.password), HttpStatus.NOT_FOUND, "There were no users found with this email and password");
		if(user.getRole().equals("MANAGER")) {
			return true;
		}
		return false;
}
	public String addSpecialBooksCombination(ManagerRequest<CombinationModel<String>> managerRequest){
		if(!validateManager( managerRequest.registerModel)) {
methodService.throwResponseException(HttpStatus.NOT_FOUND, "There were no managers found with this email and password");			
		}
Library library =methodService.getElementFromList(libraryRepository.findByNameIgnoreCase
(managerRequest.request.libraryName), HttpStatus.NOT_FOUND, "There were no library found");
		List<Book> books=new ArrayList<Book>();
		for(String str : managerRequest.request.bookList)		{
		Book book =methodService.getElementFromList(bookRepository.findByTitleIgnoreCase(str)
	, HttpStatus.NOT_FOUND, "There were no book found");;
		List<LibraryBook> libraryBooks = libraryBookRepository.findByLibraryAndBook(library, book);
		if(libraryBooks.isEmpty() || libraryBooks.get(0).getQuantity()<=0){
methodService.throwResponseException(HttpStatus.NOT_FOUND, "There were no book with title "+str+" in library "+library.getName());			
		}
		books.add(book);
		}
		if(managerRequest.request.specialDiscount<0 || managerRequest.request.specialDiscount>=30) {
methodService.throwResponseException(HttpStatus.NOT_FOUND, "Cannot set this offer");			
		}
		
		Combination combination = new Combination(books,managerRequest.request.specialDiscount);
		combination.setLibrary(library);
		combinationRepository.save(combination);
		return "Combination added successfully";
	}

	public String removeSpecialBooksCombination(ManagerRequest<Integer> managerRequest){
		if(!validateManager( managerRequest.registerModel)) {
methodService.throwResponseException(HttpStatus.NOT_FOUND,"There were no users found with this email and password");			
		}
	//	SpecialBooksCombination specialBooksCombinations =  specialBooksCombinationRepository.findById(managerRequest.request).orElseGet(null);

//		if(specialBooksCombinations==null) {
//methodService.throwResponseException(HttpStatus.BAD_REQUEST,"Wrong id for special combinations");			
//		}
//		else {specialBooksCombinationRepository.delete(specialBooksCombinations);}
		
		Combination combination = combinationRepository.findById(managerRequest.request).orElse(null); 
		if(combination==null)	{methodService.throwResponseException(HttpStatus.BAD_REQUEST, "No combination was found . Wrong combination id");}
		combinationRepository.deleteById(managerRequest.request);
		return "Combination was   found";
	}
	public List<FaturaModel> getTransactionsByFilter(ManagerRequest<RequestDTO> managerRequest) {
		if(!validateManager( managerRequest.registerModel)) {
			methodService.throwResponseException(HttpStatus.NOT_FOUND,"There managers no users found with this email and password");			
					}
		if(managerRequest.request == null) {
			System.err.println("managerRequest.request is null");
			}
		return transactionRepository.findAll(transactionFilter.getSpecification(managerRequest.request), Sort.by("id").ascending()).stream().map(e -> e.getFaturaModel()).toList();
	}
	
	public List<LibrarySalesModel> retrieveAllLibrarySales(ManagerRequest<RequestDTO> managerRequest){
		if(!validateManager( managerRequest.registerModel)) {
			methodService.throwResponseException(HttpStatus.NOT_FOUND,"There managers no users found with this email and password");			
					}
		List<LibrarySales> ls = librarySalesRepository.findAll(librarySalesFilter.getSpecification(managerRequest.request), Sort.by("id").ascending());
		
		return ls.stream().map(e -> e.getLibrarySalesModel()).toList();
		
	}
	
	public List<LibraryBookModel> managerRetrieveAllLibraryBooks(ManagerRequest<String> managerRequest){
		if(!validateManager(managerRequest.registerModel)) {
			methodService.throwResponseException(HttpStatus.BAD_REQUEST, "Wrong email or password for manager login");
		}
		Library library = methodService.getElementFromList(libraryRepository.findByNameIgnoreCase
				(managerRequest.request), HttpStatus.BAD_REQUEST, "No Library exists with this name");
		
		List<LibraryBook> libraryBooks = libraryBookRepository.findByLibrary(library);
 		return libraryBooks.stream().map(e -> e.getLibraryBookModel()).toList();

	}
	

	
	
	
	
	
	
	
	
	
	
}
