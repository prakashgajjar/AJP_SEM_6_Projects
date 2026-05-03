package com.hostelmart.model;

import java.math.BigDecimal;

public class Product {
    private int id;
    private String name;
    private String category; // e.g., "Books", "Electronics", "Stationery"
    private BigDecimal price;
    private String sellerName;
    private String contactInfo;

    public Product() {
    }

    public Product(String name, String category, BigDecimal price, String sellerName, String contactInfo) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.sellerName = sellerName;
        this.contactInfo = contactInfo;
    }

    public Product(int id, String name, String category, BigDecimal price, String sellerName, String contactInfo) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.sellerName = sellerName;
        this.contactInfo = contactInfo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getSellerName() { return sellerName; }
    public void setSellerName(String sellerName) { this.sellerName = sellerName; }

    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

    @Override
    public String toString() {
        return String.format("Product[ID: %d, Name: '%s', Category: '%s', Price: ₹%.2f, Seller: '%s', Contact: '%s']",
                id, name, category, price, sellerName, contactInfo);
    }
}
