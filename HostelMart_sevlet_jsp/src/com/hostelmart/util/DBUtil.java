package com.hostelmart.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Utility Class
 * Handles database connection and configuration
 */
public class DBUtil {
    
    // Database Configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hostelmart?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "prakash";
    private static final String DB_PASSWORD = "prakash";  // Change if your MySQL has a password
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * Get database connection
     * @return Connection object
     * @throws SQLException if connection fails
     * @throws ClassNotFoundException if driver is not found
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        try {
            // Load MySQL driver
            Class.forName(DB_DRIVER);
            // Create and return connection
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Database connection successful!");
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            throw e;
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            throw e;
        }
    }

    /**
     * Close database connection
     * @param conn Connection to close
     */
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed!");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}
