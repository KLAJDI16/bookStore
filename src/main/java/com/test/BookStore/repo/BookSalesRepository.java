package com.test.BookStore.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.test.BookStore.entities.Book;
import com.test.BookStore.entities.BookSales;
import com.test.BookStore.entities.Library;
import com.test.BookStore.entities.LibrarySales;

@Repository
public interface BookSalesRepository extends JpaRepository<BookSales,Integer> , JpaSpecificationExecutor<BookSales>{
	public List<BookSales> findByBook(Book book);
}
