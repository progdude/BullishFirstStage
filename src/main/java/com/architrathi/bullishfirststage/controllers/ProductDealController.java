package com.architrathi.bullishfirststage.controllers;

import com.architrathi.bullishfirststage.clients.DatabaseClient;
import com.architrathi.bullishfirststage.config.AppConfig;
import com.architrathi.bullishfirststage.config.Config;
import com.architrathi.bullishfirststage.models.OperationAccess;
import com.architrathi.bullishfirststage.models.Role;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ProductDealController {

    private DatabaseClient databaseClient;

    public ProductDealController(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        this.databaseClient = context.getBean(DatabaseClient.class);
    }

    @GetMapping("/productDeal")
    public ResponseEntity createOrUpdateProductDeal(@RequestParam(value = "productDeal") String productDeal, @RequestParam(value = "productDealStatus") boolean productDealStatus, @RequestHeader("FakeAuth") Role role){
        if (!Config.getOperationAccessFromRoles(role).contains(OperationAccess.PRODUCT_DEAL_WRITE)){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        this.databaseClient.addOrUpdateProductDeal(productDeal, productDealStatus);
        return new ResponseEntity(HttpStatus.OK);
    }
}
