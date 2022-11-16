package com.architrathi.bullishfirststage.controllers;

import com.architrathi.bullishfirststage.clients.DatabaseClient;
import com.architrathi.bullishfirststage.config.AppConfig;
import com.architrathi.bullishfirststage.config.Config;
import com.architrathi.bullishfirststage.models.Customer;
import com.architrathi.bullishfirststage.models.OperationAccess;
import com.architrathi.bullishfirststage.models.Product;
import com.architrathi.bullishfirststage.models.Role;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class CustomerController {

    private DatabaseClient databaseClient;

    public CustomerController(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        this.databaseClient = context.getBean(DatabaseClient.class);
    }

    @PostMapping("/customer")
    public ResponseEntity createOrUpdateCustomer(@RequestBody Customer customer, @RequestHeader("FakeAuth") Role role){
        if (!Config.getOperationAccessFromRoles(role).contains(OperationAccess.CUSTOMER_WRITE)){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        this.databaseClient.saveOrUpdateUser(customer);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/customer")
    public ResponseEntity<Customer> getCustomer(@RequestParam(value = "id") String id, @RequestHeader("FakeAuth") Role role){
        if (!Config.getOperationAccessFromRoles(role).contains(OperationAccess.CUSTOMER_READ)){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        Customer customer = (Customer)this.databaseClient.getUser(UUID.fromString(id));
        if (customer == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }

    @PostMapping("/customer/add/product")
    public ResponseEntity addProductToCart(@RequestParam(value = "id") String customer_id,@RequestBody Product product, @RequestHeader("FakeAuth") Role role){
        if (!Config.getOperationAccessFromRoles(role).contains(OperationAccess.CUSTOMER_WRITE)){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        Customer customer = this.getCustomer(customer_id, role).getBody();
        customer.addProduct(product);
        this.databaseClient.saveOrUpdateUser(customer);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/customer/remove/product")
    public ResponseEntity removeProductToCart(@RequestParam(value = "id") String customer_id,@RequestBody Product product, @RequestHeader("FakeAuth") Role role){
        if (!Config.getOperationAccessFromRoles(role).contains(OperationAccess.CUSTOMER_WRITE)){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        Customer customer = this.getCustomer(customer_id, role).getBody();
        customer.removeProduct(product);
        this.databaseClient.saveOrUpdateUser(customer);
        return new ResponseEntity(HttpStatus.OK);
    }

}
