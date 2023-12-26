package com.test.BookStore.dto;

import java.util.List;

public class BookOptionRequest {
	public String libraryName;
	public RequestDTO requestDTO;
	public List<BookQuantity<String>> bookQuantityList;
}
