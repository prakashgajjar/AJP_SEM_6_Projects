<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product - HostelMart</title>
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

    <!-- Add Product Form Section -->
    <div class="form-container">
        <div class="form-box">
            <h2>Add New Product</h2>

            <!-- Display Error Message -->
            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
                <div class="alert alert-error">
                    <%= error %>
                </div>
            <%
                }
            %>

            <form action="product" method="POST">
                <input type="hidden" name="action" value="add">

                <div class="form-group">
                    <label for="name">Product Name:</label>
                    <input type="text" id="name" name="name" required>
                </div>

                <div class="form-group">
                    <label for="price">Price (₹):</label>
                    <input type="number" id="price" name="price" step="0.01" min="0" required>
                </div>

                <div class="form-group">
                    <label for="quantity">Quantity:</label>
                    <input type="number" id="quantity" name="quantity" min="0" required>
                </div>

                <button type="submit" class="btn btn-primary" style="width: 100%; margin-top: 15px;">Add Product</button>
            </form>

            <p class="form-footer">
                <a href="product?action=view">← Back to Products</a>
            </p>
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
