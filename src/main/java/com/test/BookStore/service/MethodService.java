package com.test.BookStore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.test.BookStore.entities.Book;
import com.test.BookStore.entities.LibraryBook;


@Service
public class MethodService {
	@Autowired
	private JavaMailSender javaMailSender;	
	public void throwResponseException(HttpStatus st,String reason) {
    	System.err.println(reason);
    	throw  new ResponseStatusException(HttpStatus.NO_CONTENT,reason);
	}
	
	public List<LibraryBook> filterList(List<LibraryBook> bigList,List<Book> smallList){
		List<LibraryBook> libraryBooks = new ArrayList<>();
		for(int i=0;i<bigList.size();i++) {
			for(int j=0;j<smallList.size();j++) {
				if(bigList.get(i).getBook().equals(smallList.get(j))){
					libraryBooks.add(bigList.get(i));			
					}
		}	
		}
		return bigList.stream().filter(e -> smallList.contains(e.getBook())).toList();
	}
	public <T> void swap(T[] arr,int i,int j){
		T temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	public <T> T getElementFromList(List<T> list,HttpStatus status,String str) {
		if(list == null || list.isEmpty()) {
			 throwResponseException(status, str);
		}
		return list.get(0);
	}
	
	public void sendMail(String toMail,String subject,String text) {
		SimpleMailMessage smp = new SimpleMailMessage();
		smp.setFrom("klajdibarci2@gmail.com");
		smp.setTo(toMail);
		smp.setSubject(subject);
		smp.setText(text);
		javaMailSender.send(smp);
	}

}
