<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.hostelmart.model.Product" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Products - HostelMart</title>
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
                <li><a href="myPurchases.jsp">My Purchases</a></li>
                <li><a href="logout">Logout</a></li>
                <li><span class="user-name">Welcome, <%= userName %></span></li>
            </ul>
        </div>
    </nav>

    <!-- Products Section -->
    <div class="container" style="min-height: 85vh; padding: 40px 0;">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px;">
            <h1>Products</h1>
            <a href="addProduct.jsp" class="btn btn-primary">+ Add New Product</a>
        </div>

        <!-- Display Success Message -->
        <%
            String message = (String) request.getAttribute("message");
            if (message != null) {
        %>
            <div class="alert alert-success">
                <%= message %>
            </div>
        <%
            }
        %>

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

        <!-- Products Table -->
        <%
            List<Product> products = (List<Product>) request.getAttribute("products");
            Integer userId = (Integer) request.getAttribute("userId");
            if (products != null && !products.isEmpty()) {
        %>
            <table class="product-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Product Name</th>
                        <th>Price (₹)</th>
                        <th>Quantity</th>
                        <th>Seller</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (Product product : products) {
                            boolean isOwnProduct = (userId != null && product.getSellerId() == userId);
                    %>
                        <tr>
                            <td><%= product.getId() %></td>
                            <td><%= product.getName() %></td>
                            <td>₹ <%= String.format("%.2f", product.getPrice()) %></td>
                            <td><%= product.getQuantity() %></td>
                            <td>
                                <%= product.getSellerName() != null ? product.getSellerName() : "Unknown" %>
                                <% if (isOwnProduct) { %>
                                    <br><span style="color: #000; font-size: 12px; font-weight: bold;">(Your Product)</span>
                                <% } %>
                            </td>
                            <td>
                                <% if (isOwnProduct) { %>
                                    <a href="product?action=edit&id=<%= product.getId() %>" class="btn btn-small btn-secondary">Edit</a>
                                    <a href="product?action=delete&id=<%= product.getId() %>" class="btn btn-small btn-danger" onclick="return confirm('Are you sure?');">Delete</a>
                                <% } else if (product.getQuantity() > 0) { %>
                                    <a href="product?action=viewSeller&id=<%= product.getId() %>" class="btn btn-small btn-primary">Buy</a>
                                <% } else { %>
                                    <span style="color: #888; font-size: 12px;">Out of Stock</span>
                                <% } %>
                            </td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        <%
            } else {
        %>
            <div style="text-align: center; padding: 40px; background-color: #f5f5f5; border-radius: 5px; border: 1px solid #e0e0e0;">
                <p style="font-size: 18px; color: #333;">No products found. <a href="addProduct.jsp" style="color: #000; font-weight: bold;">Add one now</a>!</p>
            </div>
        <%
            }
        %>
    </div>

    <!-- Footer -->
    <footer class="footer">
        <div class="container">
            <p>&copy; 2024 HostelMart. All Rights Reserved.</p>
        </div>
    </footer>
</body>
</html>
