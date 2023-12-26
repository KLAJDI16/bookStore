package com.test.BookStore.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.test.BookStore.entities.Transaction;
import com.test.BookStore.entities.Library;
import com.test.BookStore.entities.Student;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> , JpaSpecificationExecutor<Transaction> {

	public	List<Transaction> findByLibrary(Library library);
	public	List<Transaction> findByStudent(Student student);
	public	List<Transaction> findByLibraryAndStudent(Library library,Student student);

}
