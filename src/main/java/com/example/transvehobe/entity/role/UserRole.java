package com.example.transvehobe.entity.role;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ADMIN, DISPATCHER, DRIVER;

    @Override
    public String getAuthority() {
        return name();
    }
}

