package com.architrathi.bullishfirststage.controllers;

import com.architrathi.bullishfirststage.clients.DatabaseClient;
import com.architrathi.bullishfirststage.models.Customer;
import com.architrathi.bullishfirststage.models.Product;
import com.architrathi.bullishfirststage.models.ProductCategory;
import com.architrathi.bullishfirststage.models.Role;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CustomerControllerTest {

    private DatabaseClient databaseClient = new DatabaseClient();
    private AnnotationConfigApplicationContext context = Mockito.mock(AnnotationConfigApplicationContext.class);

    @Test
    void createOrUpdateCustomer() {
        UUID uuid = UUID.randomUUID();
        Customer expected = new Customer(uuid.toString(), "username_1", "name_1", Role.ADMIN);
        when(this.context.getBean(DatabaseClient.class)).thenReturn(this.databaseClient);

        // Making sure we can create the product and retrieve it
        CustomerController c = new CustomerController();
        c.createOrUpdateCustomer(expected, Role.ADMIN);
        ResponseEntity responseEntity = c.getCustomer(uuid.toString(), Role.ADMIN);
        Customer actual = (Customer) responseEntity.getBody();
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void calculateBill() {
        // Create all the necessary classes
        UUID uuid = UUID.randomUUID();
        Customer expected = new Customer(uuid.toString(), "username_1", "name_1", Role.ADMIN);
        Product broom = new Product(UUID.randomUUID().toString(), "Broom", ProductCategory.CLEANING, 10.5);
        Product shirt = new Product(UUID.randomUUID().toString(), "Shirt", ProductCategory.CLOTHING, 5);
        when(this.context.getBean(DatabaseClient.class)).thenReturn(this.databaseClient);

        // Add items to the cart and calculate the cost
        CustomerController c = new CustomerController();
        c.createOrUpdateCustomer(expected, Role.ADMIN);
        c.addProductToCart(expected.getId().toString(), broom, Role.CUSTOMER);
        c.addProductToCart(expected.getId().toString(), shirt, Role.CUSTOMER);

        ResponseEntity responseEntity = c.getCustomer(uuid.toString(), Role.ADMIN);
        Customer actual = (Customer) responseEntity.getBody();
        assertEquals(15.5, actual.calculateBill());

        // Remove item from cart and confirm the bill
        c.removeProducFromCart(expected.getId().toString(), broom, Role.CUSTOMER);
        responseEntity = c.getCustomer(uuid.toString(), Role.ADMIN);
        actual = (Customer) responseEntity.getBody();
        assertEquals(5, actual.calculateBill());
    }
}