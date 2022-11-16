package com.architrathi.bullishfirststage.clients;

import com.architrathi.bullishfirststage.models.Customer;
import com.architrathi.bullishfirststage.models.Product;
import com.architrathi.bullishfirststage.models.interfaces.User;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository
public class DatabaseClient {

    private HashMap<UUID, Product> productMapping;
    private HashMap<UUID, User> userMapping;
    private HashMap<String, Boolean> productDealMapping;

    public DatabaseClient(){
        this.productMapping = new HashMap<UUID, Product>();
        this.userMapping = new HashMap<UUID, User>();
        this.productDealMapping = new HashMap<String, Boolean>();
    }

    public void saveOrUpdateProduct(Product product) { this.productMapping.put(product.getId(), product); }

    public Product getProduct(UUID id) {
        if (!this.productMapping.containsKey(id)){
            return null;
        }

        return this.productMapping.get(id);
    }

    public void deleteProduct(UUID id){
        if (this.productMapping.containsKey(id)){
            this.productMapping.remove(id);
        }
    }

    public void saveOrUpdateUser(User user) {
        this.userMapping.put(user.getId(), user);
    }

    public User getUser(UUID id) {
        if (!this.userMapping.containsKey(id)){
            return null;
        }

        return this.userMapping.get(id);
    }

    public void addOrUpdateProductDeal(String productDeal, boolean status){
        this.productDealMapping.put(productDeal, status);
    }

    public List<String> getActiveProductDeals(){
        List<String> activeProductDeals = new ArrayList<String>();

        for(String productDeal : this.productDealMapping.keySet()) {
            if (this.productDealMapping.get(productDeal)) {
                activeProductDeals.add(productDeal);
            }
        }

        return activeProductDeals;
    }
}
