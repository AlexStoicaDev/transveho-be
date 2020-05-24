package com.example.transvehobe.common.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PassengerDto {

    private Long id;
    private int numberOfAdults;
    private int numberOfChildren;
    private int numberOfInfants;
    private String email;
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private Long routeId;
    private String paymentMethod;
    private String transportType;
    private String pickUpAddress;
    private String destinationAddress;
    private LocalDateTime pickUpDateTime;
    private LocalDateTime flightDateTime;
    private String flightDetails;
    private String returnPickUpAddress;
    private String returnDestinationAddress;
    private LocalDateTime returnPickUpDateTime;
    private String returnFlightDetails;
    private boolean returnTransfer;
    private boolean paidForTransfer;
}
