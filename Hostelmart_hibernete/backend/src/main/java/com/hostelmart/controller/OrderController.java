package com.hostelmart.controller;

import com.hostelmart.dao.OrderDao;
import com.hostelmart.dao.ProductDao;
import com.hostelmart.model.Order;
import com.hostelmart.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class OrderController {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @PostMapping("/purchase")
    @Transactional
    public ResponseEntity<Map<String, Object>> purchase(@RequestBody Order order) {
        Map<String, Object> response = new HashMap<>();
        
        Product p = productDao.findById(order.getProductId());
        if (p == null || p.getQuantity() < order.getQuantity()) {
            response.put("success", false);
            response.put("message", "Product unavailable or insufficient quantity");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Apply reduction
        int reduced = productDao.reduceQuantity(order.getProductId(), order.getQuantity());
        if (reduced == 0) {
            response.put("success", false);
            response.put("message", "Failed to allocate quantity");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        // Set total price and defaults
        order.setPrice(p.getPrice() * order.getQuantity());
        order.setSellerId(p.getSellerId());
        order.setStatus("completed");

        int inserted = orderDao.save(order);
        if (inserted > 0) {
            response.put("success", true);
            response.put("message", "Purchase successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Failed to save order");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/buyer/{buyerId}")
    public List<Order> getBuyerOrders(@PathVariable int buyerId) {
        return orderDao.findByBuyerId(buyerId);
    }

    @GetMapping("/seller/{sellerId}")
    public List<Order> getSellerOrders(@PathVariable int sellerId) {
        return orderDao.findBySellerId(sellerId);
    }
}
