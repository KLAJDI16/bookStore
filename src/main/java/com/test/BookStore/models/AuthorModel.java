package com.test.BookStore.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class AuthorModel {
	public int id;
	public String name;
	public String email;
	public AuthorModel(int id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
	}

	
	public String toString() {
		return "Author with id : "+id+" name : "+name+" email : "+email;
	}
	
}
