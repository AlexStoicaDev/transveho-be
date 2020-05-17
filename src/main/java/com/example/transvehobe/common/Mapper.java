package com.example.transvehobe.common;

import com.example.transvehobe.common.dto.CarDto;
import com.example.transvehobe.common.dto.UserDto;
import com.example.transvehobe.common.enums.CarStatus;
import com.example.transvehobe.common.enums.EngineType;
import com.example.transvehobe.common.enums.Language;
import com.example.transvehobe.common.enums.UserStatus;
import com.example.transvehobe.entity.car.Car;
import com.example.transvehobe.entity.drivingLicense.DrivingLicenseCategory;
import com.example.transvehobe.entity.role.UserRole;
import com.example.transvehobe.entity.user.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Mapper {

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

    public Car updateCar(Car car, CarDto carDto) {
        if (carDto.getPlateNumber() != null && !carDto.getPlateNumber().equals("")) {
            car.setPlateNumber(carDto.getPlateNumber());
        }
        if (carDto.getModel() != null && !carDto.getModel().equals("")) {
            car.setModel(carDto.getModel());
        }
        if (carDto.getNumberOfSeats() != null) {
            car.setNumberOfSeats(carDto.getNumberOfSeats());
        }
        if (carDto.getChassisNumber() != null && !carDto.getChassisNumber().equals("")) {
            car.setChassisNumber(carDto.getChassisNumber());
        }
        if (carDto.getEngineType() != null && !carDto.getEngineType().equals("")) {
            car.setEngineType(EngineType.valueOf(carDto.getEngineType()));
        }
        car.setRented(carDto.isRented());
        car.setInTransit(carDto.isInTransit());
        if (carDto.getStatus() != null && !carDto.getStatus().equals("")) {
            car.setStatus(CarStatus.valueOf(carDto.getStatus()));
        }
        if (carDto.getItpExpirationDate() != null) {
            car.setItpExpirationDate(carDto.getItpExpirationDate());
        }
        if (carDto.getRovignetteExpirationDate() != null) {
            car.setRovignetteExpirationDate(carDto.getRovignetteExpirationDate());
        }
        if (carDto.getHuvignetteExpirationDate() != null) {
            car.setHuvignetteExpirationDate(carDto.getHuvignetteExpirationDate());
        }
        if (carDto.getRcaExpirationDate() != null) {
            car.setRcaExpirationDate(carDto.getRcaExpirationDate());
        }
        if (carDto.getOthers() != null && !carDto.getOthers().equals("")) {
            car.setOthers(carDto.getOthers());
        }
        return car;
    }
}
