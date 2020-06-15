package com.example.transvehobe.common.projection;

import com.example.transvehobe.common.enums.PassengerStatus;
import com.example.transvehobe.common.enums.PaymentMethod;
import com.example.transvehobe.common.enums.TransportType;
import com.example.transvehobe.entity.passenger.Passenger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

@Projection(name = "passenger", types = {Passenger.class})
public interface PassengerProjection {

    Long getId();

    @Value("#{target.numberOfAdults+target.numberOfChildren+target.numberOfInfants}")
    int getNumberOfCoPassengers();

    int getNumberOfAdults();

    int getNumberOfChildren();

    int getNumberOfInfants();

    String getEmail();

    String getLastName();

    String getFirstName();

    String getPhoneNumber();

    @Value("#{target.route?.id}")
    Long getRouteId();

    PaymentMethod getPaymentMethod();

    TransportType getTransportType();

    String getPickUpAddress();

    String getDestinationAddress();

    LocalDateTime getPickUpDateTime();

    LocalDateTime getFlightDateTime();

    String getFlightDetails();

    String getReturnPickUpAddress();

    String getReturnDestinationAddress();

    LocalDateTime getReturnPickUpDateTime();

    String getReturnFlightDetails();

    boolean getReturnTransfer();

    boolean getPaidForTransfer();

    PassengerStatus getStatus();
}
