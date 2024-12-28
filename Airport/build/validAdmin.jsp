<%@ page contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>

<html>
	<head>
		<link rel="stylesheet" href="./res/css/pages.css">
		<title>Administration</title>
	</head>

	<body>
		<header class="admin-header">
			<img class="logo" src="./res/images/funLogo.png" alt="Fast Travel">

			<nav>
				<ul>
					<li><a href="validAdmin.jsp">Home</a></li>
					<li><a href="about.jsp">About</a></li>
					<li><a href="profile.jsp"><%= session.getAttribute("user")%></a></li>	
				</ul>
			</nav>
		</header>
		<div class="admin-header-shadow"></div>
		
		<div class="fixed-sidebar">
			<table>
				<tr>
					<td><a href="manager.jsp">Manage Users</a></td>
				</tr>
				<tr>
					<form method="post", action="sales.jsp">
					<td><input type="submit" value="Sales Report" class="btn"/></td>
					<td><input type="text" placeholder="01" name="month"></td>
					</form>
				</tr>
				<tr>
					<form method="post", action="reservations_flight.jsp">
					<td><input type="submit" value="Flight Reservations" class="btn"/></td>
					<td><input type="text" placeholder="1" name="n_flight"></td>
					</form>
				</tr>
				<tr>
					<form method="post", action="reservations_customer.jsp">
					<td><input type="submit" value="Customer Reservations" class="btn"/></td>
					<td><input type="text" placeholder="FirstUser" name="user_username"></td>
					</form>
				</tr>
				<tr>
					<form method="post", action="revenue_flight.jsp">
					<td><input type="submit" value="Flight Revenue" class="btn"/></td>
					<td><input type="text" placeholder="1" name="flight_n"></td>
					</form>
				</tr>
				<tr>
					<form method="post", action="revenue_airline.jsp">
					<td><input type="submit" value="Airline Revenue" class="btn"/></td>
					<td><input type="text" placeholder="Harmless Company" name="airline_name"></td>
					</form>
				</tr>
				<tr>
					<form method="post", action="revenue_customer.jsp">
					<td><input type="submit" value="Customer Revenue" class="btn"/></td>
					<td><input type="text" placeholder="FirstUser" name="customer_revenue"></td>
					</form>
				</tr>
				<tr>
					<td><a href="MVP_customer.jsp">Most Valued Customer</a></td>
				</tr>
				<tr>
					<td><a href="MVP_flight.jsp">Most Valued Flight</a></td>
				</tr>
			</table>
		</div>

		<footer>
			<p>&copy; 2023 Fast Travel. All rights reserved.</p>
		</footer>
	</body>
</html>
