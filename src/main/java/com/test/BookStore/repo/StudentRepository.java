package com.test.BookStore.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.test.BookStore.entities.Library;
import com.test.BookStore.entities.Role;
import com.test.BookStore.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> , JpaSpecificationExecutor<Student>{
public List<Student> findByEmailAndPassword(String email,String password);
public Optional<Student> findByEmail(String email);
public Optional<Student> findByUsername(String email);
public List<Student> findByRole(Role role);
public List<Student> findByRoleName(String name);

}
