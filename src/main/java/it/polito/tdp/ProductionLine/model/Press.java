package it.polito.tdp.ProductionLine.model;

import java.time.LocalDate;

public class Press implements Comparable<Press> {

	private Integer id;
	private Integer tons;
	private Double cycle_time;
	private Double setup_time;
	private boolean occupied;
	private LocalDate free;
	private Integer minTons;
	
	public Press(Integer id, Integer tons, Double cycle_time, Double setup_time) {
		this.id = id;
		this.tons = tons;
		this.cycle_time = cycle_time;
		this.setup_time = setup_time;
		this.setOccupied(false);
		this.setFree(null);
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getTons() {
		return tons;
	}

	public void setTons(int tons) {
		this.tons = tons;
	}

	public Double getCycle_time() {
		return cycle_time;
	}

	public void setCycle_time(Double cycle_time) {
		this.cycle_time = cycle_time;
	}

	public Double getSetup_time() {
		return setup_time;
	}

	public void setSetup_time(Double setup_time) {
		this.setup_time = setup_time;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public LocalDate getFree() {
		return free;
	}

	public void setFree(LocalDate localDate) {
		this.free = localDate;
	}

	@Override
	public String toString() {
		return "Press: \n\tID = "+this.id+"\n\tMax tons = "+this.tons+
				"\n\tMin tons = "+this.minTons+"\n";
	}

	@Override
	public int compareTo(Press o) {
		
		return this.tons-o.getTons();
	}

	public Integer getMinTons() {
		return minTons;
	}

	public void setMinTons(Integer minTons) {
		this.minTons = minTons;
	}
	
}
