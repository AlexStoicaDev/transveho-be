package com.example.transvehobe.entity.role;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ADMIN, MANAGER, TRAINER, DEVELOPER, OTHER;

    @Override
    public String getAuthority() {
        return name();
    }
}

