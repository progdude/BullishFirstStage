package com.architrathi.bullishfirststage.controllers;

import com.architrathi.bullishfirststage.clients.DatabaseClient;
import com.architrathi.bullishfirststage.models.Product;
import com.architrathi.bullishfirststage.models.ProductCategory;
import com.architrathi.bullishfirststage.models.Role;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.ResponseEntity;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    private DatabaseClient databaseClient = new DatabaseClient();
    private AnnotationConfigApplicationContext context = Mockito.mock(AnnotationConfigApplicationContext.class);

    @Test
    public void createOrUpdateProduct() {
        // Creating the product
        UUID uuid = UUID.randomUUID();
        Product actual = new Product(uuid.toString(), "Broom", ProductCategory.CLEANING, 10.5);
        when(this.context.getBean(DatabaseClient.class)).thenReturn(this.databaseClient);

        // Making sure we can create the product
        ProductController c = new ProductController();
        c.createOrUpdateProduct(actual, Role.ADMIN);
        ResponseEntity responseEntity = c.getProduct(uuid.toString(), Role.ADMIN);
        Product expected = (Product)responseEntity.getBody();
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getProductCategory(), actual.getProductCategory());
        assertEquals(expected.getPrice(), actual.getPrice());

        // Modify the product and update the db
        actual.setPrice(13);
        c.createOrUpdateProduct(actual, Role.ADMIN);
        responseEntity = c.getProduct(uuid.toString(), Role.ADMIN);
        expected = (Product)responseEntity.getBody();
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getProductCategory(), actual.getProductCategory());
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    @Test
    public void deleteProduct(){
        // Creating the product and adding it to the db
        UUID uuid = UUID.randomUUID();
        Product actual = new Product(uuid.toString(), "Broom", ProductCategory.CLEANING, 10.5);
        this.databaseClient.saveOrUpdateProduct(actual);
        when(this.context.getBean(DatabaseClient.class)).thenReturn(this.databaseClient);

        // Deleting the product
        ProductController c = new ProductController();
        c.deleteProduct(actual.getId().toString(), Role.ADMIN);

        // Checking it's actually deleted
        Product deleted = c.getProduct(actual.getId().toString(), Role.ADMIN).getBody();
        assertEquals(deleted, null);
    }

    @Test
    public void getProduct() {
        // Creating the product and adding it to the db
        UUID uuid = UUID.randomUUID();
        Product actual = new Product(uuid.toString(), "Broom", ProductCategory.CLEANING, 10.5);
        when(this.context.getBean(DatabaseClient.class)).thenReturn(this.databaseClient);

        // Making sure we can retrieve the product
        ProductController c = new ProductController();
        c.createOrUpdateProduct(actual, Role.ADMIN);
        ResponseEntity responseEntity = c.getProduct(uuid.toString(), Role.ADMIN);
        Product expected = (Product)responseEntity.getBody();
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getProductCategory(), actual.getProductCategory());
        assertEquals(expected.getPrice(), actual.getPrice());
    }
}