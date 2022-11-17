package com.architrathi.bullishfirststage.config;

import com.architrathi.bullishfirststage.models.OperationAccess;
import com.architrathi.bullishfirststage.models.Role;

import java.util.Arrays;
import java.util.List;

public class Config {

    public static Config config = new Config();

    /*
    Function that helps us determine which user has access to which API endpoints
     */
    public static List<OperationAccess> getOperationAccessFromRoles(Role role){
         if (role == Role.ADMIN){
            return Arrays.asList(OperationAccess.CUSTOMER_READ, OperationAccess.CUSTOMER_WRITE, OperationAccess.PRODUCT_READ, OperationAccess.PRODUCT_WRITE, OperationAccess.PRODUCT_DEAL_WRITE);
         }
         else if (role == Role.CUSTOMER){
             return Arrays.asList(OperationAccess.PRODUCT_READ, OperationAccess.CUSTOMER_READ, OperationAccess.CUSTOMER_WRITE);
         }
         else{
             return Arrays.asList();
         }
     }
}
