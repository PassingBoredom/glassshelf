<%@ page import="java.sql.*" %>
<%@ page import="com.cs336.pkg.UserService" %>

<html>
<body>
<%
	String userid = request.getParameter("r_username");
	String password = request.getParameter("r_password");
	String firstname = request.getParameter("r_fname");
	String lastname = request.getParameter("r_lname");

    UserService userService = new UserService();

    if (userService.isUsernameTaken(userid)) {
        out.println("Username already taken <a href='register.jsp'>try again</a>");
    } else {
        userService.registerRep(userid, password, firstname, lastname);
        response.sendRedirect("manager.jsp");
    }
%>
</body>
</html>