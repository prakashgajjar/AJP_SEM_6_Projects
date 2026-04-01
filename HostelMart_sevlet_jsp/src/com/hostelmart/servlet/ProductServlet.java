package com.hostelmart.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.hostelmart.dao.ProductDAO;
import com.hostelmart.dao.OrderDAO;
import com.hostelmart.dao.UserDAO;
import com.hostelmart.model.Product;
import com.hostelmart.model.Order;
import com.hostelmart.model.User;

/**
 * ProductServlet - Handles product CRUD operations and purchases
 * Maps to: /product
 */
@WebServlet("/product")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO = new ProductDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private UserDAO userDAO = new UserDAO();

    /**
     * Handle GET requests - display products or edit form
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Check if user is logged in
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            int userId = (Integer) session.getAttribute("userId");
            String action = request.getParameter("action");

            if (action == null || action.equals("view")) {
                // View all products
                List<Product> products = productDAO.getAllProducts();
                request.setAttribute("products", products);
                request.setAttribute("userId", userId);
                request.getRequestDispatcher("viewProducts.jsp").forward(request, response);

            } else if (action.equals("edit")) {
                // Show edit form for specific product
                int productId = Integer.parseInt(request.getParameter("id"));
                Product product = productDAO.getProductById(productId);
                
                // Authorization check - only seller can edit their own product
                if (product.getSellerId() != userId) {
                    request.setAttribute("error", "You can only edit your own products!");
                    List<Product> products = productDAO.getAllProducts();
                    request.setAttribute("products", products);
                    request.setAttribute("userId", userId);
                    request.getRequestDispatcher("viewProducts.jsp").forward(request, response);
                    return;
                }
                
                request.setAttribute("product", product);
                request.getRequestDispatcher("editProduct.jsp").forward(request, response);

            } else if (action.equals("delete")) {
                // Delete product
                int productId = Integer.parseInt(request.getParameter("id"));
                Product product = productDAO.getProductById(productId);
                
                // Authorization check - only seller can delete their own product
                if (product.getSellerId() != userId) {
                    request.setAttribute("error", "You can only delete your own products!");
                    List<Product> products = productDAO.getAllProducts();
                    request.setAttribute("products", products);
                    request.setAttribute("userId", userId);
                    request.getRequestDispatcher("viewProducts.jsp").forward(request, response);
                    return;
                }
                
                boolean isDeleted = productDAO.deleteProduct(productId);
                
                if (isDeleted) {
                    request.setAttribute("message", "Product deleted successfully!");
                } else {
                    request.setAttribute("error", "Failed to delete product!");
                }
                
                // Redirect to view products
                List<Product> products = productDAO.getAllProducts();
                request.setAttribute("products", products);
                request.setAttribute("userId", userId);
                request.getRequestDispatcher("viewProducts.jsp").forward(request, response);

            } else if (action.equals("viewSeller")) {
                // View seller details on click of buy
                int productId = Integer.parseInt(request.getParameter("id"));
                Product product = productDAO.getProductById(productId);
                User seller = userDAO.getUserById(product.getSellerId());
                request.setAttribute("product", product);
                request.setAttribute("seller", seller);
                request.setAttribute("userId", userId);
                request.getRequestDispatcher("buyProduct.jsp").forward(request, response);

            } else {
                response.sendRedirect("viewProducts.jsp");
            }

        } catch (NumberFormatException e) {
            System.err.println("Invalid product ID: " + e.getMessage());
            response.sendRedirect("viewProducts.jsp");
        } catch (Exception e) {
            System.err.println("Error in ProductServlet (GET): " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("dashboard.jsp");
        }
    }

    /**
     * Handle POST requests - add, update, or purchase products
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Check if user is logged in
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            int userId = (Integer) session.getAttribute("userId");
            String action = request.getParameter("action");

            if (action != null && action.equals("buy")) {
                // Handle product purchase
                int productId = Integer.parseInt(request.getParameter("productId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                
                Product product = productDAO.getProductById(productId);
                
                // Cannot buy from self
                if (product.getSellerId() == userId) {
                    request.setAttribute("error", "You cannot buy your own product!");
                    List<Product> products = productDAO.getAllProducts();
                    request.setAttribute("products", products);
                    request.setAttribute("userId", userId);
                    request.getRequestDispatcher("viewProducts.jsp").forward(request, response);
                    return;
                }
                
                // Check availability
                if (product.getQuantity() < quantity) {
                    request.setAttribute("error", "Not enough quantity available!");
                    List<Product> products = productDAO.getAllProducts();
                    request.setAttribute("products", products);
                    request.setAttribute("userId", userId);
                    request.getRequestDispatcher("viewProducts.jsp").forward(request, response);
                    return;
                }
                
                // Create order
                Order order = new Order(userId, product.getSellerId(), productId, product.getName(), quantity, product.getPrice() * quantity, "pending");
                boolean orderCreated = orderDAO.createOrder(order);
                
                if (orderCreated) {
                    // Update product quantity
                    product.setQuantity(product.getQuantity() - quantity);
                    productDAO.updateProduct(product);
                    
                    request.setAttribute("message", "Purchase successful!");
                    request.setAttribute("seller", userDAO.getUserById(product.getSellerId()));
                    request.setAttribute("product", product);
                } else {
                    request.setAttribute("error", "Purchase failed!");
                }
                
                List<Product> products = productDAO.getAllProducts();
                request.setAttribute("products", products);
                request.setAttribute("userId", userId);
                request.getRequestDispatcher("viewProducts.jsp").forward(request, response);
                return;
            }

            String name = request.getParameter("name");
            String priceStr = request.getParameter("price");
            String quantityStr = request.getParameter("quantity");

            // Validation
            String errorMessage = "";
            
            if (name == null || name.trim().isEmpty()) {
                errorMessage = "Product name is required!";
            } else if (priceStr == null || priceStr.trim().isEmpty()) {
                errorMessage = "Price is required!";
            } else if (quantityStr == null || quantityStr.trim().isEmpty()) {
                errorMessage = "Quantity is required!";
            }

            // Parse price and quantity
            double price = 0;
            int quantity = 0;
            
            if (errorMessage.isEmpty()) {
                try {
                    price = Double.parseDouble(priceStr);
                    quantity = Integer.parseInt(quantityStr);
                    
                    if (price <= 0) {
                        errorMessage = "Price must be greater than 0!";
                    } else if (quantity < 0) {
                        errorMessage = "Quantity cannot be negative!";
                    }
                } catch (NumberFormatException e) {
                    errorMessage = "Invalid price or quantity format!";
                }
            }

            if (!errorMessage.isEmpty()) {
                request.setAttribute("error", errorMessage);
                if (action != null && action.equals("update")) {
                    int productId = Integer.parseInt(request.getParameter("id"));
                    Product product = productDAO.getProductById(productId);
                    request.setAttribute("product", product);
                    request.getRequestDispatcher("editProduct.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("addProduct.jsp").forward(request, response);
                }
                return;
            }

            boolean success = false;

            if (action != null && action.equals("update")) {
                // Update existing product
                int productId = Integer.parseInt(request.getParameter("id"));
                Product product = productDAO.getProductById(productId);
                
                // Authorization check - only seller can update their own product
                if (product.getSellerId() != userId) {
                    request.setAttribute("error", "You can only update your own products!");
                    List<Product> products = productDAO.getAllProducts();
                    request.setAttribute("products", products);
                    request.setAttribute("userId", userId);
                    request.getRequestDispatcher("viewProducts.jsp").forward(request, response);
                    return;
                }
                
                product.setName(name);
                product.setPrice(price);
                product.setQuantity(quantity);
                success = productDAO.updateProduct(product);

                if (success) {
                    request.setAttribute("message", "Product updated successfully!");
                } else {
                    request.setAttribute("error", "Failed to update product!");
                }
            } else {
                // Add new product
                Product product = new Product();
                product.setName(name);
                product.setPrice(price);
                product.setQuantity(quantity);
                product.setSellerId(userId);
                success = productDAO.addProduct(product);

                if (success) {
                    request.setAttribute("message", "Product added successfully!");
                } else {
                    request.setAttribute("error", "Failed to add product!");
                }
            }

            // Redirect to view products
            List<Product> products = productDAO.getAllProducts();
            request.setAttribute("products", products);
            request.setAttribute("userId", userId);
            request.getRequestDispatcher("viewProducts.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            System.err.println("Invalid product ID: " + e.getMessage());
            response.sendRedirect("viewProducts.jsp");
        } catch (Exception e) {
            System.err.println("Error in ProductServlet (POST): " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An error occurred!");
            try {
                request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
