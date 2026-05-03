package com.hostelmart.dao;

import com.hostelmart.model.Product;
import com.hostelmart.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // Create (Insert)
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (name, category, price, seller_name, contact_info) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getCategory());
            pstmt.setBigDecimal(3, product.getPrice());
            pstmt.setString(4, product.getSellerName());
            pstmt.setString(5, product.getContactInfo());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding product: " + e.getMessage());
            return false;
        }
    }

    // Read (Select All)
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setSellerName(rs.getString("seller_name"));
                product.setContactInfo(rs.getString("contact_info"));
                products.add(product);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching products: " + e.getMessage());
        }
        
        return products;
    }

    // Read (Select by ID)
    public Product getProductById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        Product product = null;
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setCategory(rs.getString("category"));
                    product.setPrice(rs.getBigDecimal("price"));
                    product.setSellerName(rs.getString("seller_name"));
                    product.setContactInfo(rs.getString("contact_info"));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching product by ID: " + e.getMessage());
        }
        
        return product;
    }

    // Update
    public boolean updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, category = ?, price = ?, seller_name = ?, contact_info = ? WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getCategory());
            pstmt.setBigDecimal(3, product.getPrice());
            pstmt.setString(4, product.getSellerName());
            pstmt.setString(5, product.getContactInfo());
            pstmt.setInt(6, product.getId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating product: " + e.getMessage());
            return false;
        }
    }

    // Delete
    public boolean deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting product: " + e.getMessage());
            return false;
        }
    }
}
