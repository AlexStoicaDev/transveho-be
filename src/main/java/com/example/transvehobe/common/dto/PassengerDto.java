package com.example.transvehobe.common.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PassengerDto {

    private Long id;
    private List<CoPassengerDto> coPassengers;
    private String email;
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private Long routeId;
    private LocalDateTime pickUpDateTime;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private String arrivalAirportLocation;
    private String pickUpAddress;
    private boolean paidForTransfer;
    private String paymentMethod;
    private String transportType;
    private String notes;
}
