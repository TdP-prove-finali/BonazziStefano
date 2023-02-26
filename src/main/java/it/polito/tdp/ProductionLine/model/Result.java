package it.polito.tdp.ProductionLine.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Result {
	
	private Press press;
	private List<Order> orders;
	private List<Order> final_orders;
	private long t_used;
	private long t_stop;
	private double setup_time;
	private LocalDateTime finishDate;
	private double usage;
	private int efficiency;
	
	public Result(Press press, List<Order> orders, List<Order> final_orders, long t_used, long t_stop, double setup_time, LocalDateTime finishDate) {
		this.press = press;
		this.orders = new ArrayList<Order>();
		
		if(final_orders != null) 
			this.final_orders = new ArrayList<Order>(final_orders);
		
		this.t_used = t_used;
		this.t_stop = t_stop;
		this.finishDate = finishDate;
		this.efficiency = 0;
		this.setup_time = setup_time;
	}

	public Press getPress() {
		return press;
	}

	public void setPress(Press press) {
		this.press = press;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public long getT_used() {
		return t_used;
	}

	public void setT_used(long t_used) {
		this.t_used = t_used;
	}

	public long getT_stop() {
		return t_stop;
	}

	public void setT_stop(long t_stop) {
		this.t_stop = t_stop;
	}

	public LocalDateTime getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(LocalDateTime finishDate) {
		this.finishDate = finishDate;
	}

	public double getUsage() {
		return usage;
	}

	public void setUsage(long t_stop, long t_used) {
		this.usage = 100*((double)t_used/((double)t_stop+(double)t_used));
	}

	public List<Order> getFinal_orders() {
		return final_orders;
	}

	public void setFinal_orders(List<Order> final_orders) {
		this.final_orders = final_orders;
	}

	public double getSetup_time() {
		double r = this.setup_time/ (double)this.orders.size();
		return r;
	}

	public void setSetup_time(double setup_time) {
		this.setup_time = setup_time;
	}

	public int getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(int efficiency) {
		this.efficiency = efficiency;
	}

	@Override
	public String toString() {
		String s = "";
		
		if(t_stop != 0 || t_used != 0) {
			this.setUsage(t_stop, t_used);
		}
		
		s = s + this.press + "\n\t Finish date = " + this.finishDate + "\n\t Usage = " + 
				Math.round(this.getUsage()*1000.0)/1000.0 + " %\n\t Time used = " + this.t_used +
				" seconds\n\t Time not used = " + this.t_stop + " seconds\n\t Mean setup time = " + 
				this.setup_time + " seconds\n\n";
		
		for (Order o: this.final_orders) {
			s = s + o + "\n";
		}
		
		return s;
	}

	@Override
	public int hashCode() {
		return Objects.hash(final_orders, finishDate, orders, press, setup_time, t_stop, t_used, usage);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Result other = (Result) obj;
		return Objects.equals(final_orders, other.final_orders) && Objects.equals(finishDate, other.finishDate)
				&& Objects.equals(orders, other.orders) && Objects.equals(press, other.press)
				&& Double.doubleToLongBits(setup_time) == Double.doubleToLongBits(other.setup_time)
				&& t_stop == other.t_stop && t_used == other.t_used
				&& Double.doubleToLongBits(usage) == Double.doubleToLongBits(other.usage);
	}
	
}
