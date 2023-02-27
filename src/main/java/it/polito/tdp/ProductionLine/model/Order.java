package it.polito.tdp.ProductionLine.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order implements Comparable<Order> {

	private LocalDateTime order_date;
	private String lot_number;
	private Integer quantity;
	private String description; 
	private Integer tons;
	private LocalDateTime finish;
	private LocalDateTime start;
	
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

	public LocalDateTime getFinish() {
		return finish;
	}

	public void setFinish(LocalDateTime finish) {
		this.finish = finish;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}
	
	@Override
	public int compareTo(Order o) {
		return this.order_date.compareTo(o.getOrder_date());
	}
	
	@Override
	public String toString() {
		return "\tOrder lot number = " + lot_number + "\n\t\t order date = " + order_date + "\n\t\t quantity = " + quantity
				+ "\n\t\t description = " + description + "\n\t\t tons = " + tons + "\n\t\t Start production = \t\t" + start
				+ "\n\t\t Finish production =\t" + finish + "\n";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(description, finish, lot_number, order_date, quantity, start, tons);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(description, other.description) && Objects.equals(finish, other.finish)
				&& Objects.equals(lot_number, other.lot_number) && Objects.equals(order_date, other.order_date)
				&& Objects.equals(quantity, other.quantity) && Objects.equals(start, other.start)
				&& Objects.equals(tons, other.tons);
	}
	
}
