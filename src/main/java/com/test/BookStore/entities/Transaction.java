package com.test.BookStore.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.test.BookStore.models.BookQuantityFaturaModel;
import com.test.BookStore.models.FaturaModel;
import com.test.BookStore.models.LibraryModel;
import com.test.BookStore.models.StudentModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "transactions")
@Data
@ToString
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private LocalDateTime date;
	@ManyToOne
	private Library library;
	@ManyToOne
	private Student student;
	@OneToMany
	@JoinColumn(name = "transaction_id")
	private List<BookCostTransaction> bookCostTransactions;
	private double specialCombinationDiscount;
	private double specialCombinationCost;
	private double specialCostumerDiscount;
	private double specialCostumerCost;
	private double finalCost;
	private int totalQuantity;
	public Transaction(LocalDateTime date, Library library, Student student,
			List<BookCostTransaction> bookCostTransactions, double specialCombinationDiscount,
			double specialCombinationCost, double specialCostumerDiscount, double specialCostumerCost, double finalCost,
			int totalQuantity, String stringRepresentation) {
		super();
		this.date = date;
		this.library = library;
		this.student = student;
		this.bookCostTransactions = bookCostTransactions;
		this.specialCombinationDiscount = specialCombinationDiscount;
		this.specialCombinationCost = specialCombinationCost;
		this.specialCostumerDiscount = specialCostumerDiscount;
		this.specialCostumerCost = specialCostumerCost;
		this.finalCost = finalCost;
		this.totalQuantity = totalQuantity;
	}
	public Transaction() {
		this.date = LocalDateTime.now();
		if(bookCostTransactions==null) {
		bookCostTransactions = new ArrayList<BookCostTransaction>();
		}
	}
	public void addBookCost(BookCostTransaction b){
		this.bookCostTransactions.add(b);
	}
	public void removeBookCost(BookCostTransaction b){
		this.bookCostTransactions.remove(b);
	}
	public FaturaModel getFaturaModel() {
		List<BookQuantityFaturaModel> list = bookCostTransactions.stream().map(e -> e.getBookQuantityFaturaModel()).toList();
           
		return new FaturaModel(id, date,  library.getLibraryModel(),  student.getStudentModel(),
				list,  specialCombinationDiscount,
				 specialCombinationCost,  specialCostumerDiscount,  specialCostumerCost,  finalCost,
				 totalQuantity, "");
	}
}
