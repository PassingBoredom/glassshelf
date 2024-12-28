<%@ page import ="java.sql.*" %>
<%@ page import="com.cs336.pkg.UserService" %>

<html>
<body>
<%
	String userid = request.getParameter("username");
	String password = request.getParameter("password");
	
	UserService userService = new UserService();
	
	if(!userService.isCorrectUser(userid, password)) {
		out.println("Incorrect login <a href='login.jsp'>try again</a>");
	} else { 
		switch(userService.getUserRole(userid)) {
			case "Admin":
				session.setAttribute("user", userid);
				response.sendRedirect("validAdmin.jsp");
				break;
			case "Customer":
				session.setAttribute("user", userid);
				response.sendRedirect("validCustomer.jsp");
				break;
			case "Representative":
				session.setAttribute("user", userid);
				response.sendRedirect("validRep.jsp");
				break;
			case "NULL":
				session.setAttribute("user", userid);
				response.sendRedirect("TooBad.jsp");
				break;
		}
	}
%>
</body>
</html>