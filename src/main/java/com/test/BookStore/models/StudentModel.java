package com.test.BookStore.models;

import com.test.BookStore.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudentModel {

	public int id;
	public String name;
	public String email;
	public String password;
	public String role;

	public StudentModel( String name,String email,String password,String role) {
		super();
		this.name = name;
		this.email = email;
		this.password=password;
		this.role=role;
	}
	public StudentModel( int id,String name,String email,String password,String role) {
		super();
		this.id=id;
		this.name = name;
		this.email = email;
		this.password=password;
		this.role=role;
	}

	
	
	
}
