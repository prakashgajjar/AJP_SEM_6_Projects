package com.hostelmart;

import com.hostelmart.dao.ProductDAO;
import com.hostelmart.model.Product;
import com.hostelmart.util.DBUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class HostelMartServer {
    private static final int PORT = 5000;
    private static final ProductDAO productDAO = new ProductDAO();

    public static void main(String[] args) {
        System.out.println("Initializing Database for Server...");
        DBUtil.initializeDatabase();
        System.out.println("Database Initialized!");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("HostelMart Server is running and listening on port " + PORT);

            while (true) {
                // Wait for a client to connect
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Handle the client in a separate method (or thread for multiple clients)
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            // Read the search string sent by the client
            String searchString = in.readLine();
            System.out.println("Received search request for: " + searchString);

            if (searchString != null && !searchString.trim().isEmpty()) {
                // Search database for the product
                List<Product> matchingProducts = productDAO.searchProducts(searchString.trim());

                // Prepare the response
                if (matchingProducts.isEmpty()) {
                    out.println("No products found matching: '" + searchString + "'");
                } else {
                    StringBuilder response = new StringBuilder();
                    response.append("Found ").append(matchingProducts.size()).append(" product(s):\n");
                    response.append("--------------------------------------------------\n");
                    for (Product p : matchingProducts) {
                        response.append(String.format("- %s (Category: %s) | Price: ₹%.2f | Seller: %s (Contact: %s)\n",
                                p.getName(), p.getCategory(), p.getPrice(), p.getSellerName(), p.getContactInfo()));
                    }
                    // Send response back to the client
                    out.println(response.toString());
                }
            } else {
                out.println("Invalid search string.");
            }
            
            // Send end marker
            out.println("END_OF_RESPONSE");

        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Client disconnected.");
        }
    }
}
