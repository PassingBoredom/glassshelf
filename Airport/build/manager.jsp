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
					<form method="post", action="jAdd_Customer.jsp">
					<td><input type="submit" value="Add Customer" class="btn"/></td>
					<td><input type="text" placeholder="my Username" name="a_username"></td>
					<td><input type="text" placeholder="my password" name="a_password"></td>
					<td><input type="text" placeholder="John" name="a_fname"></td>
					<td><input type="text" placeholder="Doe" name="a_lname"></td>
					</form>
				</tr>
				<tr>
					<form method="post", action="jEdit_Customer.jsp">
					<td><input type="submit" value="Edit Customer" class="btn"/></td>
					<td><input type="text" placeholder="Customer Username" name="ogc_username"></td>
					<td><input type="text" placeholder="new Username" name="nc_username"></td>
					<td><input type="text" placeholder="new password" name="nc_password"></td>
					<td><input type="text" placeholder="James" name="nc_fname"></td>
					<td><input type="text" placeholder="Smith" name="nc_lname"></td>
					</form>
				</tr>
				<tr>
					<form method="post", action="jAdd_Rep.jsp">
					<td><input type="submit" value="Add Rep" class="btn"/></td>
					<td><input type="text" placeholder="my Username" name="r_username"></td>
					<td><input type="text" placeholder="my password" name="r_password"></td>
					<td><input type="text" placeholder="John" name="r_fname"></td>
					<td><input type="text" placeholder="Doe" name="r_lname"></td>
					</form>
				</tr>
				<tr>
					<form method="post", action="jEdit_Rep.jsp">
					<td><input type="submit" value="Edit Rep" class="btn"/></td>
					<td><input type="text" placeholder="Rep Username" name="ogr_username"></td>
					<td><input type="text" placeholder="new Username" name="nr_username"></td>
					<td><input type="text" placeholder="new password" name="nr_password"></td>
					<td><input type="text" placeholder="James" name="nr_fname"></td>
					<td><input type="text" placeholder="Smith" name="nr_lname"></td>
					</form>
				</tr>
				<tr>
					<form method="post", action="jDelete_User.jsp">
					<td><input type="submit" value="Delete User" class="btn"/></td>
					<td><input type="text" placeholder="User Username" name="del_username"></td>
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
				ResultSet t = st.executeQuery("SELECT * FROM Users");
			
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
