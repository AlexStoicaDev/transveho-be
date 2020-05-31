package com.example.transvehobe.common.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CarDto {

    private long id;
    private String plateNumber;
    private String model;
    private Integer numberOfSeats;
    private String chassisNumber;
    private String engineType;
    private boolean isRented = false;
    private boolean isInTransit = false;
    private String status;
    private LocalDate itpExpirationDate;
    private LocalDate rovignetteExpirationDate;
    private LocalDate huvignetteExpirationDate;
    private LocalDate rcaExpirationDate;
    private String others;
}
