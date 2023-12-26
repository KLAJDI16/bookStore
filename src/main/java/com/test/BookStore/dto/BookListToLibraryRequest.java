package com.test.BookStore.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookListToLibraryRequest {
	public String fromLibrary;
	public String toLibrary;
	public List<BookQuantity<String>> bookQuantity;
}
