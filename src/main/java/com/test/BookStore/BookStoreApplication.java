package com.test.BookStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.test.BookStore.entities.Author;
import com.test.BookStore.entities.Book;
import com.test.BookStore.entities.BookInfo;
import com.test.BookStore.entities.Library;
import com.test.BookStore.entities.LibraryBook;
import com.test.BookStore.entities.Student;
import com.test.BookStore.entities.Role;
import com.test.BookStore.repo.AuthorRepository;
import com.test.BookStore.repo.BookRepository;
import com.test.BookStore.repo.LibraryBookRepository;
import com.test.BookStore.repo.LibraryRepository;
import com.test.BookStore.repo.StudentRepository;

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
public class BookStoreApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(BookStoreApplication.class, args);
	}
	@Autowired
	LibraryRepository libraryRepository;
	@Autowired
	BookRepository bookRepository;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	AuthorRepository authorRepository;
	@Autowired
	LibraryBookRepository libraryBookRepository;

	
	@Override
	public void run(String... args) throws Exception {
//		libraryBookRepository.deleteAll();
//		libraryRepository.deleteAll();
//		// TODO Auto-generated method stub
		List<Library> libraries = new ArrayList<>();	
		libraries.add(new Library("LibraryBranch1","Rruga e Lirise 17 "));
		libraries.add(new Library("LibraryBranch2","Bulevardi Shqipëria e Re 5 "));
		libraries.add(new Library("LibraryBranch3","Lagjja e Arte 3 "));
		if(libraryRepository.count()==0) {
		libraryRepository.saveAll(libraries);
		}
		List<Author> authors = new ArrayList<>();	
		if(authorRepository.count()==0) {
			authors.add(new Author("Ina","Ina1@gmail.com"));
			authors.add(new Author("Klajdi","Klajdi2@gmail.com"));
			authors.add(new Author("Beni","Beni3@gmail.com"));
			authors.add(new Author("Arnold","Arnold4@gmail.com"));
			authorRepository.saveAll(authors);
		}
		List<Student> students =  new ArrayList<>();
		if(studentRepository.count()==0) {
			students.add(new Student("Klajdi Barci", "klajdibarci2@gmail.com", "password77","ADMIN"));
			students.add(new Student("Jasir Asani", "jasirasani@gmail.com", "password1", "ADMIN"));
			students.add(new Student("Etrit Berisha", "etritberisha@gmail.com", "password2", "ADMIN"));
			students.add(new Student("Sokol Cikalleshi", "sokolcikalleshi@gmail.com", "password77777"));
			students.add(new Student("Myrto Uzuni", "myrtouzuni@gmail.com", "password77777" ));
			students.add(new Student("Marjo Mitaj", "marjomitaj@gmail.com", "password77777"));
			students.add(new Student("Kristjan Asllani", "kristjanasllani@gmail.com", "password77777"));
			students.add(new Student("Nedim Bajrami", "nedimbajrami@gmail.com", "password77777"));
			students.add(new Student("Mirlind Daku", "mirlinddaku@gmail.com", "password77777"));
		}
		
		studentRepository.saveAll(students);
		//libraryBookRepository.deleteAll();
//		studentBookLibraryRepository.deleteAll();
//		int[] times = {40,50,60,70};
//		int[] prices = {100,200,300,400};
//		int[] offerQuantitys = {5,6,7,8};
//		int[] highRequestAmounts = {15,18,21,24};
//		double[] discountPercents = {10,20,30};
//		int[] quantitys= {20,30,40,50};
//           Random random = new Random();
//		for(LibraryBook libraryBook : libraryBookRepository.findAll()) {
//			libraryBook.setBookInfo(
//					new BookInfo(prices[random.nextInt(0, 4)],offerQuantitys[random.nextInt(0, 4)],
//					discountPercents[random.nextInt(0, 3)],
//					highRequestAmounts[random.nextInt(0, 4)]));
//			libraryBook.setQuantity(quantitys[random.nextInt(0, 4)]);
//			libraryBookRepository.save(libraryBook);
//		}
	}

}
