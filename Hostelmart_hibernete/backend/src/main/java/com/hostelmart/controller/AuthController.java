package com.hostelmart.controller;

import com.hostelmart.dao.UserDao;
import com.hostelmart.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"}, allowCredentials = "true")
public class AuthController {

    @Autowired
    private UserDao userDao;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> payload, HttpServletResponse response) {
        String email = payload.get("email");
        String password = payload.get("password");
        
        User user = userDao.findByEmailAndPassword(email, password);
        Map<String, Object> responseBody = new HashMap<>();
        if (user != null) {
            try {
                // Set cookies for user tracking (URL-encoded to avoid space issues)
                response.addCookie(createCookie("userId", String.valueOf(user.getId()), 86400));
                response.addCookie(createCookie("userName", URLEncoder.encode(user.getName(), "UTF-8"), 86400));
                response.addCookie(createCookie("userEmail", user.getEmail(), 86400));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            
            responseBody.put("success", true);
            responseBody.put("user", user);
            return ResponseEntity.ok(responseBody);
        } else {
            responseBody.put("success", false);
            responseBody.put("message", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }
    }
    
    private javax.servlet.http.Cookie createCookie(String name, String value, int maxAge) {
        javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        cookie.setSecure(false);
        return cookie;
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody User user) {
        int result = userDao.save(user);
        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("success", true);
            response.put("message", "Registration successful!");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Registration failed.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpServletResponse response) {
        // Clear cookies
        response.addCookie(createCookie("userId", "", 0));
        response.addCookie(createCookie("userName", "", 0));
        response.addCookie(createCookie("userEmail", "", 0));
        
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("message", "Logged out successfully");
        return ResponseEntity.ok(responseBody);
    }
}
