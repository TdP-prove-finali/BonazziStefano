package it.polito.tdp.ProductionLine.model;

import java.time.LocalDateTime;
import java.util.List;

public class Event implements Comparable<Event> {
	
	public enum EventType {
		FREE_PRESS,	
		NEW_PRODUCTION	
	}
	
	private EventType type;
	private Order order;
	private List<Order> next_orders;
	private LocalDateTime time;
	
	public Event(Order order, List<Order> next_orders, EventType type, LocalDateTime time) {
		this.order = order;
		this.next_orders = next_orders;
		this.type = type;
		this.time = time;
	}
	
	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<Order> getNext_orders() {
		return next_orders;
	}

	public void setNext_orders(List<Order> next_orders) {
		this.next_orders = next_orders;
	}
	
	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	
	@Override
	public int compareTo(Event o) {
		if(this.time.isBefore(o.getTime())) 
			return -1;
		else
			return 1;
	}
	
}
