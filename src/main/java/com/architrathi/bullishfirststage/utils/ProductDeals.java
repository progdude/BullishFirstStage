package com.architrathi.bullishfirststage.utils;

import com.architrathi.bullishfirststage.clients.DatabaseClient;
import com.architrathi.bullishfirststage.config.AppConfig;
import com.architrathi.bullishfirststage.config.Config;
import com.architrathi.bullishfirststage.models.Product;
import com.architrathi.bullishfirststage.models.interfaces.ProductDeal;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.List;

public class ProductDeals {

    private DatabaseClient databaseClient;
    private HashMap<String, ProductDeal> productDealMapping = new HashMap<>();

    public static ProductDeals productDeals = new ProductDeals();

    public ProductDeals(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        this.databaseClient = context.getBean(DatabaseClient.class);
        this.setupDealMapping();
    }

    public void applyDeals(List<Product> products){
        for (String productDeal : this.databaseClient.getActiveProductDeals()){
            if (this.productDealMapping.containsKey(productDeal)){
                this.productDealMapping.get(productDeal).run(products);
            }
        }
    }

    private void setupDealMapping() {
        productDealMapping.put("FreeTVDeal", products -> freeTVDeal(products));
    }

    private void freeTVDeal(List<Product> products) {
        for (Product product : products) {
            if (product.getName().equals("TV")) {
                product.setPrice(0);
            }
        }
    }
}
