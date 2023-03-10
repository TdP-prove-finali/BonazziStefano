package it.polito.tdp.ProductionLine.db;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBConnect {

	private static String jdbcURL = "jdbc:mysql://localhost/ProductionOrders";
	private static HikariDataSource ds;

	public static Connection getConnection() {

		if (ds == null) {
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(jdbcURL);
			
			// Inserire username e password per l'accesso al database SQL
			config.setUsername("stefanobonazzi");
			config.setPassword("psw2810");
			
			config.addDataSourceProperty("cachePrepStmts", "true");
			config.addDataSourceProperty("prepStmtCacheSize", "250");
			config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
			
			ds = new HikariDataSource(config);
		}

		try {
			Connection c = ds.getConnection();
			return c;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
