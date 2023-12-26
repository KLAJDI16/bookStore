package com.test.BookStore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequestDTO {
	 String key;
	 String value;
	 public SearchOperation searchOperation;
	 
	 public enum SearchOperation{
		 EQUAL,
		 IN,
		 LIKE,
		 GREATER_THAN,
		 LESS_THAN,
		 BETWEEN,
	 }
}
