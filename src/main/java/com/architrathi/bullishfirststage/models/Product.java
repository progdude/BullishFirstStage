package com.architrathi.bullishfirststage.models;

import java.util.UUID;

public class Product {
    private UUID id;
    private String name;
    private ProductCategory productCategory;
    private double price;

    public Product(String id, String name, ProductCategory productCategory, double price){
        this.id = UUID.fromString(id);
        this.name = name;
        this.productCategory = productCategory;
        this.price = price;
    }

    public UUID getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public ProductCategory getProductCategory(){
        return this.productCategory;
    }

    public double getPrice(){
        return this.price;
    }

    public void setPrice(double price){
        this.price = price;
    }
}
