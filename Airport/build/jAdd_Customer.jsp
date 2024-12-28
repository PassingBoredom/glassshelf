<%@ page import="java.sql.*" %>
<%@ page import="com.cs336.pkg.UserService" %>

<html>
<body>
<%
	String userid = request.getParameter("a_username");
	String password = request.getParameter("a_password");
	String firstname = request.getParameter("a_fname");
	String lastname = request.getParameter("a_lname");

    UserService userService = new UserService();

    if (userService.isUsernameTaken(userid)) {
        out.println("Username already taken <a href='register.jsp'>try again</a>");
    } else {
        userService.registerUser(userid, password, firstname, lastname);
        response.sendRedirect("manager.jsp");
    }
%>
</body>
</html>