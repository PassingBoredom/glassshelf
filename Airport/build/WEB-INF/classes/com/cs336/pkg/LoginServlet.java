package com.cs336.pkg;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

// @WebServlet("/login")
public class LoginServlet extends HttpServlet { 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

			System.out.println("I have been called");
			String userid = request.getParameter("username");
			String password = request.getParameter("password");

			UserService userService = new UserService();
			// boolean realUser = userService.isCorrectUser(userid, password); 

			System.out.println(userService.isCorrectUser(userid, password));
			if(!userService.isCorrectUser(userid, password)) {
				// response.getWriter().println("Incorrect User <a href='login.jsp'>try again</a>");
				response.sendRedirect(request.getContextPath() + "TooBad.jsp");
			} else { 
				switch(userService.getUserRole(userid)) {
					case "Admin": 
						response.sendRedirect("validAdmin.jsp");
						break;
					case "Representative": 
						response.sendRedirect("validRep.jsp");
						break;
					case "Customer":  
						response.sendRedirect("validCustomer.jsp");
						break;
					default: 
						response.sendRedirect("TooBad.jsp");
						break;
				}
			}
	}

}
