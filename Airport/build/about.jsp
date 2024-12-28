<%@ page contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>

<html>
	<head>
		<link rel="stylesheet" href="./res/css/pages.css">
		<title>About</title>
	</head>
	<body>
		<header class="info-header">
			<img class="logo" src="./res/images/funLogo.png" alt="Fast Travel">
			
			<nav>
				<ul>
					<li><a href="greetings.jsp">Home</a></li>
					<li><a href="about.jsp">About</a></li>
					<li><a href="login.jsp">Login</a></li>
				</ul>
			</nav>
		</header>
		<div class="info-header-shadow"></div>
	
		<h1>Description</h1>
		<p>Online Flight Reservation Project for Principles of Information and Data Management - CS 336.</p>
		<br>
	
		<h1>Contributors</h1>
		<table>
			<tr>
				<td>Contributors</td>
			</tr>
			<tr>
				<td>Alan Wang</td>
				<td>Owner, Manager, Producer, and QA Tester of this website</td>
			</tr>
			<tr>
				<td>"Shado"</td>
				<td>Helper in inspiration and feeding misinformation</td>
			</tr>
			<tr>
				<td>Ghost Member 1</td>
				<td>Nothing but waste my time</td>
			</tr>
			<tr>
				<td>Ghost Member 2</td>
				<td>Nothing but waste my time</td>
			</tr>
			<tr>
				<td>Ghost Member 3</td>
				<td>Nothing but waste my time</td>
			</tr>		
		</table>
		
		<footer>
			<p>&copy; 2023 Fast Travel. All rights reserved.</p>
		</footer>
		<% session.invalidate(); %>
  </body>
</html>
