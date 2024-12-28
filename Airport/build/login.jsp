<%@ page contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>

<html>
<head>
	<link rel="stylesheet" href="./res/css/buffer.css">
	<title>Login</title>
</head>

	<body>
		<div id="registration-wrapper">
			<div id="registration-title-card">
				<h1 align="center">Login</h1>
			</div>
		<form method="post" action="jFindUser.jsp"> 
		<table>	
			<tr><td>Username </td><td><input type="text" name="username" class="long-input"/></td></tr>
			<tr><td>Password </td><td><input type="password" name="password" class="long-input"/></td></tr>
			<tr><td colspan="3"><input type="submit" value="Login" class="btn"/></td></tr>
		</table>	
		</form>
		</div>
		
		<footer class="footer">
			<p>&copy; 2023 Fast Travel. All rights reserved.</p>
		</footer>
	</body>
</html>
