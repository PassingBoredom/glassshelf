<%@ page import="java.sql.*" %>
<%@ page import="com.cs336.pkg.UserService" %>

<html>
	<body>
		<%
			String target = request.getParameter("del_airport");
			UserService userService = new UserService();
		
			if (userService.isAirport(target)) {
				userService.deleteAirport(target);
				response.sendRedirect("validRep.jsp");
			} else {
				out.println("Airport doesn't exists <a href='validRep.jsp'>try again</a>");
			}
		%>
	</body>
</html>