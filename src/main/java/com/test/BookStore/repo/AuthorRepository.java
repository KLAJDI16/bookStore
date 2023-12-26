package com.test.BookStore.repo;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.test.BookStore.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> , JpaSpecificationExecutor<Author>{

	public Author findByName(String name);

	public List<Author> findAll(Specification<Author> specification2);
	

}
