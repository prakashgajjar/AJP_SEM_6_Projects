package com.hostelmart.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    // Database configuration
    // Using createDatabaseIfNotExist to automatically create the 'hostelmart_db' database
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hostelmart_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "prakash";
    private static final String PASS = "prakash"; // Update if your root user has a password

    // Get a database connection
    public static Connection getConnection() throws SQLException {
        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL JDBC Driver not found.");
        }
        
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    // Initialize database table
    public static void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS products ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(150) NOT NULL, "
                + "category VARCHAR(50) NOT NULL, "
                + "price DECIMAL(10, 2) NOT NULL, "
                + "seller_name VARCHAR(100) NOT NULL, "
                + "contact_info VARCHAR(100) NOT NULL"
                + ")";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Execute table creation
            stmt.execute(createTableSQL);
            System.out.println("Database table 'products' ensured/created successfully.");
            
        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
