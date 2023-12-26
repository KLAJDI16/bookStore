package com.test.BookStore.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.test.BookStore.entities.BookSales;
import com.test.BookStore.entities.Library;
import com.test.BookStore.entities.LibrarySales;

@Repository
public interface LibrarySalesRepository extends JpaRepository<LibrarySales,Integer> , JpaSpecificationExecutor<LibrarySales> {

	public List<LibrarySales> findByLibrary(Library library);
}
