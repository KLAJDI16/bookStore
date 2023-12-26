package com.test.BookStore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.test.BookStore.entities.BookCostTransaction;
import com.test.BookStore.entities.LibraryBook;

public interface BookCostTransactionRepository extends JpaRepository<BookCostTransaction, Integer> , JpaSpecificationExecutor<BookCostTransaction>{

}
