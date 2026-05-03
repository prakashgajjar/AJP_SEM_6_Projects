package com.hostelmart;

import com.hostelmart.dao.ProductDAO;
import com.hostelmart.model.Product;
import com.hostelmart.util.DBUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ProductDAO productDAO = new ProductDAO();

    public static void main(String[] args) {
        System.out.println("Initializing Database...");
        DBUtil.initializeDatabase();
        
        boolean running = true;
        
        while (running) {
            System.out.println("\n--- HostelMart Marketplace ---");
            System.out.println("1. Sell a Product (Add)");
            System.out.println("2. View All Products");
            System.out.println("3. Update a Product Listing");
            System.out.println("4. Remove a Product (Delete)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    addProduct();
                    break;
                case "2":
                    viewAllProducts();
                    break;
                case "3":
                    updateProduct();
                    break;
                case "4":
                    deleteProduct();
                    break;
                case "5":
                    running = false;
                    System.out.println("Exiting application...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void addProduct() {
        System.out.println("\n-- Sell a Product --");
        System.out.print("Enter product name (e.g. Engineering Mathematics Book): ");
        String name = scanner.nextLine();
        
        System.out.print("Enter category (Books, Electronics, Stationery, etc.): ");
        String category = scanner.nextLine();
        
        System.out.print("Enter price: ");
        BigDecimal price;
        try {
            price = new BigDecimal(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format. Product adding aborted.");
            return;
        }
        
        System.out.print("Enter your name (Seller): ");
        String sellerName = scanner.nextLine();
        
        System.out.print("Enter your contact info (Room No. / Phone): ");
        String contactInfo = scanner.nextLine();
        
        Product newProduct = new Product(name, category, price, sellerName, contactInfo);
        boolean success = productDAO.addProduct(newProduct);
        
        if (success) {
            System.out.println("Product added to marketplace successfully!");
        } else {
            System.out.println("Failed to add product.");
        }
    }

    private static void viewAllProducts() {
        System.out.println("\n-- Available Products --");
        List<Product> products = productDAO.getAllProducts();
        
        if (products.isEmpty()) {
            System.out.println("No products found in the marketplace.");
        } else {
            System.out.printf("%-5s | %-30s | %-15s | %-10s | %-20s | %-20s\n", 
                    "ID", "Product Name", "Category", "Price", "Seller", "Contact");
            System.out.println("---------------------------------------------------------------------------------------------------------------");
            for (Product p : products) {
                System.out.printf("%-5d | %-30s | %-15s | ₹%-9.2f | %-20s | %-20s\n", 
                        p.getId(), p.getName(), p.getCategory(), p.getPrice(), p.getSellerName(), p.getContactInfo());
            }
        }
    }

    private static void updateProduct() {
        System.out.println("\n-- Update Product Listing --");
        System.out.print("Enter product ID to update: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
            return;
        }
        
        Product existingProduct = productDAO.getProductById(id);
        if (existingProduct == null) {
            System.out.println("Product with ID " + id + " not found.");
            return;
        }
        
        System.out.print("Enter new name (leave blank to keep '" + existingProduct.getName() + "'): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) existingProduct.setName(name);
        
        System.out.print("Enter new category (leave blank to keep '" + existingProduct.getCategory() + "'): ");
        String category = scanner.nextLine();
        if (!category.isEmpty()) existingProduct.setCategory(category);
        
        System.out.print("Enter new price (leave blank to keep '₹" + existingProduct.getPrice() + "'): ");
        String priceStr = scanner.nextLine();
        if (!priceStr.isEmpty()) {
            try {
                existingProduct.setPrice(new BigDecimal(priceStr));
            } catch (NumberFormatException e) {
                System.out.println("Invalid price format. Skipping price update.");
            }
        }
        
        System.out.print("Enter new seller name (leave blank to keep '" + existingProduct.getSellerName() + "'): ");
        String sellerName = scanner.nextLine();
        if (!sellerName.isEmpty()) existingProduct.setSellerName(sellerName);
        
        System.out.print("Enter new contact info (leave blank to keep '" + existingProduct.getContactInfo() + "'): ");
        String contactInfo = scanner.nextLine();
        if (!contactInfo.isEmpty()) existingProduct.setContactInfo(contactInfo);
        
        boolean success = productDAO.updateProduct(existingProduct);
        if (success) {
            System.out.println("Product listing updated successfully!");
        } else {
            System.out.println("Failed to update product.");
        }
    }

    private static void deleteProduct() {
        System.out.println("\n-- Remove Product --");
        System.out.print("Enter product ID to delete: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
            return;
        }
        
        boolean success = productDAO.deleteProduct(id);
        if (success) {
            System.out.println("Product removed successfully!");
        } else {
            System.out.println("Failed to remove product. Check if the ID exists.");
        }
    }
}
