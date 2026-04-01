package com.hostelmart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hostelmart.model.Product;
import com.hostelmart.util.DBUtil;

/**
 * Product Data Access Object (DAO) Class
 * Handles all database operations for Product entity
 */
public class ProductDAO {

    /**
     * Add a new product
     * @param product Product object
     * @return true if product is added successfully, false otherwise
     */
    public boolean addProduct(Product product) {
        String query = "INSERT INTO products (name, price, quantity, seller_id) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setInt(4, product.getSellerId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding product: " + e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver not found: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get all products
     * @return List of all products
     */
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT p.*, u.name as seller_name, u.phone as seller_phone, u.address as seller_address FROM products p LEFT JOIN users u ON p.seller_id = u.id";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setSellerId(rs.getInt("seller_id"));
                product.setSellerName(rs.getString("seller_name"));
                product.setSellerPhone(rs.getString("seller_phone"));
                product.setSellerAddress(rs.getString("seller_address"));
                products.add(product);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving products: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver not found: " + e.getMessage());
        }
        
        return products;
    }

    /**
     * Get product by ID
     * @param id Product ID
     * @return Product object if found, null otherwise
     */
    public Product getProductById(int id) {
        String query = "SELECT p.*, u.name as seller_name, u.phone as seller_phone, u.address as seller_address FROM products p LEFT JOIN users u ON p.seller_id = u.id WHERE p.id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setSellerId(rs.getInt("seller_id"));
                product.setSellerName(rs.getString("seller_name"));
                product.setSellerPhone(rs.getString("seller_phone"));
                product.setSellerAddress(rs.getString("seller_address"));
                return product;
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving product: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver not found: " + e.getMessage());
        }
        
        return null;
    }

    /**
     * Update product
     * @param product Product object with updated values
     * @return true if product is updated successfully, false otherwise
     */
    public boolean updateProduct(Product product) {
        String query = "UPDATE products SET name = ?, price = ?, quantity = ? WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setInt(4, product.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating product: " + e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver not found: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete product
     * @param id Product ID
     * @return true if product is deleted successfully, false otherwise
     */
    public boolean deleteProduct(int id) {
        String query = "DELETE FROM products WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting product: " + e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver not found: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get products by seller ID
     * @param sellerId Seller ID
     * @return List of products sold by this seller
     */
    public List<Product> getProductsBySellerId(int sellerId) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT p.*, u.name as seller_name, u.phone as seller_phone, u.address as seller_address FROM products p LEFT JOIN users u ON p.seller_id = u.id WHERE p.seller_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, sellerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setSellerId(rs.getInt("seller_id"));
                product.setSellerName(rs.getString("seller_name"));
                product.setSellerPhone(rs.getString("seller_phone"));
                product.setSellerAddress(rs.getString("seller_address"));
                products.add(product);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving seller products: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver not found: " + e.getMessage());
        }
        
        return products;
    }
}
