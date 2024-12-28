<%@ page import="java.sql.*" %>
<%@ page import="com.cs336.pkg.UserService" %>

<html>
	<body>
		<%
			String target = request.getParameter("del_aircraft");
			UserService userService = new UserService();
		
			if (userService.isAircraft(target)) {
				userService.deleteAircraft(target);
				response.sendRedirect("validRep.jsp");
			} else {
				out.println("Aircraft doesn't exists <a href='validRep.jsp'>try again</a>");
			}
		%>
	</body>
</html>