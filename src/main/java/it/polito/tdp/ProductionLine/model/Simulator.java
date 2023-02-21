package it.polito.tdp.ProductionLine.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.ProductionLine.model.Event.EventType;

public class Simulator {
	
	// coda degli eventi
	private PriorityQueue<Event> queue;
	
	//parametri
	private long time_used;
	private long time_stopped;
	private LocalDateTime date_finish;
	
	//modello del mondo
	//private List<Order> orders;
	private Press press;
		
	public Simulator() {
		this.queue = new PriorityQueue<Event>();
	}

	public void init(List<Order> orders, Press press) {
		this.time_used = 0;
		this.time_stopped = 0;
		//this.orders = new ArrayList<>(orders);
		this.press = press;

		List<Order> next_orders = new ArrayList<Order>(orders);
		Order o = orders.get(0);
		next_orders.remove(o);
		
		Event e = new Event(o, next_orders, EventType.NEW_PRODUCTION, o.getOrder_date());
		this.queue.add(e);
	
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			this.processEvent(e);
		}
	}

	private void processEvent(Event e) {
		
		switch (e.getType()) {
		case NEW_PRODUCTION:
			
			int pieces = e.getOrder().getQuantity();
			double ct = this.press.getCycle_time();  //Posso anche metterlo su
			long processTime = (long) (pieces*ct);
			
			LocalDateTime releaseTime = e.getTime().plus(Duration.ofSeconds(processTime));
			
			// CONTROLLARE SE RISPETTA LA DATA D'EVASIONE E AGGIUNGERE IL TEMPO DI SETUP
			
			if(e.getNext_orders().isEmpty()) {
				
				Event new_e = new Event(null, null, EventType.FREE_PRESS, releaseTime);
				this.queue.add(new_e);
				
			} else {
				Event new_e;
				Order next = e.getNext_orders().get(0);
				LocalDateTime nextOrder_start = next.getOrder_date();
				List<Order> next_orders = new ArrayList<Order>(e.getNext_orders());
				next_orders.remove(0);
				
				if(nextOrder_start.isAfter(releaseTime)) {
					Period p = Period.between(nextOrder_start.toLocalDate(), releaseTime.toLocalDate());
					int days = p.getDays();
				
					Duration d = Duration.between(nextOrder_start.toLocalTime(), releaseTime.toLocalTime());
					long seconds = (int) d.getSeconds();
					//Viene negativo il valore?
					
					long unused = seconds + (days*24*60*60);
				
					this.time_stopped = this.time_stopped + unused;
					new_e = new Event(next, next_orders, EventType.NEW_PRODUCTION, nextOrder_start);
				} else {
					//Gli cambio la data d'inizio
					//MAGARI LA SALVO
					next.setOrder_date(releaseTime);
					new_e = new Event(next, next_orders, EventType.NEW_PRODUCTION, releaseTime);
				}
				
				
				this.queue.add(new_e);
				
				this.time_used = this.time_used + processTime;
			}
		
			break;

		case FREE_PRESS:
			this.date_finish = e.getTime();
			
			break;
		}
		
	}

	public long getTime_used() {
		return time_used;
	}

	public void setTime_used(long time_used) {
		this.time_used = time_used;
	}

	public long getTime_stopped() {
		return time_stopped;
	}

	public void setTime_stopped(long time_stopped) {
		this.time_stopped = time_stopped;
	}

	public LocalDateTime getDate_finish() {
		return date_finish;
	}

	public void setDate_finish(LocalDateTime date_finish) {
		this.date_finish = date_finish;
	}

}
