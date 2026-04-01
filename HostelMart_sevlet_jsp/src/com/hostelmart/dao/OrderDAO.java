package com.hostelmart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.hostelmart.model.Order;
import com.hostelmart.util.DBUtil;

/**
 * Order Data Access Object (DAO) Class
 * Handles all database operations for Order entity
 */
public class OrderDAO {

    public boolean createOrder(Order order) {
        String query = "INSERT INTO orders (buyer_id, seller_id, product_id, quantity, price, status) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, order.getBuyerId());
            pstmt.setInt(2, order.getSellerId());
            pstmt.setInt(3, order.getProductId());
            pstmt.setInt(4, order.getQuantity());
            pstmt.setDouble(5, order.getPrice());
            pstmt.setString(6, order.getStatus());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error creating order: " + e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver not found: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get all orders for a buyer
     */
    public List<Order> getBuyerOrders(int buyerId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE buyer_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, buyerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setBuyerId(rs.getInt("buyer_id"));
                order.setSellerId(rs.getInt("seller_id"));
                order.setProductId(rs.getInt("product_id"));
                order.setQuantity(rs.getInt("quantity"));
                order.setPrice(rs.getDouble("price"));
                order.setStatus(rs.getString("status"));
                order.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                
                orders.add(order);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving buyer orders: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver not found: " + e.getMessage());
        }
        
        return orders;
    }

    /**
     * Get all orders for a seller
     */
    public List<Order> getSellerOrders(int sellerId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE seller_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, sellerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setBuyerId(rs.getInt("buyer_id"));
                order.setSellerId(rs.getInt("seller_id"));
                order.setProductId(rs.getInt("product_id"));
                order.setQuantity(rs.getInt("quantity"));
                order.setPrice(rs.getDouble("price"));
                order.setStatus(rs.getString("status"));
                order.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                
                orders.add(order);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving seller orders: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver not found: " + e.getMessage());
        }
        
        return orders;
    }

    /**
     * Update order status
     * @param orderId ID of the order
     * @param status New status
     * @return true if updated successfully, false otherwise
     */
    public boolean updateOrderStatus(int orderId, String status) {
        String query = "UPDATE orders SET status = ? WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, status);
            pstmt.setInt(2, orderId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating order status: " + e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver not found: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get order details by ID
     * @param orderId ID of the order
     * @return Order object if found, null otherwise
     */
    public Order getOrderById(int orderId) {
        String query = "SELECT * FROM orders WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setBuyerId(rs.getInt("buyer_id"));
                order.setSellerId(rs.getInt("seller_id"));
                order.setProductId(rs.getInt("product_id"));
                order.setQuantity(rs.getInt("quantity"));
                order.setPrice(rs.getDouble("price"));
                order.setStatus(rs.getString("status"));
                order.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                
                return order;
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving order: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver not found: " + e.getMessage());
        }
        
        return null;
    }
}
