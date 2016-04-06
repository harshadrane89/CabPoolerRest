package com.cabpooler.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	public Connection connectToDataBase() throws Exception {
		try {
			
			Connection connection = null;
			
			Class.forName("com.mysql.jdbc.GoogleDriver").newInstance();
			
			connection = DriverManager.getConnection("jdbc:google:mysql://api-project-593579034167:cabpooler?user=root");
			
			return connection;
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

}
