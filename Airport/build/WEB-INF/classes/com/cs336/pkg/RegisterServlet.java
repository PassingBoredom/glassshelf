package com.cs336.pkg;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

// @WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

			String userid = request.getParameter("username");
			String password = request.getParameter("password");
			String firstname = request.getParameter("Fname");
			String lastname = request.getParameter("Lname");

			UserService userService = new UserService(); 
			// boolean isUsernameTaken = userService.isUsernameTaken(userid);

			if (userService.isUsernameTaken(userid)) {
				response.getWriter().println("Username already taken <a href='register.jsp'>try again</a>");
				// response.sendRedirect("TooBad.jsp");
			} else {
				userService.registerUser(userid, password, firstname, lastname);
				response.sendRedirect("toLogin.jsp");
			}
	}
}
