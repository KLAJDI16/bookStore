package com.test.BookStore.filters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.test.BookStore.dto.RequestDTO;
import com.test.BookStore.dto.RequestDTO.GlobalOperator;
import com.test.BookStore.dto.SearchRequestDTO;
import com.test.BookStore.dto.SearchRequestDTO.SearchOperation;
import com.test.BookStore.entities.Book;
import com.test.BookStore.entities.LibraryBook;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Component
public  class SearchFilters<T> {
	
	public Specification<Book> bookSpecification(RequestDTO requestDTO){
		
		RequestDTO requestDTO2 = seperateFilters(requestDTO).get(0);
		return  (Specification<Book>) getSpecification(requestDTO2);
	}
	public Specification<LibraryBook> libraryBookSpecification(RequestDTO requestDTO){
		RequestDTO requestDTO2 = seperateFilters(requestDTO).get(1);
		return  (Specification<LibraryBook>) getSpecification(requestDTO2);
	}

 	public  Specification<T> getSpecification(RequestDTO requestDTO){
		
		GlobalOperator globalOperator = requestDTO.getGlobalOperator();
		
		List<SearchRequestDTO> searchResponseDTOs = requestDTO.getSearchResponse();
		
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				List<Predicate> predicates = new ArrayList<>();
				for(SearchRequestDTO searchResponseDTO : searchResponseDTOs) {
					String key = searchResponseDTO.getKey();
					if(searchResponseDTO.getSearchOperation() == SearchOperation.EQUAL) {	
predicates.add(criteriaBuilder.equal((Path<Integer>)innerObjectRoot(key,root),searchResponseDTO.getValue()));				
					}
					else if(searchResponseDTO.getSearchOperation() == SearchOperation.IN) {							
								String[] strings = searchResponseDTO.getValue().toString().split(",");
predicates.add(root.get(key).in(strings));
									}
					else if(searchResponseDTO.getSearchOperation() == SearchOperation.BETWEEN) {			
						String[] strings = searchResponseDTO.getValue().toString().split(",");
predicates.add(criteriaBuilder.between((Path<Integer>)innerObjectRoot(key,root), Integer.parseInt(strings[0]), Integer.parseInt(strings[1])));
									}
				else if(searchResponseDTO.getSearchOperation() == SearchOperation.GREATER_THAN) {			
predicates.add(criteriaBuilder.greaterThan((Path<Integer>)innerObjectRoot(key,root),Integer.parseInt(searchResponseDTO.getValue())));
								}
				else if(searchResponseDTO.getSearchOperation() == SearchOperation.LESS_THAN) {			
predicates.add(criteriaBuilder.lessThan((Path<Integer>)innerObjectRoot(key,root),Integer.parseInt(searchResponseDTO.getValue())));
					}	
				else if(searchResponseDTO.getSearchOperation() == SearchOperation.LIKE) {			
predicates.add(criteriaBuilder.like((Path<String>)innerObjectRoot(key,root),"%"+searchResponseDTO.getValue()+"%"));
					}
//				else if(searchResponseDTO.getSearchOperation() == SearchOperation.INNER_OB) {			
//predicates.add(criteriaBuilder.like(root.get(key),"%"+searchResponseDTO.getValue()+"%"));
//					}
				}
				if(globalOperator == GlobalOperator.OR) {
					return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
				}
				else {
					return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
				}
			}
		};

}
	public List<RequestDTO> seperateFilters(RequestDTO requestDTO){
		List<SearchRequestDTO> searchResponse = requestDTO.getSearchResponse();
		List<RequestDTO> requestDTOs = new ArrayList<>();
		List<SearchRequestDTO> list1 = new ArrayList<>();
		List<SearchRequestDTO> list2 = new ArrayList<>();

		String[] bookAttributes={"id","publicationYear","pages","genre","title"};
		String[] libraryBookAtributes= {"libraryName","bookTitle",  "quantity","offerQuantity",  "discountPercent",
				"amountSold",  "highRequestAmount",  "price","revenue"};

		for(SearchRequestDTO searchRequestDTO : searchResponse) {
			String str = searchRequestDTO.getKey();
			if(checkString(str,bookAttributes)) {
				list1.add(searchRequestDTO);
			}
			else if(checkString(str,libraryBookAtributes)) {
				list2.add(searchRequestDTO);
			}
			else {
				String reason = "No keys exist with this name";
				System.err.println(reason);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,reason);
			}
		}
		RequestDTO requestDTO1 = new RequestDTO();
		requestDTO1.globalOperator=requestDTO.getGlobalOperator();
		requestDTO1.searchResponse=list1;
		
		RequestDTO requestDTO2 = new RequestDTO();
		requestDTO2.globalOperator=requestDTO.getGlobalOperator();
		requestDTO2.searchResponse=list2;
		
		requestDTOs.add(requestDTO1);
		requestDTOs.add(requestDTO2);
        if(requestDTO1==null && requestDTO2==null) {
        	String reason = "Both requestDTOs are empty";
			System.err.println(reason);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,reason);
        }
		return requestDTOs;
	}
	
	public <T> Path<T> innerObjectRoot(String key,Root<T> root) {
		String[] bookInfoAtributes= {"libraryName","bookTitle","offerQuantity",  "discountPercent",
				"amountSold",  "highRequestAmount",  "price","revenue"};
        String[] parts = key.split("\\.");
		if(checkString(key,bookInfoAtributes)) {
			return  root.get("bookInfo").get(key);
		}
		else if(parts.length == 1) {
			return root.get(key);
		}
		else {
			Path<T> path = root; 
			 for (String part : parts) {
			        path = path.get(part);
			    }
			 return path;
		}
	}
	/*
	 private <T> Path<T> getPath(Root<T> root, String attributeName) {
    Path<T> path = root;
    for (String part : attributeName.split("\\.")) {
        path = path.get(part);
    }
    return path;
}
	 */
	public String joinString(String[] arr,int index) {
		String str = "";
		if(arr.length == 1 )return arr[0];
		for(int i = index;i<arr.length;i++) {
			str+=arr[i];
			str+=".";
		}
		return str;
	}
	public boolean checkString(String str,String[] arr) {
		for(int i=0;i<arr.length;i++) {
			if(str.equals(arr[i])) {
				return true;
			}
		}
		return false;
	}

}
