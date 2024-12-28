<%@ page import="java.sql.*" %>
<%@ page import="com.cs336.pkg.UserService" %>

<html>
<body>
<%
	String userid = request.getParameter("username");
	String password = request.getParameter("password");
	String firstname = request.getParameter("Fname");
	String lastname = request.getParameter("Lname");

    UserService userService = new UserService();

    if (userService.isUsernameTaken(userid)) {
        out.println("Username already taken <a href='register.jsp'>try again</a>");
    } else {
        userService.registerUser(userid, password, firstname, lastname);
        response.sendRedirect("toLogin.jsp");
    }
%>
</body>
</html>