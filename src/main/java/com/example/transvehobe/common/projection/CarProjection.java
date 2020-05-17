package com.example.transvehobe.common.projection;

import com.example.transvehobe.common.enums.CarStatus;
import com.example.transvehobe.common.enums.EngineType;
import com.example.transvehobe.entity.car.Car;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;

@Projection(name = "car", types = {Car.class})
public interface CarProjection {

    Long getId();

    String getPlateNumber();

    String getModel();

    Integer getNumberOfSeats();

    String getChassisNumber();

    EngineType getEngineType();

    boolean isRented();

    boolean isInTransit();

    CarStatus getStatus();

    LocalDate getItpExpirationDate();

    LocalDate getRovignetteExpirationDate();

    LocalDate getHuvignetteExpirationDate();

    LocalDate getRcaExpirationDate();

    String getOthers();
}
