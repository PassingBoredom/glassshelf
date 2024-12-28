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
					<td><a href="logout.jsp">Log out</a></td>
				</tr>
				<tr>
					<form method="post", action="portfolio.jsp">
					<td><input type="submit" value="View History" class="btn"/></td>
					</form>
				</tr>
			</table>
		</div>
		
		<div class="content">
			<%
				String curr = "";
				if (session.getAttribute("user") != null) {
					curr = session.getAttribute("user").toString();
				}

				try {
					Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					e.printStackTrace();
				} 
				
				try { 
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Icarus","root","root");
				Statement st = con.createStatement();
				ResultSet t = st.executeQuery("SELECT * FROM Users WHERE username = '" + curr + "'");
			
				out.print("<table>");
					out.print("<tr>");
						out.print("<td>");
							out.print("Username");
						out.print("</td>");
						out.print("<td>");
							out.print("Role");
						out.print("</td>");
						out.print("<td>");
							out.print("First Name");
						out.print("</td>");
						out.print("<td>");
							out.print("Last Name");
						out.print("</td>");
					out.print("</tr>");
				
				while(t.next()) {
					out.print("<tr>");
						out.print("<td>");
							out.print(t.getString("username"));
						out.print("</td>");
						out.print("<td>");
							out.print(t.getString("Role"));
						out.print("</td>");
						out.print("<td>");
							out.print(t.getString("Fname"));
						out.print("</td>");
						out.print("<td>");
							out.print(t.getString("Lname"));
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
