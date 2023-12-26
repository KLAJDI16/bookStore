package com.test.BookStore.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.BookStore.entities.Library;
import com.test.BookStore.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
public Optional<Role> findByName(String name);
}
