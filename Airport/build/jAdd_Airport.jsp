<%@ page import="java.sql.*" %>
<%@ page import="com.cs336.pkg.UserService" %>

<html>
	<body>
		<%
			String id = request.getParameter("new_airport");
			String location = request.getParameter("new_place");
		
			UserService userService = new UserService();
		
			if (userService.isAirport(id)) {
				out.println("Aircraft already exists <a href='validRep.jsp'>try again</a>");
			} else {
				userService.addAirport(id, location);
				response.sendRedirect("validRep.jsp");
			}
		%>
	</body>
</html>