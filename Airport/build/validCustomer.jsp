<%@ page contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>

<html>
	<head>
		<link rel="stylesheet" href="./res/css/pages.css">
		<title>Flight Reservations</title>
	</head>
	<body>
		<header class="customer-header">
			<img class="logo" src="./res/images/funLogo.png" alt="Fast Travel">

			<nav>
				<ul>
					<li><a href="validCustomer.jsp">Home</a></li>
					<li><a href="about.jsp">About</a></li>
					<li><a href="profile.jsp"><%=session.getAttribute("user")%></a></li>
				</ul>
			</nav>
		</header>
		<div class="customer-header-shadow"></div>

		<div class="fixed-sidebar">
			<table>
				<tr>
					<form method="post", action="findAirports.jsp">
					<td><input type="submit" value="Search Airports" class="btn"/></td>
					<td><input type="text" placeholder="Airport 1" name="depart-port"></td>
					<td><input type="text" placeholder="Airport 2" name="arrive-port"></td>
					</form>
				</tr>
				<tr>
					<form method="post", action="One_way_Rides.jsp">
					<td><input type="submit" value="One-Way Flights" class="btn"/></td>
					<td><input type="text" placeholder="1337-01-01" name="one-way-date"></td>
					</form>
				</tr>
				<tr>
					<form method="post", action="round_rides.jsp">
					<td><input type="submit" value="Round-Trip Flights" class="btn"/></td>
					<td><input type="text" placeholder="1337-01-01" name="round-trip-date"></td>
					</form>
				</tr>
				<tr>
					<form method="post", action="timely_round_rides.jsp">
					<td><input type="submit" value="Flex Flights" class="btn"/></td>
					<td><input type="text" placeholder="1337-01-01" name="flex-trip-date"></td>
					</form>
				</tr>
				<tr>
					<form method="post", action="custom_sort.jsp">
					<td><input type="submit" value="Sort By: " class="btn"/></td>
					<td><input type="text" placeholder="Price" name="random-sort"></td>
					</form>
				</tr>
			</table>
		</div>
		<div class="content">
			<%
				try {
					Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					e.printStackTrace();
				} 
				
				try { 
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Icarus","root","root");
				Statement st = con.createStatement();
				ResultSet t = st.executeQuery("SELECT * FROM Flights");
			
				out.print("<table>");
					out.print("<tr>");
						out.print("<td>");
							out.print("Flight # ");
						out.print("</td>");
						out.print("<td>");
							out.print("Seats");
						out.print("</td>");
						out.print("<td>");
							out.print("Flight Type");
						out.print("</td>");
						out.print("<td>");
							out.print("Trip Type");
						out.print("</td>");
						out.print("<td>");
							out.print("Departure");
						out.print("</td>");
						out.print("<td>");
							out.print("Time");
						out.print("</td>");
						out.print("<td>");
							out.print("Arrival");
						out.print("</td>");
						out.print("<td>");
							out.print("Time");
						out.print("<td>");
						out.print("<td>");
							out.print("Airline");
						out.print("</td>");
					out.print("</tr>");
				
				while(t.next()) {
					out.print("<tr>");
						out.print("<td>");
							out.print(t.getString("FlightNumber"));
						out.print("</td>");
						out.print("<td>");
							out.print(t.getString("Seats"));
						out.print("</td>");
						out.print("<td>");
							out.print(t.getString("GlobalType"));
						out.print("</td>");
						out.print("<td>");
							out.print(t.getString("TripType"));
						out.print("</td>");
						out.print("<td>");
							out.print(t.getString("DepartLocation"));
						out.print("</td>");
						out.print("<td>");
							out.print(t.getString("DepartTime"));
						out.print("</td>");
						out.print("<td>");
							out.print(t.getString("ArrivalLocation"));
						out.print("</td>");
						out.print("<td>");
							out.print(t.getString("ArrivalTime"));
						out.print("<td>");
						out.print("<td>");
							out.print(t.getString("AirlineCom"));
						out.print("</td>");
					out.print("</tr>");
				}
				out.print("</table>");
			
				t.close();
				st.close();
				con.close();
				
				} catch (SQLException e) {
					e.printStackTrace();
				}
			%> 
		</div>

		<footer>
			<p>&copy; 2023 Fast Travel. All rights reserved.</p>
		</footer>
	</body>
</html>
