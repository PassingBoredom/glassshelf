<%@ page import="java.sql.*" %>
<%@ page import="com.cs336.pkg.UserService" %>

<html>
	<body>
		<%
			String id = request.getParameter("new_aircraft");
			String owner_company = request.getParameter("real_company");
		
			UserService userService = new UserService();
		
			if (userService.isAircraft(id)) {
				out.println("Aircraft already exists <a href='validRep.jsp'>try again</a>");
			} else {
				userService.addAircraft(id, owner_company);
				response.sendRedirect("validRep.jsp");
			}
		%>
	</body>
</html>