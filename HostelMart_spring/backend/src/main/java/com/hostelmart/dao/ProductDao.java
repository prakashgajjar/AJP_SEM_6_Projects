package com.hostelmart.dao;

import com.hostelmart.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Product> rowMapper = new RowMapper<Product>() {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product p = new Product();
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setPrice(rs.getDouble("price"));
            p.setQuantity(rs.getInt("quantity"));
            p.setSellerId(rs.getInt("seller_id"));
            p.setCreatedAt(rs.getTimestamp("created_at"));
            p.setUpdatedAt(rs.getTimestamp("updated_at"));

            try {
                p.setSellerName(rs.getString("seller_name"));
            } catch (SQLException e) {
                // Ignore if not fetched
            }
            return p;
        }
    };

    public int save(Product product) {
        String sql = "INSERT INTO products (name, price, quantity, seller_id) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getQuantity(), product.getSellerId());
    }

    public int update(Product product) {
        String sql = "UPDATE products SET name=?, price=?, quantity=? WHERE id=? AND seller_id=?";
        return jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getQuantity(), product.getId(), product.getSellerId());
    }

    public int delete(int id, int sellerId) {
        String sql = "DELETE FROM products WHERE id=? AND seller_id=?";
        return jdbcTemplate.update(sql, id, sellerId);
    }

    public Product findById(int id) {
        String sql = "SELECT p.*, u.name as seller_name FROM products p LEFT JOIN users u ON p.seller_id = u.id WHERE p.id = ?";
        List<Product> list = jdbcTemplate.query(sql, new Object[]{id}, rowMapper);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Product> findAll() {
        String sql = "SELECT p.*, u.name as seller_name FROM products p LEFT JOIN users u ON p.seller_id = u.id ORDER BY p.created_at DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<Product> findBySellerId(int sellerId) {
        String sql = "SELECT p.*, u.name as seller_name FROM products p LEFT JOIN users u ON p.seller_id = u.id WHERE p.seller_id = ? ORDER BY p.created_at DESC";
        return jdbcTemplate.query(sql, new Object[]{sellerId}, rowMapper);
    }

    public int reduceQuantity(int id, int qty) {
        String sql = "UPDATE products SET quantity = quantity - ? WHERE id = ? AND quantity >= ?";
        return jdbcTemplate.update(sql, qty, id, qty);
    }
}
