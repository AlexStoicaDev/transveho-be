package com.example.transvehobe.entity.role;

import org.springframework.security.core.GrantedAuthority;

//TODO change to class so a use can have multiple roles
public enum UserRole implements GrantedAuthority {
    ADMIN, DISPATCHER, DRIVER;

    @Override
    public String getAuthority() {
        return name();
    }
}

