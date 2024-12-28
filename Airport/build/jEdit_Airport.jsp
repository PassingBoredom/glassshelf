<%@ page import="java.sql.*" %>
<%@ page import="com.cs336.pkg.UserService" %>

<html>
	<body>
		<%
			String target = request.getParameter("old_airport");
			String id = request.getParameter("n_airport");
			String location = request.getParameter("n_place");
		
			UserService userService = new UserService();
		
			if (userService.isAirport(target)) {
				userService.editAirport(target, id, location);
				response.sendRedirect("validRep.jsp");
			} else {
				out.println("Airport doesn't exists <a href='validRep.jsp'>try again</a>");
			}
		%>
	</body>
</html>