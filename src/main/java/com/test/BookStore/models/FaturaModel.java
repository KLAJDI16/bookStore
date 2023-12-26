package com.test.BookStore.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.test.BookStore.dto.BookQuantity;
import com.test.BookStore.models.BookModel;
import com.test.BookStore.models.BookQuantityFaturaModel;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class FaturaModel {
	
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private LocalDateTime date;
	@ManyToOne
	private LibraryModel libraryModel;
	@ManyToOne
	private StudentModel studentModel;
	@OneToMany
	private List<BookQuantityFaturaModel> bookQuantityFaturaModels;
	private double specialCombinationDiscount;
	private double specialCombinationCost;
	private double specialCostumerDiscount;
	private double specialCostumerCost;
	private double finalCost;
	private int totalQuantity;
	public FaturaModel(int id,LocalDateTime date, LibraryModel library, StudentModel student,
			List<BookQuantityFaturaModel> bookQuantityFaturaModel, double specialCombinationDiscount,
			double specialCombinationCost, double specialCostumerDiscount, double specialCostumerCost, double finalCost,
			int totalQuantity, String stringRepresentation) {
		super();
		this.date = date;
		this.libraryModel = library;
		this.studentModel = student;
		this.bookQuantityFaturaModels = bookQuantityFaturaModel;
		this.specialCombinationDiscount = specialCombinationDiscount;
		this.specialCombinationCost = specialCombinationCost;
		this.specialCostumerDiscount = specialCostumerDiscount;
		this.specialCostumerCost = specialCostumerCost;
		this.finalCost = finalCost;
		this.totalQuantity = totalQuantity;
	}
	public FaturaModel() {
		date=LocalDateTime.now();
		bookQuantityFaturaModels = new ArrayList<BookQuantityFaturaModel>();
	}
	
	public void addBookQuantity(BookQuantityFaturaModel b) {
		bookQuantityFaturaModels.add(b);
	}
	public String toString() {
		String str = "";
		str+="Date -> "+date.toString()+"\n";
		str+="---------------------------------------------------------\n";
		str+="Customer : Id -> "+studentModel.getId()+", Name -> "+studentModel.getName()+"\n";
		str+="---------------------------------------------------------\n";

		str+="Library : Id -> "+libraryModel.id+", Name -> "+libraryModel.name+"\n";
		str+="---------------------------------------------------------\n";
		for(BookQuantityFaturaModel bookQuantityFaturaModel : bookQuantityFaturaModels) {
			str+="Book-Title -> "+bookQuantityFaturaModel.bookModel.title+"\n";
			str+=" Quantity  -> "+bookQuantityFaturaModel.quantity+"\n";
			str+=" Price per Book  -> "+bookQuantityFaturaModel.pricePerBook+"$\n";
			str+=" Total Price  -> "+ bookQuantityFaturaModel.quantity*bookQuantityFaturaModel.pricePerBook+"$ = "+bookQuantityFaturaModel.totalPrice+"$\n";
			str+="Minimum quantity for offer -> "+bookQuantityFaturaModel.offerQuantity+"\n";
			str+="Discount -> "+bookQuantityFaturaModel.discount+"%\n";
			str+="Price after discount -> "+bookQuantityFaturaModel.cost+"$\n";
			str+="---------------------------------------------------------\n";
		//	str+=" Quantity  -> "+bookQuantityFaturaModel.+"\n";
		}
		str+="---------------------------------------------------------\n";
		str+="Discount for special combination  -> "+specialCombinationDiscount+"%\n";
		str+="Price after special combination offer  -> "+specialCombinationCost+"$\n";
		str+="Discount for special costumer  -> "+specialCostumerDiscount+"%\n";
		str+="Price after special costumer  offer -> "+specialCostumerCost+"$\n";
		str+="---------------------------------------------------------\n";
		str+="Total quantity  of books  -> "+totalQuantity+"\n";
		str+="Final   price   of books  -> "+finalCost+"\n";
		str+="---------------------------------------------------------\n";
		return str;
	}
	
	
	
}
