<%@ page contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>

<html>
<head>
	<link rel="stylesheet" href="./res/css/welcome.css">
	<title>Greetings</title>
</head>

	<body>
		<div id="welcome-container">
			<h1 class="welcome-mat" align="center">WELCOME</h1>
			<ul>
				<li><a href="register.jsp">Sign Up</a></li>
				<li><a href="login.jsp">Login</a></li>
			</ul>
		</div>
		
	    <footer class="footer">
			<p>&copy; 2023 Fast Travel. All rights reserved.</p>
		</footer>
	</body>
</html>
