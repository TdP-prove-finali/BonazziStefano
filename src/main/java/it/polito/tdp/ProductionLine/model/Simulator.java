package it.polito.tdp.ProductionLine.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import it.polito.tdp.ProductionLine.model.Event.EventType;

public class Simulator {
	
	// Coda degli eventi
	private PriorityQueue<Event> queue;
	
	// Parametri
	private double ct; 
	private long time_used;
	private long time_stopped;
	private long setup_time;
	private LocalDateTime date_finish;
	private Result result;
	private double setup_error = 0;
	private boolean valid;
	private int efficiency;
	
	// Modello del mondo
	private Press press;
	private List<Order> next_orders;
		
	public Simulator() {
		this.queue = new PriorityQueue<Event>();
	}

	public void init(List<Order> orders, Press press, double setup_error) {
		this.time_used = 0;
		this.time_stopped = 0;
		this.setup_time = 0;
		this.press = press;
		this.setup_error = setup_error;
		this.ct = this.press.getCycle_time();
		this.valid = true;
		this.efficiency = 0;

		this.next_orders = new ArrayList<Order>(orders);
		this.result = new Result(press, null, null, 0, 0, 0, null);
		
		Order o = orders.get(0);
		this.next_orders.remove(o);
		
		Event e = new Event(o, this.next_orders, EventType.NEW_PRODUCTION, o.getOrder_date());
		this.queue.add(e);
		
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			this.processEvent(e);
		}
		
		this.result.setT_used(time_used);
		this.result.setT_stop(time_stopped);
		this.result.setFinishDate(date_finish);
		this.result.setSetup_time(setup_time);
	}

	private void processEvent(Event e) {
		
		switch (e.getType()) {
		case NEW_PRODUCTION:
			
			this.result.getOrders().add(e.getOrder());
			int i = this.result.getOrders().indexOf(e.getOrder());
			
			this.result.getOrders().get(i).setStart(e.getTime());
			
			int pieces = e.getOrder().getQuantity(); 
			long processTime = (long) (pieces*this.ct);
			this.time_stopped = this.time_stopped + this.press.getSetup_time();
			
			LocalDateTime time = e.getTime();
			LocalDateTime releaseTime;
			
			if(this.setup_error != 0) {
				LocalDateTime time_with_setup;
			
				if(this.setup_error < Math.random()*100) {
					time_with_setup = time.plus(Duration.ofSeconds(this.press.getSetup_time()));
					this.setup_time = this.setup_time + this.press.getSetup_time();
				} else {
					time_with_setup = time.plus(Duration.ofSeconds(this.press.getSetup_time()*2));
					this.setup_time = this.setup_time + (2*this.press.getSetup_time());
				}
				
				releaseTime = time_with_setup.plus(Duration.ofSeconds(processTime));
			} else {
				this.setup_time = this.setup_time + this.press.getSetup_time();
				releaseTime = time.plus(Duration.ofSeconds(processTime));
			}
			
			this.result.getOrders().get(i).setFinish(releaseTime);
			
			LocalDateTime validity = e.getOrder().getOrder_date();
			validity = validity.plusYears(1);
			
			if(releaseTime.isAfter(validity)) 
				this.valid = false;
			
			
			if(e.getNext_orders().isEmpty()) {

				Event new_e = new Event(null, null, EventType.FREE_PRESS, releaseTime);
				this.queue.add(new_e);
				this.time_used = this.time_used + processTime;
				
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
					
					long unused = seconds + (-days*24*60*60);
					
					this.efficiency = this.efficiency - 1;
					this.time_stopped = this.time_stopped + unused;
					new_e = new Event(next, next_orders, EventType.NEW_PRODUCTION, nextOrder_start);
				} else {
					this.efficiency = this.efficiency + 1;
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

	public boolean isValid() {
		return valid;
	}

	public Result getResult() {
		return result;
	}
	
}
