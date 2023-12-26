package com.test.BookStore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.test.BookStore.entities.Combination;
@Repository
public interface CombinationRepository extends JpaRepository<Combination, Integer> , JpaSpecificationExecutor<Combination>{

}
