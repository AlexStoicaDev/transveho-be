package com.example.transvehobe.common.mappers;

import com.example.transvehobe.common.dto.CarDto;
import com.example.transvehobe.common.enums.CarStatus;
import com.example.transvehobe.common.enums.EngineType;
import com.example.transvehobe.entity.car.Car;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CarMapper {

    public Car mapCarDtoToCarEntity(Car car, CarDto carDto) {
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
