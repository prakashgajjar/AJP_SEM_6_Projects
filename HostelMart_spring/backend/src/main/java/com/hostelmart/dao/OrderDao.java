package com.hostelmart.dao;

import com.hostelmart.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrderDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Order> rowMapper = new RowMapper<Order>() {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order o = new Order();
            o.setId(rs.getInt("id"));
            o.setBuyerId(rs.getInt("buyer_id"));
            o.setSellerId(rs.getInt("seller_id"));
            o.setProductId(rs.getInt("product_id"));
            o.setQuantity(rs.getInt("quantity"));
            o.setPrice(rs.getDouble("price"));
            o.setStatus(rs.getString("status"));
            o.setCreatedAt(rs.getTimestamp("created_at"));
            o.setUpdatedAt(rs.getTimestamp("updated_at"));

            try { o.setProductName(rs.getString("product_name")); } catch (SQLException e) {}
            try { o.setBuyerName(rs.getString("buyer_name")); } catch (SQLException e) {}
            try { o.setSellerName(rs.getString("seller_name")); } catch (SQLException e) {}
            
            return o;
        }
    };

    public int save(Order order) {
        String sql = "INSERT INTO orders (buyer_id, seller_id, product_id, quantity, price, status) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, order.getBuyerId(), order.getSellerId(), order.getProductId(), 
                                   order.getQuantity(), order.getPrice(), order.getStatus());
    }

    public List<Order> findByBuyerId(int buyerId) {
        String sql = "SELECT o.*, p.name as product_name, u.name as seller_name " +
                     "FROM orders o " +
                     "JOIN products p ON o.product_id = p.id " +
                     "JOIN users u ON o.seller_id = u.id " +
                     "WHERE o.buyer_id = ? ORDER BY o.created_at DESC";
        return jdbcTemplate.query(sql, new Object[]{buyerId}, rowMapper);
    }

    public List<Order> findBySellerId(int sellerId) {
        String sql = "SELECT o.*, p.name as product_name, u.name as buyer_name " +
                     "FROM orders o " +
                     "JOIN products p ON o.product_id = p.id " +
                     "JOIN users u ON o.buyer_id = u.id " +
                     "WHERE o.seller_id = ? ORDER BY o.created_at DESC";
        return jdbcTemplate.query(sql, new Object[]{sellerId}, rowMapper);
    }
}
