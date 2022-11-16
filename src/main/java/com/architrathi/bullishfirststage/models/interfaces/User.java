package com.architrathi.bullishfirststage.models.interfaces;

import com.architrathi.bullishfirststage.models.Role;
import java.util.UUID;

public interface User {
    public UUID getId();

    public String getUsername();

    public String getName();

    public Role getRole();
}
