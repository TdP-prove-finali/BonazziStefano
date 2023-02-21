package it.polito.tdp.ProductionLine.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Production {

	private int productionId;
	private Date start_date;
	private Date end_date;
	private String CODINTPRO;
	private int press;
	private int producted_items;
	private int opened_cavities;
	private int cavities;
	private Double cycle_time;
	private Timestamp start_hour;
	private Timestamp end_hour;
	private String production_lot;
	
	public Production(int productionId, Date start_date, Date end_date, String CODINTPRO, int press,
			int producted_items, int opened_cavities, int cavities, Double cycle_time, Timestamp start_hour,
			Timestamp end_hour, String production_lot) {

		this.productionId = productionId;
		this.start_date = start_date;
		this.end_date = end_date;
		this.CODINTPRO = CODINTPRO;
		this.press = press;
		this.producted_items = producted_items;
		this.opened_cavities = opened_cavities;
		this.cavities = cavities;
		this.cycle_time = cycle_time;
		this.start_hour = start_hour;
		this.end_hour = end_hour;
		this.production_lot = production_lot;
	}

	public int getProductionId() {
		return productionId;
	}

	public void setProductionId(int productionId) {
		this.productionId = productionId;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public String getCODINTPRO() {
		return CODINTPRO;
	}

	public void setCODINTPRO(String cODINTPRO) {
		CODINTPRO = cODINTPRO;
	}

	public int getPress() {
		return press;
	}

	public void setPress(int press) {
		this.press = press;
	}

	public int getProducted_items() {
		return producted_items;
	}

	public void setProducted_items(int producted_items) {
		this.producted_items = producted_items;
	}

	public int getOpened_cavities() {
		return opened_cavities;
	}

	public void setOpened_cavities(int opened_cavities) {
		this.opened_cavities = opened_cavities;
	}

	public int getCavities() {
		return cavities;
	}

	public void setCavities(int cavities) {
		this.cavities = cavities;
	}

	public Double getCycle_time() {
		return cycle_time;
	}

	public void setCycle_time(Double cycle_time) {
		this.cycle_time = cycle_time;
	}

	public Timestamp getStart_hour() {
		return start_hour;
	}

	public void setStart_hour(Timestamp start_hour) {
		this.start_hour = start_hour;
	}

	public Timestamp getEnd_hour() {
		return end_hour;
	}

	public void setEnd_hour(Timestamp end_hour) {
		this.end_hour = end_hour;
	}

	public String getProduction_lot() {
		return production_lot;
	}

	public void setProduction_lot(String production_lot) {
		this.production_lot = production_lot;
	}
	
}
