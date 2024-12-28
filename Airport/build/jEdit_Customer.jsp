<%@ page import="java.sql.*" %>
<%@ page import="com.cs336.pkg.UserService" %>

<html>
<body>
	<%
		String old_name = request.getParameter("ogc_username");
		String userid = request.getParameter("nc_username");
		String password = request.getParameter("nc_password");
		String firstname = request.getParameter("nc_fname");
		String lastname = request.getParameter("nc_lname");
	
		UserService userService = new UserService();
	
		if (userService.isUsernameTaken(old_name)) {
			userService.editUser(old_name, userid, password, firstname, lastname);
			response.sendRedirect("manager.jsp");
		} else {
			out.println("Username does not exist <a href='manager.jsp'>try again</a>");
		}
	%>	
</body>
</html>