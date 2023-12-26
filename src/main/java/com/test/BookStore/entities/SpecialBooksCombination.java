//package com.test.BookStore.entities;
//
//import java.util.List;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.Table;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
//@Entity
//@Table(name = "special_comb_table")
//public class SpecialBooksCombination {
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	int id;
//	@ManyToOne
//	private Library library;
//	@OneToMany
//	@JoinColumn(name="special_combination_id")
//	private List<Combination> combinations;
//	public SpecialBooksCombination(Library library, List<Combination> combinations) {
//		super();
//		this.library = library;
//		this.combinations = combinations;
//	}
//	
//	public void addCombination(Combination combination) {
//		combinations.add(combination);
//	}
//	public void removeCombination(Combination combination) {
//		combinations.remove(combination);
//	}
//
//	public SpecialBooksCombination(Library library) {
//		super();
//		this.library = library;
//	}
//	
//	
//}
