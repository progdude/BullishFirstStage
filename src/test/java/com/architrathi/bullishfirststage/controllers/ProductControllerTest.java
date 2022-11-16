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

    @Mock
    DatabaseClient databaseClientMock;

//    @Mock
//    AnnotationConfigApplicationContext context;

    @Test
    void createOrUpdateProduct() {
//        when(databaseClientMock.getProduct(UUID.randomUUID())).thenReturn(new Product("id", "lol", ProductCategory.CLEANING, 0));
        AnnotationConfigApplicationContext context = Mockito.mock(AnnotationConfigApplicationContext.class);
        DatabaseClient db = new DatabaseClient();
        Product p = new Product("c7a02679-5825-464b-afc8-692a4347c0cc", "lol", ProductCategory.CLEANING, 0);
//        db.saveOrUpdateProduct(new Product("id", "lol", ProductCategory.CLEANING, 0));
        when(context.getBean(DatabaseClient.class)).thenReturn(db);

        ProductController c = new ProductController();
        c.createOrUpdateProduct(p, Role.ADMIN);
        ResponseEntity x = c.getProduct("c7a02679-5825-464b-afc8-692a4347c0cc", Role.ADMIN);
        assertEquals(((Product)x.getBody()).getName(), "lol");
    }

    @Test
    void getProduct() {
    }
}