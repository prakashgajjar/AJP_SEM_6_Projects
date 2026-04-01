<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.hostelmart.model.Order" %>
<%@ page import="com.hostelmart.dao.OrderDAO" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Purchases - HostelMart</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .purchases-container {
            max-width: 1000px;
            margin: 30px auto;
            padding: 20px;
        }
        
        .purchases-header {
            margin-bottom: 25px;
        }
        
        .purchases-header h2 {
            color: #000;
            margin-bottom: 10px;
        }
        
        .table-container {
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
            overflow: hidden;
            border: 1px solid #e0e0e0;
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
        }
        
        th {
            background: #e8e8e8;
            color: #000;
            padding: 15px;
            text-align: left;
            font-weight: bold;
            border-bottom: 1px solid #d0d0d0;
        }
        
        td {
            padding: 12px 15px;
            border-bottom: 1px solid #e0e0e0;
            color: #333;
        }
        
        tr:hover {
            background: #fafafa;
        }
        
        .status-badge {
            display: inline-block;
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: bold;
        }
        
        .status-pending {
            background: #f0f0f0;
            color: #333;
            border: 1px solid #d0d0d0;
        }
        
        .status-confirmed {
            background: #e8e8e8;
            color: #333;
            border: 1px solid #c0c0c0;
        }
        
        .status-delivered {
            background: #e0e0e0;
            color: #333;
            border: 1px solid #b0b0b0;
        }
        
        .status-cancelled {
            background: #d8d8d8;
            color: #333;
            border: 1px solid #a0a0a0;
        }
        
        .empty-message {
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
            padding: 40px;
            text-align: center;
            color: #333;
            border: 1px solid #e0e0e0;
        }
        
        .empty-message p {
            margin: 10px 0;
            font-size: 16px;
            color: #333;
        }
        
        .btn {
            display: inline-block;
            padding: 10px 20px;
            background: linear-gradient(135deg, #667eea, #764ba2);
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-top: 15px;
            border: none;
            cursor: pointer;
        }
        
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
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
                <li><a href="myPurchases.jsp" style="color: #667eea; border-bottom: 2px solid #667eea;">My Purchases</a></li>
                <li><a href="logout">Logout</a></li>
            </ul>
        </div>
    </nav>

    <!-- Purchase History Section -->
    <div class="purchases-container">
        <div class="purchases-header">
            <h2>Purchase History</h2>
            <p>View all your purchases and their status</p>
        </div>

        <%
            Integer userId = (Integer) session.getAttribute("userId");
            
            if (userId == null) {
                response.sendRedirect("login.jsp");
                return;
            }
            
            OrderDAO orderDAO = new OrderDAO();
            List<Order> orders = orderDAO.getBuyerOrders(userId);
            
            if (orders != null && !orders.isEmpty()) {
        %>

        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Product Name</th>
                        <th>Quantity</th>
                        <th>Total Price</th>
                        <th>Status</th>
                        <th>Order Date</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (Order order : orders) {
                            String statusClass = "";
                            switch (order.getStatus()) {
                                case "pending":
                                    statusClass = "status-pending";
                                    break;
                                case "confirmed":
                                    statusClass = "status-confirmed";
                                    break;
                                case "delivered":
                                    statusClass = "status-delivered";
                                    break;
                                case "cancelled":
                                    statusClass = "status-cancelled";
                                    break;
                            }
                    %>
                    <tr>
                        <td>#<%= order.getId() %></td>
                        <td><%= order.getProductName() %></td>
                        <td><%= order.getQuantity() %> units</td>
                        <td>₹<%= String.format("%.2f", order.getPrice()) %></td>
                        <td><span class="status-badge <%= statusClass %>"><%= order.getStatus().toUpperCase() %></span></td>
                        <td><%= order.getCreatedAt() %></td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>

        <%
            } else {
        %>

        <div class="empty-message">
            <p>You haven't made any purchases yet!</p>
            <a href="product?action=view" class="btn">Browse Products</a>
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
