package com.example.transvehobe.common.projection;

import com.example.transvehobe.common.enums.PaymentMethod;
import com.example.transvehobe.common.enums.TransportType;
import com.example.transvehobe.entity.passenger.Passenger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

@Projection(name = "passenger", types = {Passenger.class})
public interface PassengerProjection {

    Long getId();

    @Value("#{target.coPassengers.size()}")
    int getNumberOfCoPassengers();

    @Value("#{target.coPassengers.?[passengerType == T(com.example.transvehobe.common.enums.PassengerType).Adult].size()}")
    int getNumberOfAdults();

    @Value("#{target.coPassengers.?[passengerType == T(com.example.transvehobe.common.enums.PassengerType).Child].size()}")
    int getNumberOfChildren();

    @Value("#{target.coPassengers.?[passengerType == T(com.example.transvehobe.common.enums.PassengerType).Infant].size()}")
    int getNumberOfInfants();

    String getEmail();

    String getLastName();

    String getFirstName();

    String getPhoneNumber();

    @Value("#{target.route?.id}")
    Long getRouteId();

    LocalDateTime getPickUpDateTime();

    LocalDateTime getDepartureDateTime();

    LocalDateTime getArrivalDateTime();

    String getArrivalAirportLocation();

    String getPickUpAddress();

    boolean getPaidForTransfer();

    PaymentMethod getPaymentMethod();

    TransportType getTransportType();

    String getNotes();
}
