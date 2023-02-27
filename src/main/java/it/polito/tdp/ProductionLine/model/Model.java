package it.polito.tdp.ProductionLine.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.ProductionLine.db.Dao;

public class Model {

	private Dao dao;
	private List<Order> orders;
	private List<Press> presses;
	private List<Production> production;
	private Map<Press, Result> results;
	
	public Model() {
		this.dao = new Dao();
		
		this.orders = this.dao.getOrders();
		this.presses = this.dao.getPresses();
		this.production = this.dao.getProduction();
		this.results = new HashMap<>();
		
		this.findPressesCt(this.presses, this.production);
		this.setTonsRange(this.presses);

	}

	// RICORSIONE e SIMULAZIONE
	
	public String optimize(double err) {
		this.results = new HashMap<>();
		List<Press> presses = new ArrayList<Press>(this.presses);
	    Collections.sort(presses);
	    
		String s = "";
		
		for(Press p: presses) {
			List<Order> allOrders = this.getOrdersInPress(p);
			
			if(!allOrders.isEmpty()) {
				List<Order> toDo = new ArrayList<>(allOrders);
				Collections.sort(allOrders);
			
				List<Order> orders = new ArrayList<>();
			
				this.recursive(orders, toDo, allOrders, p, err);
			}
			
			if(this.results.get(p) == null) 
				s = s + p + "\n\tNESSUN ORDINE DA ELABORARE O PARAMETRI DI CONSEGNA NON RISPETTATI\n";
			else
				s = s + this.results.get(p) + "\n";
		}
		
		return s;
	}
	
	private void recursive(List<Order> orders, List<Order> toDo, List<Order> allOrders, Press p, double err) {
		
		if(orders.size() == allOrders.size()) {	
			Simulator sim = new Simulator();
			
			List<Order> orders2 = new ArrayList<Order>();
			for (Order o : orders) {
				Order order = new Order(o.getOrder_date(), o.getLot_number(), o.getQuantity(), o.getDescription(), o.getTons());
				orders2.add(order);
			}
			
			sim.init(orders2, p, err);
			sim.run();
			
			LocalDateTime finish = sim.getResult().getFinishDate();
			
			if(sim.isValid()) {
				if(!this.results.containsKey(p)) {
					Result result = new Result(p, null, orders2, sim.getResult().getT_used(),
							sim.getResult().getT_stop(), sim.getResult().getSetup_time(), sim.getResult().getFinishDate());
				
					this.results.put(p, result);
				} else {
				
					if(finish.isBefore(this.results.get(p).getFinishDate()) || finish.isEqual(this.results.get(p).getFinishDate())) {
						Result result;
						if(finish.isEqual(this.results.get(p).getFinishDate())
								&& sim.getResult().getEfficiency() <= this.results.get(p).getEfficiency()) {
					
								result = new Result(p, null, orders2, sim.getResult().getT_used(),
									sim.getResult().getT_stop(), sim.getResult().getSetup_time(), sim.getResult().getFinishDate());
								this.results.put(p, result);
						} else {
				
							result = new Result(p, null, orders2, sim.getResult().getT_used(),
								sim.getResult().getT_stop(), sim.getResult().getSetup_time(), sim.getResult().getFinishDate());
							this.results.put(p, result);
						}
					}
				}
			}
		} else {
			for (Order o: toDo) {
				orders.add(o);
				List<Order> deepArray = new ArrayList<>(toDo);
				deepArray.remove(o);
				this.recursive(orders, deepArray, allOrders, p, err);
				orders.remove(o);
			}
		}
	}
	
	public String optimizeForPress(Press p, double err) {
		this.results = new HashMap<>();
		String s = "";
					
		List<Order> allOrders = this.getOrdersInPress(p);
			
		if(!allOrders.isEmpty()) {
			List<Order> toDo = new ArrayList<>(allOrders);
			Collections.sort(allOrders);
		
			List<Order> orders = new ArrayList<>();
			
			this.recursive(orders, toDo, allOrders, p, err);
			
			if(this.results.get(p) == null) 
				s = s + p + "\n\tNESSUN ORDINE DA ELABORARE O PARAMETRI DI CONSEGNA NON RISPETTATI\n";
			else
				s = s + this.results.get(p) + "\n";
		}
		
		return s;
	}

	// OTHERS
	
	public List<Press> getPresses() {
		Collections.sort(this.presses);
		return this.presses;
	}

	public String addOrderOpt(LocalDateTime date, String lot, Integer pieces, String description, Integer tons) {
		Order order = new Order(date, lot, pieces, description, tons);
		this.orders.add(order);
		this.dao.addOrder(order);
		return "Aggiunto ordine:\n" + order.toString();
	}
	
	public String addPressSim(Press press) {
		this.presses.add(press);
		this.dao.addPress(press);
		this.setTonsRange(this.presses);
		return "Correctly added " + press;
	}
	
	public String removeOrder(String lot) {
		int i = -1;
		Order o = null;
		for (Order order : orders) {
			if(order.getLot_number().compareTo(lot) == 0) {
				i = orders.indexOf(order);
			    o = order;
			}
		}
		
		if(i == -1) 
			return "Ordine non presente o numero del lotto inseatto\n";
		
		this.orders.remove(i);
		this.dao.removeOrder(o);
		
		return "Rimosso ordine:\n"+o;
	}
	
	public String removePress(Integer id) {
		int i = -1;
		Press p = null;
		for (Press press : presses) {
			if(press.getId() == id) {
				i = presses.indexOf(press);
			    p = press;
			}
		}
		
		if(i == -1) 
			return "Pressa non presente o ID inseatto\n";
		
		this.presses.remove(i);
		this.dao.removePress(p);
		this.setTonsRange(this.presses);
		
		return "Rimossa press:\n"+p;
	}
	
	private List<Order> getOrdersInPress(Press p) {
		List<Order> result = new ArrayList<Order>();		
		
		for (Order o: this.orders) {
			int oTons = o.getTons();
			int pMinTons = p.getMinTons();
			int pMaxTons = p.getTons();
			
			if(oTons >= pMinTons && oTons <= pMaxTons) {
				Order order = new Order(o.getOrder_date(), o.getLot_number(), o.getQuantity(), o.getDescription(), o.getTons());
				result.add(order);
			}
		}
		
		return result;
	}
	
	private void findPressesCt(List<Press> presses, List<Production> production) {
		for (Press press : presses) {
			double sum = 0;
			int n = 0;
			
			for (Production p : production) {
				int pr = p.getPress();
				double ct = p.getCycle_time();
				
				if(press.getId() == pr) {
					n++;
					sum += ct;
				}
			}
			
			double mean = sum/n;
			
			press.setCycle_time(mean);
		}
	}
	
	private void setTonsRange(List<Press> presses) {
		List<Press> allPresses = new ArrayList<>(presses);
		Collections.sort(allPresses);
		
		for (int i = 0; i<allPresses.size(); i++) {
			Press p1 = allPresses.get(i);
			Press p2;
			
			if(i == 0) 
				p1.setMinTons(0);
			
			if(i-1>=0) {
				p2 = allPresses.get(i-1);
				int minTons = p2.getTons()+1;
				p1.setMinTons(minTons);
			} 
		}
	}

	public List<Order> sizePress(int tons) {
		List<Order> result;		
		Press press = null;
		for (Press p : this.presses) {
			if(tons <= p.getTons() && tons > p.getMinTons()) 
				press = p;
		}
		
		if(press == null) 
			return null;
		
		result = this.getOrdersInPress(press);
		
		return result;
	}
	
	public boolean isPresent(String lot, Integer pressId) {
		if (lot != null) {
			for (Order order : orders) {
				if(order.getLot_number().compareTo(lot) == 0) 
					return true;
			}
		} else if (pressId != null){
			for (Press press : presses) {
				if(press.getId() == pressId) 
					return true;
			}
		}
		
		return false;
	}
	
}
