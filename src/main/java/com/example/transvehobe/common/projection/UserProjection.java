package com.example.transvehobe.common.projection;

import com.example.transvehobe.common.enums.UserStatus;
import com.example.transvehobe.entity.role.UserRole;
import com.example.transvehobe.entity.user.User;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "user", types = {User.class})
public interface UserProjection {

    Long getId();

    String getUsername();

    String getEmail();

    UserRole getRole();

    String getDrivingLicenseCategory();

    String getSpokenLanguage();

    UserStatus getUserStatus();

    String getLastName();

    String getFirstName();

    String getPhoneNumber();

}
