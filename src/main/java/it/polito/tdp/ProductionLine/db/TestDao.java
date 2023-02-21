package it.polito.tdp.ProductionLine.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestDao {

	public static void main(String[] args) {
			
		String s = "";
		String sql = "SELECT *"
				+ "FROM orders";
			
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
				
			while (res.next()) {
				s = s+res.getDate("Data Inizio")+" "+res.getString("Lotto prod.")+" "+
						res.getInt("Quantità utilizzo")+" "+res.getString("Descrizione articolo")+" "+
							res.getInt("tons")+"\n";
				}
				
			conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("SQL Error");
			}
			
		System.out.print(s);
	}
	
}
