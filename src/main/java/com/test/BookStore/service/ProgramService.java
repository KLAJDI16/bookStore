package com.test.BookStore.service;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

//import com.test.BookStore.dto.BookLibraryRequestDTO;
import com.test.BookStore.dto.BookListToLibraryRequest;
import com.test.BookStore.dto.BookOptionRequest;
import com.test.BookStore.dto.BookQuantity;
import com.test.BookStore.dto.BooksTransferModel;
import com.test.BookStore.dto.ManagerRequest;
import com.test.BookStore.dto.RegisterModel;
import com.test.BookStore.dto.RequestDTO;
import com.test.BookStore.dto.SearchRequestDTO;
import com.test.BookStore.models.*;
import com.test.BookStore.filters.SearchFilters;
import com.test.BookStore.repo.AuthorRepository;
import com.test.BookStore.repo.BookCostTransactionRepository;
import com.test.BookStore.repo.BookRepository;
import com.test.BookStore.repo.BookSalesRepository;
import com.test.BookStore.repo.CombinationRepository;
import com.test.BookStore.repo.LibraryBookRepository;
import com.test.BookStore.repo.LibraryRepository;
import com.test.BookStore.repo.LibrarySalesRepository;
//import com.test.BookStore.repo.SpecialBooksCombinationRepository;
import com.test.BookStore.repo.StudentRepository;
import com.test.BookStore.repo.TransactionRepository;
import com.test.BookStore.entities.*;
import com.test.BookStore.entities.Role;

@Service
public class ProgramService {
	@Autowired
	private  BookRepository bookRepository;
	@Autowired
	private  StudentRepository studentRepository;
	@Autowired
	private  LibraryRepository libraryRepository;
	@Autowired
	private  LibraryBookRepository libraryBookRepository;
//
//	@Autowired
//	private SpecialBooksCombinationRepository specialBooksCombinationRepository;
	@Autowired
	private CombinationRepository combinationRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private BookCostTransactionRepository bookCostTransactionRepository;
	@Autowired
	private BookSalesRepository bookSalesRepository;
	@Autowired
	private LibrarySalesRepository librarySalesRepository;
	
	@Autowired
	private ManagerService  managerService;
	@Autowired
	private MethodService methodService;
	@Autowired
	private SearchFilters<Book> booksFilter;
	
	@Autowired
	private SearchFilters<LibraryBook> libraryBooksFilter;

	public String registerStudent(Student student) {
//		student.role=Role.USER;
		studentRepository.save(student);
		
		return student.getStudentModel().toString()+" Registered Successfullly";
	}
	
	public List<StudentModel> retrieveAllStudent(RegisterModel registerModel) {
		if(managerService.validateManager(registerModel)) {
		
		return studentRepository.findAll().stream().map(e -> e.getStudentModel()).toList();
		}
		return null;
	}

	public List<BookQuantity<BookModel>> retrieveLibraryBooks(String name){
		Library library2 =  libraryRepository.findByNameIgnoreCase(name).get(0);
		
		List<LibraryBook> libraryBooks =  libraryBookRepository.findByLibrary(library2);

		return libraryBooks.stream().map(e -> new BookQuantity<BookModel>(e.getBook().getBookModel(),e.getQuantity())).toList();
	}

	/*public  List<BookQuantity<BookModel>> getStudentsBooks(int id){
		Student student = studentRepository.findById(id).get();
	  //  return studentBookLibraryRepository.findByStudent(student).stream().map(e -> new BookQuantity<BookModel>(e.getBook().getBookModel(),e.getQuantity())).toList();
	
	}
	*/
public List<LibraryBookModel> studentViewBooksAtLibrary(int student_id,BookOptionRequest bookOptionRequest){
	String libraryName = bookOptionRequest.libraryName;
    List<LibraryBook> studentsBook = getBooksAtLibraryWithFilter(student_id,  bookOptionRequest);
	
    return studentsBook.stream().map(e -> e.getLibraryBookModel()).toList();
}

public FaturaModel studentGetBookFromLibrary(int student_id,BookOptionRequest bookOptionRequest) {
		
		String libraryName = bookOptionRequest.libraryName;
         
	    List<LibraryBook> studentsBook = getBooksAtLibraryWithFilter(student_id,  bookOptionRequest);;
	    Library library = studentsBook.get(0).getLibrary();
	   	    
	    List<BookQuantity<String>> list = bookOptionRequest.bookQuantityList;
		Student student = studentRepository.findById(student_id).get();
		//--------------------------
		double totalMoney = 0;
		int totalQuantity=0;
		int specialCombinationDiscount = 0;
		int specialCostumerDiscount = 0;
		//-----------------------------------
		Transaction transaction = new Transaction();
		transaction.setDate( LocalDateTime.now());
		transaction.setLibrary(library);
		transaction.setStudent(student);
		List<BookCostTransaction> bookCostTransactions = new ArrayList<>();
	    for(BookQuantity<String> bookQuantity : list) {
	    	BookCostTransaction bookCostTransaction = new BookCostTransaction();
	    	LibraryBook libraryBook = findLibraryBookByLibraryAndName(studentsBook,libraryName,bookQuantity.book);
	    	BookInfo bookInfo = libraryBook.getBookInfo();
	    	bookCostTransaction.setOfferQuantity(libraryBook.getBookInfo().getOfferQuantity());
	    	bookCostTransaction.setDiscount(libraryBook.getBookInfo().getDiscountPercent());
	    	
	    	int count = libraryBook.getQuantity()-bookQuantity.quantity;
	    	if(count < 0) {
	    		methodService.throwResponseException(HttpStatus.BAD_REQUEST, "Negative quantity");
	    	}else if(count==0) {
	    		for(Student st : studentRepository.findAll()) {
	    			if(st.getRole().getName().equals("MANAGER")) {
	    		methodService.sendMail(st.getEmail(), "BookInformation", "Book with title "+bookQuantity.book
	    				+" Has reached quantity 0");
	    	}
	    	}
	    	}
	    	bookCostTransaction.setQuantity(bookQuantity.quantity);
	    	bookCostTransaction.setBook(libraryBook.getBook());
	    	totalQuantity+=bookQuantity.quantity;
	    	bookCostTransaction.setPricePerBook(bookInfo.getPrice());
	    	libraryBook.setQuantity(count);
	    	bookInfo.addAmountSold(bookQuantity.quantity);
	    	if(bookInfo.amountSold>=bookInfo.highRequestAmount) {
	    		for(Student st : studentRepository.findAll()) {
	    			if(st.getRole().getName().equals("MANAGER")) {
	    		methodService.sendMail(st.getEmail(), "Book Information", "Book with title "+bookQuantity.book
	    				+" Has reached highRequestAmount " +bookInfo.highRequestAmount);
	    	}
	    		}
	    	}
	    	//libraryBookRepository.save(libraryBook);
	    	double moneySpent = bookInfo.getPrice()*bookQuantity.quantity;
	    	bookCostTransaction.setTotalPrice(moneySpent);
	    	
	    	if(bookQuantity.quantity>=bookInfo.getOfferQuantity() ) {
	    		moneySpent = moneySpent -  ((bookInfo.getDiscountPercent()/100)*moneySpent);
	    	}
	    	bookCostTransaction.setCost(moneySpent);
	    	
	    	bookInfo.addRevenue(moneySpent);
	    	libraryBook.setBookInfo(bookInfo);
	    	totalMoney+=moneySpent;
	    	totalQuantity+=bookQuantity.quantity;

    		libraryBookRepository.save(libraryBook);
    		bookCostTransactions.add(bookCostTransaction);
    		transaction.addBookCost(bookCostTransaction);
	    }
	    bookCostTransactionRepository.saveAll(bookCostTransactions);
	    if(bookInSpecialCombination(list, library)>0) {
	    	transaction.setSpecialCombinationDiscount(bookInSpecialCombination(list, library));
	    	totalMoney = totalMoney -  ((bookInSpecialCombination(list, library)/100)*totalMoney);
	    }
	    transaction.setSpecialCombinationCost(totalMoney);
	    if(checkForTopCustomer( student, library, 3)==true) {
	    	transaction.setSpecialCostumerDiscount(5);
	    	totalMoney = totalMoney -  (0.05)*totalMoney;
	    }
	    transaction.setSpecialCostumerCost(totalMoney);
	    transaction.setFinalCost(totalMoney);
	    transaction.setTotalQuantity(totalQuantity);
	    transactionRepository.save(transaction);
    	System.err.println(transaction.getFaturaModel().toString());
    	methodService.sendMail(student.getEmail(), "Fatura juaj ", transaction.getFaturaModel().toString());
	    studentRepository.save(student);
	    return transaction.getFaturaModel();
}
public List<LibraryBook> getBooksAtLibraryWithFilter(int student_id,BookOptionRequest bookOptionRequest){
		Student student = studentRepository.findById(student_id).get();
		if(student==null) {
    		methodService.throwResponseException(HttpStatus.BAD_REQUEST, "There is no student with id : "+student_id);
		}

		List<Library> libraries = libraryRepository.findByNameIgnoreCase(bookOptionRequest.libraryName);
		
		if(libraries.size()==0) {
    		methodService.throwResponseException(HttpStatus.BAD_REQUEST,"There is no library with name : "+bookOptionRequest.libraryName);
		}
		Library library = libraries.get(0);
		checkTopCustomer(student, library, 3);
		RequestDTO  requestDTO = bookOptionRequest.requestDTO;
		List<LibraryBook> libraryBooks =  libraryBookRepository.findByLibrary(library);
		  if(libraryBooks.size()<=0) {
	    		methodService.throwResponseException(HttpStatus.NO_CONTENT,"There is no library with that  Name  : ");
		    }
		List<LibraryBook> libraryBooks2 = libraryBookRepository.findAll(libraryBooksFilter.libraryBookSpecification(requestDTO));
		List<Book> books = bookRepository.findAll(libraryBooksFilter.bookSpecification(requestDTO));

	    List<LibraryBook> libraryBooks3 = new ArrayList<>();
	    libraryBooks3 = libraryBooks.stream().filter(e -> libraryBooks2.contains(e)).filter(e -> books.contains(e.getBook())).filter(e -> e.getQuantity()>0).toList();
	    		if(libraryBooks3.size()<=0) {
		    		methodService.throwResponseException(HttpStatus.NO_CONTENT,"There were no books found in  library");
	    }
	    return libraryBooks3;
	}
	

	
public boolean checkTopCustomer(Student student,Library library,int n) {
	
    Sort sort = Sort.by(Sort.Direction.DESC, "name");

	List<Transaction> transactions  = transactionRepository.findByLibrary(library);
	
	List<Transaction> transactions2 = transactionRepository.findByLibraryAndStudent(library, student);
	if(transactions.isEmpty() || transactions2.isEmpty()) return false;
	HashMap<Student,Double> map =new HashMap<>();
	if(transactions.isEmpty() || transactions2.isEmpty()) return false;
	for(int i=0;i<transactions.size();i++) {
		Transaction transaction = transactions.get(i);
		
		if(!map.containsKey(transaction.getStudent())) {
			map.put(transaction.getStudent(), transaction.getFinalCost());
		}
		else {
			Double cost = map.get(transaction.getStudent());
			map.put(transaction.getStudent(),cost + transaction.getFinalCost());
		}
		}
	Student[] students = new Student[map.size()];
	Double[] costs = new Double[map.size()];
	int in = 0;
	for(Student st : map.keySet()) {
		students[in] = st;
		costs[in] = map.get(st);
		in++;
	}
	for(int i=0 ; i< students.length-1;i++) {
		Double max = Double.MIN_VALUE;
		int index = -99;
		for(int j=i+1;j<students.length;j++) {
			if(costs[j]>=max) {
				index=j;
				max = costs[j];
			}
		}
		if(index>0 && max>=costs[i]) {
			swap(costs,i,index);
			swap(students,i,index);
		}
	}
	for(int i=0;i<n && i<students.length;i++) {
		if(students[i].equals(student)) {			
			return true;
		}
	}
	return false;
}
public <T> void swap(T[] arr,int i,int j){
	T temp = arr[i];
	arr[i] = arr[j];
	arr[j] = temp;
}


public LibraryBook findLibraryBookByLibraryAndName(List<LibraryBook> libraryBooks ,String libraryName,String bookTitle) {
	List<LibraryBook> libraryBook = libraryBooks.stream().filter(e -> e.getLibraryName().equals(libraryName)).filter(e -> e.getBookTitle().equals(bookTitle)).toList();
    
	if(libraryBook.isEmpty()==false) return libraryBook.get(0);
	else {
		methodService.throwResponseException(HttpStatus.NO_CONTENT,"No libraryBook is Present ");
return null;
	}
}

public double bookInSpecialCombination(List<BookQuantity<String>> bookQuantities,Library library) {
	List<Book> books = new ArrayList<>();
	for(BookQuantity<String> bookQuantity : bookQuantities) {
		Book book2 = bookRepository.findByTitleIgnoreCase(bookQuantity.book).get(0);
		books.add(book2);
	}
	// Algjeber,Gjeometri,Biologji,Matematik
	//Algjeber,Gjeometri,Fizik,Matematik
//	SpecialBooksCombination specialBooksCombination =   specialBooksCombinationRepository.findByLibrary(library).get(0);
//	List<Combination> combinations = specialBooksCombination.getCombinations();
	List<Combination> combinations = combinationRepository.findAll().stream().filter(e -> e.getLibrary().equals(library)).toList();
	boolean check = true;
	double discount = -20;
	for(Combination combination: combinations) {
		check=true;
		for(Book book : combination.getBooks()) {
			if(!books.contains(book)) {
				check=false;
				break;
			}
		}
		if(check==true) {
			discount=0;
			discount+=combination.getSpecialDiscount();
			break;
		}
	}
	return discount;
}


public boolean checkForTopCustomer(Student student, Library library, int n) {
    List<Transaction> transactions = transactionRepository.findByStudent(student);

    // Filter transactions for the specified library
    transactions = transactions.stream()
            .filter(transaction -> transaction.getLibrary().equals(library))
            .collect(Collectors.toList());

    // Combine finalCosts for the same student
    double totalFinalCost = transactions.stream()
            .mapToDouble(transaction -> transaction.getFinalCost())
            .sum();

    // Get the top N students based on totalFinalCost
    List<Student> topStudents = transactionRepository.findAll()
            .stream()
            .collect(Collectors.groupingBy(transaction -> transaction.getStudent(), Collectors.summingDouble(transaction -> transaction.getFinalCost())))
            .entrySet()
            .stream()
            .sorted((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue())) // Sort in descending order
            .limit(n)
            .map(entry -> entry.getKey())
            .collect(Collectors.toList());
    // Check if the given student is in the top N students
    return topStudents.contains(student);
}
public List<CombinationModel<BookModel>> retrieveAllLibrariesSpecialOffers(String libraryName){
	Library library = methodService.getElementFromList(
			libraryRepository.findByNameIgnoreCase(libraryName)
			, HttpStatus.BAD_REQUEST, "Wrong name for library");
List<Combination> combinations = combinationRepository.findAll().stream().filter(e -> e.getLibrary().equals(library)).toList();
List<CombinationModel<BookModel>> returnList = new ArrayList<>();

for(Combination c : combinations) {
	CombinationModel<BookModel> combinationModel = new CombinationModel<BookModel>();
	combinationModel.libraryName = libraryName;
	combinationModel.bookList = c.getBooks().stream().map(e -> e.getBookModel()).toList();
	combinationModel.specialDiscount = c.getSpecialDiscount();
	
	returnList.add(combinationModel);	
}
//	List<SpecialBooksCombination> specialBooksCombinations = specialBooksCombinationRepository.findByLibrary(library);
//       List<CombinationModel<BookModel>> list = new ArrayList<>();
//for(SpecialBooksCombination spc : specialBooksCombinations) {
//	for(Combination combation : spc.getCombinations()) {
//		CombinationModel<BookModel> model = new CombinationModel<BookModel>();
//		model.libraryName = libraryName;
//		model.bookList = combation.getBooks().stream().map(e -> e.getBookModel()).toList();
//		model.specialDiscount = combation.getSpecialDiscount();
//		list.add(model);
//	}
//}
return returnList;
}
//public double bookInSpecialCombinations(List<BookQuantity<String>> bookQuantities,Library library) {
//	List<Book> books = new ArrayList<>();
//	for(BookQuantity<String> bookQuantity : bookQuantities) {
//		Book book2 = bookRepository.findByTitleIgnoreCase(bookQuantity.book).get(0);
//		books.add(book2);
//	}
//	List<Combination> combinations = combinationRepository.findAll().stream().filter(e -> e.getLibrary().equals(library)).toList();
////	List<SpecialBooksCombination> specialBooksCombinations = specialBooksCombinationRepository.findByLibrary(library);
//	
//	boolean check = true;
//	double discount = -20;
////	for(SpecialBooksCombination sbc : specialBooksCombinations) {
//	//	List<Combination> combinations = sbc.getCombinations();
//	for(Combination combination: combinations) {
//		check=true;
//		for(Book book : combination.getBooks()) {
//			if(!books.contains(book)) {
//				check=false;
//				break;
//			}
//		}
//		if(check==true) {
//			discount=0;
//			discount+=combination.getSpecialDiscount();
//			return discount;
//		}
//	}
//
//	return discount ;
//
//}


@Scheduled(fixedRate = 120000)
public void updateLibrarySales() {
	for(Library library : libraryRepository.findAll()) {
		
		List<LibraryBook> libraryBooks = libraryBookRepository.findByLibrary(library);
		
		List<BookSales> bookSales = new ArrayList<>();
		
		for(LibraryBook lb : libraryBooks) {
			if(lb.getBookInfo().amountSold>0 && lb.getBookInfo().revenue>0) {
			bookSales.add(new BookSales(lb.getBook(),lb.getBookInfo().amountSold,lb.getBookInfo().revenue));
		   lb.getBookInfo().setAmountSold(0);
		   lb.getBookInfo().setRevenue(0);
		   libraryBookRepository.save(lb);
		}
		}
		bookSalesRepository.saveAll(bookSales);
		LibrarySales  librarySale =  new LibrarySales();
		if(bookSales.size()>0) {
		librarySale.setLibrary(library);
		librarySale.setBookSales(bookSales);
		librarySalesRepository.save(librarySale);
	}
	}
}


public List<BookModel> studentRetrieveAllLibraryBooks(String libraryName){
	
	Library library = methodService.getElementFromList(libraryRepository.findByNameIgnoreCase
			(libraryName), HttpStatus.BAD_REQUEST, "No Library exists with this name");
	
	List<LibraryBook> libraryBooks = libraryBookRepository.findByLibrary(library);
	
	return libraryBooks.stream().map(e -> e.getBook().getBookModel()).toList();
}



















}


