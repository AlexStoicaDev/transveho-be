package com.example.transvehobe.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateTransferStepperDataDto {

    private int totalNumberOfAdults;
    private int totalNumberOfChildren;
    private int totalNumberOfInfants;
    private List<CarDto> availableCars;
    private List<UserDto> availableDrivers;
    private List<PassengerDto> selectedPassengers;
    private RouteDto selectedRoute;
}
