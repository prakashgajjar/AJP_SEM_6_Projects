package com.hostelmart.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.hostelmart.dao.UserDAO;
import com.hostelmart.model.User;

/**
 * LoginServlet - Handles user login and session management
 * Maps to: /login
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handle user login (POST request)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Get form parameters
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Basic validation
            String errorMessage = "";
            
            if (email == null || email.trim().isEmpty()) {
                errorMessage = "Email is required!";
            } else if (password == null || password.trim().isEmpty()) {
                errorMessage = "Password is required!";
            }

            // If validation fails
            if (!errorMessage.isEmpty()) {
                request.setAttribute("error", errorMessage);
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            // Verify credentials
            UserDAO userDAO = new UserDAO();
            User user = userDAO.loginUser(email, password);

            if (user != null) {
                // Login successful - create session
                HttpSession session = request.getSession(true);
                session.setAttribute("userId", user.getId());
                session.setAttribute("userName", user.getName());
                session.setAttribute("userEmail", user.getEmail());
                session.setMaxInactiveInterval(30 * 60);  // 30 minutes session timeout

                // Redirect to dashboard
                response.sendRedirect("dashboard.jsp");
            } else {
                // Login failed
                request.setAttribute("error", "Invalid email or password!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            System.err.println("Error in LoginServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An error occurred during login!");
            try {
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Redirect GET requests to login page
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}
