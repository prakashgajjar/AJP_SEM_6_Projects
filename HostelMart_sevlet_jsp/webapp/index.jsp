<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HostelMart - Home</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <!-- Navigation Bar -->
    <nav class="navbar">
        <div class="container">
            <div class="logo">HostelMart</div>
            <ul class="nav-links">
                <li><a href="index.jsp">Home</a></li>
                <%
                    HttpSession session1 = request.getSession(false);
                    if (session1 != null && session1.getAttribute("userId") != null) {
                        String userName = (String) session1.getAttribute("userName");
                %>
                    <li><a href="dashboard.jsp">Dashboard</a></li>
                    <li><a href="logout">Logout</a></li>
                    <li><span class="user-name">Welcome, <%= userName %></span></li>
                <%
                    } else {
                %>
                    <li><a href="login.jsp">Login</a></li>
                    <li><a href="signup.jsp">Sign Up</a></li>
                <%
                    }
                %>
            </ul>
        </div>
    </nav>

    <!-- Hero Section -->
    <div class="hero">
        <div class="hero-content">
            <h1>Welcome to HostelMart</h1>
            <p>Manage your hostel products efficiently</p>
            <%
                if (session1 == null || session1.getAttribute("userId") == null) {
            %>
                <a href="login.jsp" class="btn btn-primary">Login to Start</a>
                <a href="signup.jsp" class="btn btn-secondary">Create Account</a>
            <%
                } else {
            %>
                <a href="dashboard.jsp" class="btn btn-primary">Go to Dashboard</a>
            <%
                }
            %>
        </div>
    </div>

    <!-- Features Section -->
    <div class="features">
        <div class="container">
            <h2>Features</h2>
            <div class="feature-grid">
                <div class="feature-card">
                    <h3>Easy Authentication</h3>
                    <p>Secure login and registration for users</p>
                </div>
                <div class="feature-card">
                    <h3>Product Management</h3>
                    <p>Add, edit, and delete products easily</p>
                </div>
                <div class="feature-card">
                    <h3>Real-time Updates</h3>
                    <p>Keep track of all products and inventory</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="footer">
        <div class="container">
            <p>&copy; 2024 HostelMart. All Rights Reserved.</p>
        </div>
    </footer>
</body>
</html>
