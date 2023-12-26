package com.test.BookStore.dto;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class RequestDTO {

	public List<SearchRequestDTO> searchResponse;
	
	public GlobalOperator globalOperator;
	
	public enum GlobalOperator{
		AND,OR
	}
}

