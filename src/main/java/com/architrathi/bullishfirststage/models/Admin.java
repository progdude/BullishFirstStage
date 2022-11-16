package com.architrathi.bullishfirststage.models;

import com.architrathi.bullishfirststage.models.interfaces.User;
import java.util.UUID;

public class Admin implements User {

    private UUID id;
    private String username;
    private String name;
    private Role role;

    public Admin(String id, String username, String name, Role role){
        this.id = UUID.fromString(id);
        this.username = username;
        this.name = name;
        this.role = role;
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
}
