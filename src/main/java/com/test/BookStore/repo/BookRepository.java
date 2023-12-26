package com.test.BookStore.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.test.BookStore.entities.Author;
import com.test.BookStore.entities.Book;
import com.test.BookStore.entities.Library;


@Repository
public interface BookRepository extends JpaRepository<Book,Integer> , JpaSpecificationExecutor<Book>{
	
	public List<Book> findByTitleIgnoreCase(String title);
	
//	public List<Book> findByPriceLessThanEqual(int price);
	

	
	//findByAgeLessThanEqual
}
