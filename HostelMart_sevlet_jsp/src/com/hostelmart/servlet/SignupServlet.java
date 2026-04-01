package com.hostelmart.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.hostelmart.dao.UserDAO;
import com.hostelmart.model.User;

/**
 * SignupServlet - Handles user registration
 * Maps to: /signup
 */
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handle user signup (POST request)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Get form parameters
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");

            // Basic validation
            String errorMessage = "";
            
            if (name == null || name.trim().isEmpty()) {
                errorMessage = "Name is required!";
            } else if (email == null || email.trim().isEmpty()) {
                errorMessage = "Email is required!";
            } else if (!email.contains("@")) {
                errorMessage = "Invalid email format!";
            } else if (phone == null || phone.trim().isEmpty()) {
                errorMessage = "Phone number is required!";
            } else if (address == null || address.trim().isEmpty()) {
                errorMessage = "Address is required!";
            } else if (password == null || password.trim().isEmpty()) {
                errorMessage = "Password is required!";
            } else if (password.length() < 4) {
                errorMessage = "Password must be at least 4 characters!";
            } else if (!password.equals(confirmPassword)) {
                errorMessage = "Passwords do not match!";
            }

            // If validation fails, redirect with error
            if (!errorMessage.isEmpty()) {
                request.setAttribute("error", errorMessage);
                request.getRequestDispatcher("signup.jsp").forward(request, response);
                return;
            }

            // Check if email already exists
            UserDAO userDAO = new UserDAO();
            if (userDAO.emailExists(email)) {
                request.setAttribute("error", "Email already registered!");
                request.getRequestDispatcher("signup.jsp").forward(request, response);
                return;
            }

            // Create user object and register
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPhone(phone);
            user.setAddress(address);
            user.setPassword(password);

            boolean isRegistered = userDAO.registerUser(user);

            if (isRegistered) {
                // Registration successful - redirect to login
                request.setAttribute("success", "Registration successful! Please login.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Registration failed! Please try again.");
                request.getRequestDispatcher("signup.jsp").forward(request, response);
            }

        } catch (Exception e) {
            System.err.println("Error in SignupServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An error occurred during registration!");
            try {
                request.getRequestDispatcher("signup.jsp").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Redirect GET requests to signup page
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("signup.jsp");
    }
}
