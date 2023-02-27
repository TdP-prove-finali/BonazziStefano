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
				result.add(new Order(LocalDateTime.parse(res.getDate("order_date").toString()+"T"+res.getTime("order_date").toString()), res.getString("lot"),
						res.getInt("pieces"), res.getString("description"), res.getInt("tons")));
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
						res.getDouble("cycle_time"), (long) res.getDouble("setup_time")));
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
	
	public boolean addOrder(Order o) {
		final String sql = "INSERT INTO orders(order_date, lot, pieces, description, tons) " + 
				" values(?, ?, ?, ?, ?)";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, o.getOrder_date().toString());
			st.setString(2, o.getLot_number());
			st.setInt(3, o.getQuantity());
			st.setString(4, o.getDescription());
			st.setInt(5, o.getTons());
			
			st.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean addPress(Press p) {
		final String sql = "INSERT INTO presses(id, tons, cycle_time, setup_time) " + 
				" values(?, ?, ?, ?)";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, p.getId());
			st.setInt(2, p.getTons());
			st.setDouble(3, p.getCycle_time());
			st.setDouble(4, p.getSetup_time());
			
			st.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean removeOrder(Order o) {
		final String sql = "DELETE FROM orders " + 
				"WHERE lot = ?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
		
			st.setString(1, o.getLot_number());
			st.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean removePress(Press p) {
		final String sql = "DELETE FROM presses " + 
				"WHERE id = ?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
		
			st.setInt(1, p.getId());
			st.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
