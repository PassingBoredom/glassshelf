<%@ page import="java.sql.*" %>
<%@ page import="com.cs336.pkg.UserService" %>

<html>
<body>
	<%
		String target = request.getParameter("del_username");
	
		UserService userService = new UserService();
	
		if (userService.isUsernameTaken(target)) {
			userService.deleteUser(target);
			response.sendRedirect("manager.jsp");
		} else {
			out.println("Username does not exist <a href='manager.jsp'>try again</a>");
		}
	%>
</body>
</html>