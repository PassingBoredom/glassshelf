<%@ page import ="java.sql.*" %>
<%@ page import="com.cs336.pkg.UserService" %>

<html>
<body>
	<%
		session.invalidate();
		response.sendRedirect("greetings.jsp");
	%>
</body>
</html>