<%@ page contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>

<html>
	<head>
		<link rel="stylesheet" href="./res/css/pages.css">
		<title>Representatives</title>
	</head>

	<body>
		<header class="rep-header">
			<img class="logo" src="./res/images/funLogo.png" alt="Fast Travel">

			<nav>
				<ul>
					<li><a href="validRep.jsp">Home</a></li>
					<li><a href="about.jsp">About</a></li>
					<li><a href="profile.jsp"><%= session.getAttribute("user")%></a></li>
				</ul>
			</nav>
		</header>
		<div class="rep-header-shadow"></div>
		
		<div class="fixed-sidebar">
			<table>
				<tr>
					<td><a href="flight_user_reservations.jsp">Manage Reservations</a></td>
				</tr>
				<tr>
					<form method="post", action="jAdd_Aircraft.jsp">
					<td><input type="submit" value="Add Aircraft" class="btn"/></td>
					<td><input type="text" placeholder="111" name="new_aircraft"></td>
					<td><input type="text" placeholder="1" name="real_company"></td>
					</form>
				</tr>
				<tr>
					<form method="post", action="jEdit_Aircraft.jsp">
					<td><input type="submit" value="Edit Aircraft" class="btn"/></td>
					<td><input type="text" placeholder="111" name="old_aircraft"></td>
					<td><input type="text" placeholder="122" name="n_aircraft"></td>
					<td><input type="text" placeholder="1" name="n_company"></td>
					</form>
				</tr>
				<tr>
					<form method="post", action="jAdd_Airport.jsp">
					<td><input type="submit" value="Add Airport" class="btn"/></td>
					<td><input type="text" placeholder="ACI" name="new_airport"></td>
					<td><input type="text" placeholder="Air Casual Iceland" name="new_place"></td>
					</form>
				</tr>
				<tr>
					<form method="post", action="jEdit_Airport.jsp">
					<td><input type="submit" value="Edit Airport" class="btn"/></td>
					<td><input type="text" placeholder="ACH" name="old_airport"></td>
					<td><input type="text" placeholder="ACI" name="n_airport"></td>
					<td><input type="text" placeholder="Air Casual Iceland" name="n_place"></td>
					</form>
				</tr>
				<tr>
					<form method="post", action="flight_passengers.jsp">
					<td><input type="submit" value="List Waiting Passengers" class="btn"/></td>
					<td><input type="text" placeholder="1" name="flight"></td>
					</form>
				</tr>
				<tr>
					<form method="post", action="flight_airport.jsp">
					<td><input type="submit" value="Flights for Airport" class="btn"/></td>
					<td><input type="text" placeholder="AAA" name="airport"></td>
					</form>
				</tr>
				<tr>
					<td><a href="manage_flights.jsp">Manage Flights</a></td>
				</tr>
				<tr>
					<td><a href="questions.jsp">Forums</a></td>
				</tr>
			</table>
		</div>
		
		<div class="content">
			<h2>Flights</h2>
				<%
					String port = request.getParameter("airport");
					try {
						Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
						e.printStackTrace();
					} 
					
					try { 
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Icarus","root","root");
					Statement st = con.createStatement();
					ResultSet t = st.executeQuery("SELECT * FROM Flights WHERE DepartLocation='"+ port +"' OR ArrivalLocation='"+ port +"'");
				
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
