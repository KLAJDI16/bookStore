package com.test.BookStore.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.test.BookStore.entities.Library;

public interface LibraryRepository extends JpaRepository<Library, Integer> , JpaSpecificationExecutor<Library>{

	public List<Library> findByNameIgnoreCase(String name);
	
}
