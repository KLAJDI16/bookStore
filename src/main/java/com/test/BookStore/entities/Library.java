package com.test.BookStore.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.test.BookStore.models.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Library {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String adress;

	
//	@OneToMany(mappedBy = "library")
//	public List<LibraryBook> library_book;
	

	
	
	public Library(String name,String adress) {
		this.name=name;
		this.adress=adress;
		//books=new ArrayList<>();
	}
	
	public LibraryModel getLibraryModel() {
		return new LibraryModel(id,name,adress);
	}
	
	
}
