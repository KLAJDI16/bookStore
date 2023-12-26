package com.test.BookStore.models;

public class LibraryModel {

	public int id;
	public String name;
	public String address;

	
	public LibraryModel(int id, String name,String address) {
		super();
		this.id = id;
		this.name = name;
		this.address=address;
	}
	@Override
	public String toString() {
		return " Library With id : " + id + ", name  : " + name +" ,address : "+address;
	}
}
