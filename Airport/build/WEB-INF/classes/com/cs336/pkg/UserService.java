package com.cs336.pkg;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserService {

	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Icarus";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASSWORD = "root";

	public boolean isUsernameTaken(String userid) {
		boolean val = true;
		try {
			//Load JDBC driver - the interface standardizing the connection procedure. Look at WEB-INF\lib for a mysql connector jar file, otherwise it fails.
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} 

		try { 
			Connection con = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
			Statement st = con.createStatement();
			ResultSet t = st.executeQuery("select username from users where username='" + userid +"'");

			val = t.next();
			t.close();
			st.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return val;
	}

	public void registerUser(String userid, String password, String first, String last) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} 

		try { 
			Connection con = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
			PreparedStatement pst = con.prepareStatement("INSERT INTO Users (Username, Password, Role, Fname, Lname) VALUE (?, ?, ?, ?, ?)");
			pst.setString(1, userid);
			pst.setString(2, password);
			pst.setString(3, "Customer");
			pst.setString(4, first);
			pst.setString(5, last);
			pst.executeUpdate();

			pst.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void registerRep(String userid, String password, String first, String last) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} 

		try { 
			Connection con = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
			PreparedStatement pst = con.prepareStatement("INSERT INTO Users (Username, Password, Role, Fname, Lname) VALUE (?, ?, ?, ?, ?)");
			pst.setString(1, userid);
			pst.setString(2, password);
			pst.setString(3, "Representative");
			pst.setString(4, first);
			pst.setString(5, last);
			pst.executeUpdate();

			pst.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void editUser(String target, String userid, String password, String first, String last) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} 

		StringBuilder query = new StringBuilder("UPDATE USERS SET ");

		// Create a list to store parameters for the prepared statement
		List<Object> parameters = new ArrayList<>();

		// Check which fields have values and add them to the query and parameters list
		if (userid != null && !userid.isEmpty()) {
			query.append("username = ?, ");
			parameters.add(userid);
		}
		if (password != null && !password.isEmpty()) {
			query.append("password = ?, ");
			parameters.add(password);
		}
		if (first != null && !first.isEmpty()) {
			query.append("Fname = ?, ");
			parameters.add(first);
		}
		if (last != null && !last.isEmpty()) {
			query.append("Lname = ?, ");
			parameters.add(last);
		}

		// Remove the trailing comma and space
		query.setLength(query.length() - 2);

		// Add the WHERE clause based on the condition (e.g., updating a specific user)
		query.append(" WHERE username = '"+ target + "'");

		try { 
			Connection con = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
			PreparedStatement pst = con.prepareStatement(query.toString());
			for (int i = 0; i < parameters.size(); i++) {
				pst.setObject(i + 1, parameters.get(i));
			}
			pst.executeUpdate();

			pst.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteUser(String target) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} 

		try { 
			Connection con = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
			PreparedStatement pst = con.prepareStatement("DELETE FROM USERS WHERE username = ?");
			pst.setString(1, target);
			pst.executeUpdate();
			System.out.println("User deleted successfully.");

			pst.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteAirport(String target) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} 

		try { 
			Connection con = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
			PreparedStatement pst = con.prepareStatement("DELETE FROM AIRPORTS WHERE PortID = ?");
			pst.setString(1, target);
			pst.executeUpdate();
			System.out.println("Location deleted successfully.");

			pst.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void editAirport(String target, String id, String full_name) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} 

		StringBuilder query = new StringBuilder("UPDATE AIRPORTS SET ");
		List<Object> parameters = new ArrayList<>();

		if (id != null && !id.isEmpty()) {
			query.append("PortID = ?, ");
			parameters.add(id);
		}
		if (full_name != null && !full_name.isEmpty()) {
			query.append("AirportName = ?, ");
			parameters.add(full_name);
		}

		query.setLength(query.length() - 2);
		query.append(" WHERE PortID = '"+ target + "'");

		try { 
			Connection con = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
			PreparedStatement pst = con.prepareStatement(query.toString());
			for (int i = 0; i < parameters.size(); i++) {
				pst.setObject(i + 1, parameters.get(i));
			}
			pst.executeUpdate();

			pst.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	public void addAirport(String id, String full_name) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} 

		try { 
			Connection con = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
			PreparedStatement pst = con.prepareStatement("INSERT INTO airports (PortID, AirportName) VALUE (?, ?)");
			pst.setString(1, id);
			pst.setString(2, full_name);
			pst.executeUpdate();

			pst.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean isAirport(String id) {
		boolean val = false;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} 

		try { 
			Connection con = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
			Statement st = con.createStatement();
			ResultSet t = st.executeQuery("SELECT PortID FROM Airports WHERE PortID='" + id +"'");

			val = t.next();
			t.close();
			st.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return val;
	}		

	public void deleteAircraft(String target) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} 

		try { 
			Connection con = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
			PreparedStatement pst = con.prepareStatement("DELETE FROM AIRCRAFTS WHERE AircraftID = ?");
			pst.setString(1, target);
			pst.executeUpdate();
			System.out.println("Plane deleted successfully.");

			pst.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addAircraft(String id, String company) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} 

		try { 
			Connection con = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
			PreparedStatement pst = con.prepareStatement("INSERT INTO aircrafts (AircraftID, CompanyID) VALUE (?, ?)");
			pst.setString(1, id);
			pst.setString(2, company);
			pst.executeUpdate();

			pst.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void editAircraft(String target, String id, String company) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} 

		StringBuilder query = new StringBuilder("UPDATE AIRCRAFTS SET ");
		List<Object> parameters = new ArrayList<>();

		if (id != null && !id.isEmpty()) {
			query.append("aircraftID = ?, ");
			parameters.add(id);
		}
		if (company != null && !company.isEmpty()) {
			query.append("companyID = ?, ");
			parameters.add(company);
		}

		query.setLength(query.length() - 2);
		query.append(" WHERE aircraftID = '"+ target + "'");

		try { 
			Connection con = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
			PreparedStatement pst = con.prepareStatement(query.toString());
			for (int i = 0; i < parameters.size(); i++) {
				pst.setObject(i + 1, parameters.get(i));
			}
			pst.executeUpdate();

			pst.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public boolean isAircraft(String id) {
		boolean val = false;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} 

		try { 
			Connection con = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
			Statement st = con.createStatement();
			ResultSet t = st.executeQuery("SELECT AircraftID FROM Aircrafts WHERE AircraftID='" + id +"'");

			val = t.next();
			t.close();
			st.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return val;
	}

	public boolean isCorrectUser(String userid, String password) {
		boolean real = false;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} 

		try { 
			Connection con = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
			Statement st = con.createStatement();
			ResultSet t = st.executeQuery("select * from users where username='" + userid +"' and password='"+ password +"'");

			real = t.next();
			t.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return real;
	}

	public String getUserRole(String user) {
		String position = "NULL";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} 

		try { 
			Connection con = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
			Statement st = con.createStatement();
			ResultSet t = st.executeQuery("select Role from users where username='" + user + "'");

			if(t.next()) position = t.getNString(1);

			t.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return position;
	}

}
