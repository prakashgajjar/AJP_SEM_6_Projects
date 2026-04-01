<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.hostelmart.model.Product" %>
<%@ page import="com.hostelmart.model.User" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Buy Product - HostelMart</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .buy-container {
            max-width: 700px;
            margin: 30px auto;
            padding: 20px;
        }
        
        .buy-card {
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
            padding: 30px;
            border: 1px solid #e0e0e0;
        }
        
        .product-info, .seller-info {
            margin-bottom: 25px;
            padding-bottom: 20px;
            border-bottom: 1px solid #e0e0e0;
        }
        
        .product-info h3, .seller-info h3 {
            color: #000;
            margin-bottom: 15px;
            font-size: 18px;
        }
        
        .info-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 12px;
            padding: 10px 0;
        }
        
        .info-row label {
            font-weight: bold;
            color: #000;
            min-width: 120px;
        }
        
        .info-row span {
            color: #333;
        }
        
        .seller-contact {
            background: #f5f5f5;
            padding: 15px;
            border-radius: 5px;
            margin-top: 10px;
            border: 1px solid #e0e0e0;
        }
        
        .seller-contact p {
            margin: 8px 0;
            color: #333;
        }
        
        .phone-display {
            color: #000;
            font-weight: bold;
            font-size: 16px;
        }
        
        .purchase-form {
            margin-top: 20px;
        }
        
        .form-group {
            margin-bottom: 15px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #000;
        }
        
        .form-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #d0d0d0;
            border-radius: 4px;
            font-size: 14px;
            background: #fff;
            color: #000;
        }
        
        .button-group {
            display: flex;
            gap: 10px;
            margin-top: 25px;
        }
        
        .btn {
            flex: 1;
            padding: 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            font-weight: bold;
            transition: all 0.3s ease;
        }
        
        .btn-buy {
            background: #d0d0d0;
            color: #000;
            border: 1px solid #c0c0c0;
        }
        
        .btn-buy:hover {
            background: #c0c0c0;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }
        
        .btn-cancel {
            background: #e8e8e8;
            color: #000;
            border: 1px solid #d0d0d0;
        }
        
        .btn-cancel:hover {
            background: #d8d8d8;
        }
        
        .alert {
            padding: 12px;
            margin-bottom: 15px;
            border-radius: 4px;
            font-size: 14px;
        }
        
        .alert-error {
            background: #f5f5f5;
            color: #333;
            border-left: 4px solid #888;
        }
        
        .alert-success {
            background: #f5f5f5;
            color: #333;
            border-left: 4px solid #888;
        }
    </style>
</head>
<body>
    <!-- Navigation Bar -->
    <nav class="navbar">
        <div class="container">
            <div class="logo">HostelMart</div>
            <ul class="nav-links">
                <li><a href="dashboard.jsp">Dashboard</a></li>
                <li><a href="product?action=view">Browse Products</a></li>
                <li><a href="myPurchases.jsp">My Purchases</a></li>
                <li><a href="logout">Logout</a></li>
            </ul>
        </div>
    </nav>

    <!-- Buy Product Section -->
    <div class="buy-container">
        <div class="buy-card">
            <h2>Purchase Product</h2>

            <!-- Display Messages -->
            <%
                String error = (String) request.getAttribute("error");
                String message = (String) request.getAttribute("message");
                if (error != null) {
            %>
                <div class="alert alert-error"><%= error %></div>
            <%
                }
                if (message != null) {
            %>
                <div class="alert alert-success"><%= message %></div>
            <%
                }
            %>

            <%
                Product product = (Product) request.getAttribute("product");
                User seller = (User) request.getAttribute("seller");
                
                if (product != null && seller != null) {
            %>

            <!-- Product Information -->
            <div class="product-info">
                <h3>Product Details</h3>
                <div class="info-row">
                    <label>Product Name:</label>
                    <span><%= product.getName() %></span>
                </div>
                <div class="info-row">
                    <label>Price per Unit:</label>
                    <span>₹<%= String.format("%.2f", product.getPrice()) %></span>
                </div>
                <div class="info-row">
                    <label>Available Quantity:</label>
                    <span><%= product.getQuantity() %> units</span>
                </div>
            </div>

            <!-- Seller Information -->
            <div class="seller-info">
                <h3>Seller Information</h3>
                <div class="info-row">
                    <label>Seller Name:</label>
                    <span><%= seller.getName() %></span>
                </div>
                <div class="seller-contact">
                    <p><strong>Contact Details:</strong></p>
                    <p><strong>Phone:</strong> <span class="phone-display"><%= seller.getPhone() %></span></p>
                    <p><strong>Address:</strong> <%= seller.getAddress() %></p>
                </div>
            </div>

            <!-- Purchase Form -->
            <form action="product" method="POST" class="purchase-form">
                <input type="hidden" name="action" value="buy">
                <input type="hidden" name="productId" value="<%= product.getId() %>">

                <div class="form-group">
                    <label for="quantity">Quantity to Purchase:</label>
                    <input type="number" id="quantity" name="quantity" min="1" max="<%= product.getQuantity() %>" value="1" required>
                </div>

                <div class="button-group">
                    <button type="submit" class="btn btn-buy">Confirm Purchase</button>
                    <a href="product?action=view" class="btn btn-cancel" style="text-decoration: none; display: flex; align-items: center; justify-content: center;">Cancel</a>
                </div>
            </form>

            <%
                } else {
            %>
                <div class="alert alert-error">Product or seller information not found!</div>
                <a href="product?action=view" class="btn btn-cancel" style="display: inline-block; padding: 10px 20px;">Back to Products</a>
            <%
                }
            %>
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
