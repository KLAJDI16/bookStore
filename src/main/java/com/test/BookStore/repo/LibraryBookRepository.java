package com.test.BookStore.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.test.BookStore.entities.Book;
import com.test.BookStore.entities.Library;
import com.test.BookStore.entities.LibraryBook;
import com.test.BookStore.entities.Student;

public interface LibraryBookRepository extends JpaRepository<LibraryBook, Integer> , JpaSpecificationExecutor<LibraryBook>{

	public List<LibraryBook> findByBook(Book book);
	public List<LibraryBook> findByLibrary(Library library);
	
	public List<LibraryBook> findByLibraryAndBook(Library library,Book book);

	
}
