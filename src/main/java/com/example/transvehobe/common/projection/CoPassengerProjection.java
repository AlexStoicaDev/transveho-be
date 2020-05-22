package com.example.transvehobe.common.projection;

import com.example.transvehobe.common.enums.PassengerType;
import com.example.transvehobe.entity.passenger.CoPassenger;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "coPassenger", types = {CoPassenger.class})
public interface CoPassengerProjection {

    Long getId();

    PassengerType getPassengerType();
}
