package com.hostelmart.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * LogoutServlet - Handles user logout
 * Maps to: /logout
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handle user logout
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Get current session
            HttpSession session = request.getSession(false);

            // Invalidate session
            if (session != null) {
                session.invalidate();
                System.out.println("User session invalidated successfully!");
            }

            // Redirect to home page
            response.sendRedirect("index.jsp");

        } catch (Exception e) {
            System.err.println("Error in LogoutServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("index.jsp");
        }
    }

    /**
     * Also handle POST requests
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
