<%@ page contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>

<html>
<head>
	<meta http-equiv="Refresh" content="5;url=greetings.jsp"> 
	<title>Login</title>
</head>

	<body>
		<h1>OHIYO SEKAI GOOD MORNING WORLD</h1>
		This session belongs to <%= session.getAttribute("user")%>
	</body>
	
	<footer>
		<p>&copy; 2023 Fast Travel. All rights reserved.</p>
	</footer>
</html>
