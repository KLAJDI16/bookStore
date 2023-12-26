package com.test.BookStore.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Embeddable
public class BookInfo {
	public int offerQuantity;
	public double discountPercent;
	public int amountSold;
	public int highRequestAmount;
	public double revenue;
	public int price;
	
	public int addAmountSold(int a) {
		amountSold+=a;
		return amountSold;
	}
	public int removeAmountSold(int a) {
		amountSold-=a;
		return amountSold;
	}
	public int addRevenue(double a) {
		revenue+=a;
		return amountSold;
	}
	public int removeRevenue(double a) {
		revenue-=a;
		return amountSold;
	}
	public BookInfo(int price,int offerQuantity, double discountPercent, int amountSold, double revenue,
			int highRequestAmount) {
		super();
		this.price = price;
		this.offerQuantity = offerQuantity;
		this.discountPercent = discountPercent;
		this.amountSold = amountSold;
		this.revenue = revenue;
		this.highRequestAmount = highRequestAmount;
	}
	public BookInfo(int price,int offerQuantity, double discountPercent,
			int highRequestAmount) {
		super();
		this.price = price;
		this.offerQuantity = offerQuantity;
		this.discountPercent = discountPercent;
		this.highRequestAmount = highRequestAmount;
	}

}
