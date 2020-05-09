package com.example.transvehobe.common;

import com.example.transvehobe.common.dto.UserDto;
import com.example.transvehobe.common.enums.Language;
import com.example.transvehobe.common.enums.UserStatus;
import com.example.transvehobe.entity.drivingLicense.DrivingLicenseCategory;
import com.example.transvehobe.entity.role.UserRole;
import com.example.transvehobe.entity.user.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Mapper {

    public User mapUserDtoToUserEntity(UserDto userDto) {
        User newUser = new User();
        newUser.setDrivingLicenseCategory(DrivingLicenseCategory.valueOf(userDto.getDrivingLicenseCategory()));
        newUser.setSpokenLanguage(Language.valueOf(userDto.getSpokenLanguage()));
        newUser.setLastName(userDto.getLastName());
        newUser.setFirstName(userDto.getFirstName());
        newUser.setPhoneNumber(userDto.getPhoneNumber());
        newUser.setUserStatus(UserStatus.Available);
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(userDto.getPassword());
        newUser.setEmail(userDto.getEmail());
        newUser.setRole(UserRole.valueOf(userDto.getRole()));
        return newUser;
    }

    public User updateUser(User user, UserDto userDto) {
        if (userDto.getSpokenLanguage() != null) {
            user.setSpokenLanguage(Language.valueOf(userDto.getSpokenLanguage()));
        }
        if (userDto.getDrivingLicenseCategory() != null) {
            user.setDrivingLicenseCategory(DrivingLicenseCategory.valueOf(userDto.getDrivingLicenseCategory()));
        }
        if (userDto.getUserStatus() != null) {
            user.setUserStatus(UserStatus.valueOf(userDto.getUserStatus()));
        }
        if (userDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }
        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }
        if (userDto.getRole() != null) {
            user.setRole(UserRole.valueOf(userDto.getRole()));
        }
        return user;
    }
}
