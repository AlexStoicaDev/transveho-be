package com.example.transvehobe.common.dto;

import lombok.Data;

@Data
public class UserDto {

    private String username;
    private String email;
    private String password;
    private String role;
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private String userStatus;
    private String drivingLicenseCategory;
    private String spokenLanguage;
}
