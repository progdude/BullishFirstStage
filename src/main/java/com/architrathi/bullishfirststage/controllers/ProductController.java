package com.architrathi.bullishfirststage.controllers;

import com.architrathi.bullishfirststage.clients.DatabaseClient;
import com.architrathi.bullishfirststage.config.AppConfig;
import com.architrathi.bullishfirststage.config.Config;
import com.architrathi.bullishfirststage.models.OperationAccess;
import com.architrathi.bullishfirststage.models.Product;
import com.architrathi.bullishfirststage.models.Role;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ProductController {

    private DatabaseClient databaseClient;

    public ProductController(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        this.databaseClient = context.getBean(DatabaseClient.class);
    }

    @PostMapping("/product")
    public ResponseEntity createOrUpdateProduct(@RequestBody Product newProduct, @RequestHeader("FakeAuth") Role role){
        if (!Config.getOperationAccessFromRoles(role).contains(OperationAccess.PRODUCT_WRITE)){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        this.databaseClient.saveOrUpdateProduct(newProduct);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/product/delete")
    public ResponseEntity deleteProduct(@RequestParam(value = "id") String id, @RequestHeader("FakeAuth") Role role){
        if (!Config.getOperationAccessFromRoles(role).contains(OperationAccess.PRODUCT_WRITE)){
            return  new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        this.databaseClient.deleteProduct(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/product")
    public ResponseEntity<Product> getProduct(@RequestParam(value = "id") String id, @RequestHeader("FakeAuth") Role role){
        if (!Config.getOperationAccessFromRoles(role).contains(OperationAccess.PRODUCT_READ)){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        Product product = this.databaseClient.getProduct(UUID.fromString(id));
        if (product == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Product>(this.databaseClient.getProduct(UUID.fromString(id)), HttpStatus.OK);
    }

}
