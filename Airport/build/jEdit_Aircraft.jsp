<%@ page import="java.sql.*" %>
<%@ page import="com.cs336.pkg.UserService" %>

<html>
	<body>
		<%
			String target = request.getParameter("old_aircraft");
			String id = request.getParameter("n_aircraft");
			String owner_company = request.getParameter("n_company");
		
			UserService userService = new UserService();
		
			if (userService.isAircraft(target)) {
				userService.editAircraft(target, id, owner_company);
				response.sendRedirect("validRep.jsp");
			} else {
				out.println("Aircraft doesn't exists <a href='validRep.jsp'>try again</a>");
			}
		%>
	</body>
</html>