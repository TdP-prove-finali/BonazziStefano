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

	// RICORSIONE
	
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
	
	public String addOrderOpt(Order order) {
		this.orders.add(order);
		return "Aggiunto ordine: " + order.toString();
	}
	
	public String optimize() {
		List<Press> presses = new ArrayList<Press>(this.presses);
	    Collections.sort(presses);
	    
		String s = "";
		
		for(Press p: presses) {
			
			List<Order> allOrders = this.getOrdersInPress(p);
			
			if(!allOrders.isEmpty()) {
			
				List<Order> toDo = new ArrayList<>(allOrders);
				Collections.sort(allOrders);
			
				List<Order> orders = new ArrayList<>();
			
				this.recursive(orders, toDo, allOrders, p);
				
			}
			
			s = s + this.results.get(p) + "\n";
		}
		
		return s;
	}
	
	private void recursive(List<Order> orders, List<Order> toDo, List<Order> allOrders, Press p) {
		if(orders.size() == allOrders.size()) {	
			//Simulo alla fine
			Simulator sim = new Simulator();
			
			List<Order> orders2 = new ArrayList<Order>();
			for (Order o : orders) {
				Order order = new Order(o.getOrder_date(), o.getLot_number(), o.getQuantity(), o.getDescription(), o.getTons());
				orders2.add(order);
			}
			
			sim.init(orders2, p);
			sim.run();
			
			//MODIFICA L?ORDER DATE PERCHé?? NON VA A PRENDERE I RIFERIMENTI GIUSTI DEGLI ORDERS
			
			LocalDateTime finish = sim.getResult().getFinishDate();
			
			if(this.results.get(p) == null) {
				Result result = new Result(p, null, orders2, sim.getResult().getT_used(),
						sim.getResult().getT_stop(), sim.getResult().getFinishDate());
				
				this.results.put(p, result);
			}
			
			if(finish.isBefore(this.results.get(p).getFinishDate())) {
				Result result = new Result(p, null, orders2, sim.getResult().getT_used(),
						sim.getResult().getT_stop(), sim.getResult().getFinishDate());
				
				this.results.put(p, result);
			}
			
		} else {
			
			for (Order o: toDo) {
				// vado in profondità
				orders.add(o);
				List<Order> deepArray = new ArrayList<>(toDo);
				deepArray.remove(o);
				this.recursive(orders, deepArray, allOrders, p);
				orders.remove(o);
			}
		}
	}
	
	private List<Order> getOrdersInPress(Press p) {
		List<Order> result = new ArrayList<Order>();
		List<Order> orders2 = new ArrayList<Order>();
		
		for (Order o : this.orders) {
			Order order = new Order(o.getOrder_date(), o.getLot_number(), o.getQuantity(), o.getDescription(), o.getTons());
			orders2.add(order);
		}
		
		for (Order o: orders2) {
			int oTons = o.getTons();
			int pMinTons = p.getMinTons();
			int pMaxTons = p.getTons();
			
			if(oTons >= pMinTons && oTons <= pMaxTons) {
				result.add(o);
			}
		}
		
		return result;
	}
	
	private void setTonsRange(List<Press> presses) {
		List<Press> allPresses = new ArrayList<>(presses);
		Collections.sort(allPresses);
		
		for (int i = 0; i<allPresses.size(); i++) {
			Press p1 = allPresses.get(i);
			Press p2;
			
			if(i == 0) {
				p1.setMinTons(0);
			}
			
			if(i-1>=0) {
				p2 = allPresses.get(i-1);
				int minTons = p2.getTons()+1;
				p1.setMinTons(minTons);
			} 
		}
	}
	
	// SIMULAZIONE 
	
	// DEVO SALVARMI I RISULTATI DELLA RICORSIONE
	
	public String addOrderSim(Order order) {
		// TODO Auto-generated method stub
		return null;
	}


	public String addPressSim(Press press) {
		// TODO Auto-generated method stub
		return null;
	}


	public String addErrorSim(Double error_probability) {
		// TODO Auto-generated method stub
		return null;
	}

	public String simulate() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Press> getPresses() {
		return this.presses;
	}

	public String optimizeForPress(Press p) {
		String s = "";
					
		List<Order> allOrders = this.getOrdersInPress(p);
			
		if(!allOrders.isEmpty()) {
			
			List<Order> toDo = new ArrayList<>(allOrders);
			Collections.sort(allOrders);
		
			List<Order> orders = new ArrayList<>();
			
			this.recursive(orders, toDo, allOrders, p);
			
			s = s + this.results.get(p) + "\n";
		}
		
		return s;
	}

}
