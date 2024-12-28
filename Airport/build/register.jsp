<%@ page contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>

<html>
<head>
	<link rel="stylesheet" href="./res/css/buffer.css">
	<title>Sign Up</title>
</head>

	<body>
		<div id="registration-wrapper">
			<div id="registration-title-card">
				<h1 align="center">Create your account</h1>
			</div>
			
		<form method="post" action="jAddUser.jsp"> 
		<table>
			<tr>
				<td>Name </td>
				<td><input type="text" name="Fname" placeholder="First" class="name"/></td>
				<td><input type="text" name="Lname" placeholder="Last" class="name"/></td>
			</tr> 			
			<tr><td>Username </td><td colspan="2"><input type="text" name="username" class="long-input"/></td></tr>
			<tr><td>Password </td><td colspan="2"><input type="password" name="password" class="long-input"/></td></tr>
			<tr><td colspan="3"><input type="submit" name="submit" value="Sign Up" class="btn"/></td></tr>
		</table>	
		</form>
		</div>
		
		<footer class="footer">
			<p>&copy; 2023 Fast Travel. All rights reserved.</p>
		</footer>
	</body>
</html>