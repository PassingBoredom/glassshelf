<%@ page import="java.sql.*" %>
<%@ page import="com.cs336.pkg.UserService" %>

<html>
	<body>
      <%
         String old_name = request.getParameter("ogr_username");
         String userid = request.getParameter("nr_username");
         String password = request.getParameter("nr_password");
         String firstname = request.getParameter("nr_fname");
         String lastname = request.getParameter("nr_lname");
         
         UserService userService = new UserService();
         
         if (userService.isUsernameTaken(old_name)) {
         	userService.editUser(old_name, userid, password, firstname, lastname);
         	response.sendRedirect("manager.jsp");
         } else {
         	out.println("Username does not exist <a href='manager.jsp'>try again</a>");
         }
         %>
	</body>
</html>
