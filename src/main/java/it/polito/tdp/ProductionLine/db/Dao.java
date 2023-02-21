package it.polito.tdp.ProductionLine.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.ProductionLine.model.Order;
import it.polito.tdp.ProductionLine.model.Press;
import it.polito.tdp.ProductionLine.model.Production;

public class Dao {

	public List<Order> getOrders() {
		String sql = "SELECT * "
				   + "FROM `orders`";
		List<Order> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Order(LocalDateTime.parse(res.getDate("Data Inizio").toString()+"T"+res.getTime("Data Inizio").toString()), res.getString("Lotto prod."),
						res.getInt("Quantità utilizzo"), res.getString("Descrizione articolo"), res.getInt("tons")));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<Press> getPresses() {
		String sql = "SELECT * "
				   + "FROM `presses`";
		List<Press> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Press(res.getInt("id"), res.getInt("tons"),
						res.getDouble("cycle_time"), res.getDouble("setup_time")));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<Production> getProduction() {
		String sql = "SELECT * "
				   + "FROM `production`";
		List<Production> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Production(res.getInt("production_ID"), res.getDate("start_date"), res.getDate("end_date"), 
						res.getString("CODINTPRO"), res.getInt("press"), res.getInt("producted_items"), res.getInt("opened_cavities"),
						res.getInt("cavities"), res.getDouble("cycle_time"), res.getTimestamp("start_hour"), res.getTimestamp("end_hour"),
						res.getString("production_lot")));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public String addOrderToDatabase(Order order) {
		String sql = "";
		
		boolean fail = false;
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			fail = true;
			throw new RuntimeException("SQL Error");
		}

		if(fail)
			return "Failed to add order to dataset.";
		else
			return "Succed to add order to dataset.";
	}
	
	public String addPressToDatabase(Press press) {
		String sql = "";
		
		boolean fail = false;
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			fail = true;
			throw new RuntimeException("SQL Error");
		}

		if(fail)
			return "Failed to add press to dataset.";
		else
			return "Succed to add press to dataset.";
	}
	
}

