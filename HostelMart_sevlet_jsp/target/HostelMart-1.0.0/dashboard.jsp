<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - HostelMart</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <%
        // Check if user is logged in
        HttpSession session1 = request.getSession(false);
        if (session1 == null || session1.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String userName = (String) session1.getAttribute("userName");
    %>

    <!-- Navigation Bar -->
    <nav class="navbar">
        <div class="container">
            <div class="logo">HostelMart</div>
            <ul class="nav-links">
                <li><a href="index.jsp">Home</a></li>
                <li><a href="dashboard.jsp">Dashboard</a></li>
                <li><a href="product?action=view">Products</a></li>
                <li><a href="logout">Logout</a></li>
                <li><span class="user-name">Welcome, <%= userName %></span></li>
            </ul>
        </div>
    </nav>

    <!-- Dashboard Section -->
    <div class="container" style="min-height: 85vh; padding: 40px 0;">
        <h1>Dashboard</h1>
        <p style="font-size: 18px; margin-bottom: 30px;">Hello <strong><%= userName %></strong>, welcome to HostelMart!</p>

        <div class="dashboard-grid">
            <div class="dashboard-card">
                <h3>📦 Product Management</h3>
                <p>Manage your hostel products</p>
                <div class="dashboard-links">
                    <a href="product?action=view" class="btn btn-primary">View Products</a>
                    <a href="addProduct.jsp" class="btn btn-secondary">Add Product</a>
                </div>
            </div>

            <div class="dashboard-card">
                <h3>📊 Quick Stats</h3>
                <p>View your inventory statistics</p>
                <div class="dashboard-links">
                    <a href="product?action=view" class="btn btn-primary">View Inventory</a>
                </div>
            </div>

            <div class="dashboard-card">
                <h3>👤 Profile</h3>
                <p>Manage your account settings</p>
                <div class="dashboard-links">
                    <a href="logout" class="btn btn-danger">Logout</a>
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
