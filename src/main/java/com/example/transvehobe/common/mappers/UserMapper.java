package com.example.transvehobe.common.mappers;

import com.example.transvehobe.common.dto.UserDto;
import com.example.transvehobe.common.enums.Language;
import com.example.transvehobe.common.enums.UserStatus;
import com.example.transvehobe.entity.drivingLicense.DrivingLicenseCategory;
import com.example.transvehobe.entity.role.UserRole;
import com.example.transvehobe.entity.user.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public User mapUserDtoToUserEntity(User user, UserDto userDto) {
        if (userDto.getSpokenLanguage() != null) {
            user.setSpokenLanguage(Language.valueOf(userDto.getSpokenLanguage()));
        }
        if (userDto.getDrivingLicenseCategory() != null) {
            user.setDrivingLicenseCategory(DrivingLicenseCategory.valueOf(userDto.getDrivingLicenseCategory()));
        }
        if (userDto.getUserStatus() != null) {
            user.setUserStatus(UserStatus.valueOf(userDto.getUserStatus()));
        }
        if (userDto.getPhoneNumber() != null && !userDto.getPhoneNumber().equals("")) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }
        if (userDto.getFirstName() != null && !userDto.getFirstName().equals("")) {
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null && !userDto.getLastName().equals("")) {
            user.setLastName(userDto.getLastName());
        }
        if (userDto.getRole() != null) {
            user.setRole(UserRole.valueOf(userDto.getRole()));
        }
        if (userDto.getEmail() != null && !userDto.getEmail().equals("")) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getUsername() != null && !userDto.getUsername().equals("")) {
            user.setUsername(userDto.getUsername());
        }
        if (userDto.getPassword() != null && !userDto.getPassword().equals("")) {
            user.setPassword(userDto.getPassword());
        }
        return user;
    }
}
