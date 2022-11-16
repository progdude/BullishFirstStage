package com.architrathi.bullishfirststage.models;

import com.architrathi.bullishfirststage.models.interfaces.User;
import com.architrathi.bullishfirststage.utils.ProductDeals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Customer implements User {

    private UUID id;
    private String username;
    private String name;
    private Role role;
    private List<Product> products;

    public Customer(String id, String username, String name, Role role){
        this.id = UUID.fromString(id);
        this.username = username;
        this.name = name;
        this.role = role;
        this.products = new ArrayList<Product>();
    }

    public UUID getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getName() {
        return this.name;
    }

    public Role getRole() {
        return this.role;
    }

    public void addProduct(Product product){
        this.products.add(product);
    }

    public void removeProduct(Product product){
        this.products.removeIf(p -> p.getId().equals(product.getId()));
    }

    public double calculateBill(){
        ProductDeals.productDeals.applyDeals(this.products);
        return this.products.stream().mapToDouble(Product::getPrice).sum();
    }
}
