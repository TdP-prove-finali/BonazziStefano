package it.polito.tdp.ProductionLine.model;

import java.util.Objects;

public class Press implements Comparable<Press> {

	private Integer id;
	private Integer tons;
	private Double cycle_time;
	private Long setup_time;
	private Integer minTons;
	
	public Press(Integer id, Integer tons, Double cycle_time, Long setup_time) {
		this.id = id;
		this.tons = tons;
		this.cycle_time = cycle_time;
		this.setup_time = setup_time;
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

	public Long getSetup_time() {
		return setup_time;
	}

	public void setSetup_time(Long setup_time) {
		this.setup_time = setup_time;
	}
	
	public Integer getMinTons() {
		return minTons;
	}

	public void setMinTons(Integer minTons) {
		this.minTons = minTons;
	}
	
	@Override
	public String toString() {
		return "PRESS n°" + this.id + " (tons from " + this.minTons
				+ " to " + this.tons + ")";
	}

	@Override
	public int compareTo(Press o) {
		return this.tons-o.getTons();
	}

	@Override
	public int hashCode() {
		return Objects.hash(cycle_time, id, minTons, setup_time, tons);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Press other = (Press) obj;
		return Objects.equals(cycle_time, other.cycle_time) && Objects.equals(id, other.id)
				&& Objects.equals(minTons, other.minTons) && Objects.equals(setup_time, other.setup_time)
				&& Objects.equals(tons, other.tons);
	}
	
}
