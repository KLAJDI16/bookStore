package com.test.BookStore.models;

import java.util.List;

import com.test.BookStore.entities.Book;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CombinationModel<T> {
public String libraryName;
public List<T> bookList;
public int specialDiscount;
}
