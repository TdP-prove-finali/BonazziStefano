package it.polito.tdp.ProductionLine.model;

import java.time.LocalDateTime;

public class Order implements Comparable<Order>{

	private LocalDateTime order_date;
	private String lot_number;
	private Integer quantity;
	private String description; 
	private Integer tons;
	
	public Order(LocalDateTime date, String lot_number, Integer quantity, String description, Integer tons) {
		this.order_date = date;
		this.lot_number = lot_number;
		this.quantity = quantity;
		this.description = description;
		this.tons = tons;
	}

	public LocalDateTime getOrder_date() {
		return order_date;
	}

	public void setOrder_date(LocalDateTime order_date) {
		this.order_date = order_date;
	}

	public String getLot_number() {
		return lot_number;
	}

	public void setLot_number(String lot_number) {
		this.lot_number = lot_number;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTons() {
		return tons;
	}

	public void setTons(int tons) {
		this.tons = tons;
	}

	@Override
	public int compareTo(Order o) {
		return this.getOrder_date().compareTo(o.getOrder_date());
	}

	@Override
	public String toString() {
		return "Order: lot number = " + lot_number + "\n\t order date = "+order_date + "\n\t quantity = " + quantity
				+ "\n\t description = " + description + "\n\t tons = " + tons + "\n";
	}
	
}
