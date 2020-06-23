package com.example.transvehobe.common.projection;

import com.example.transvehobe.entity.transfer.Transfer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "transfer", types = {Transfer.class})
public interface CurrentTransfer {

    Long getId();

    @Value("#{target.passengers.get(0)}")
    PassengerProjection getPassenger();

    CarProjection getCar();

    @Value("#{target.driver.lastName +' '+target.driver.firstName }")
    String getDriverName();

    RouteProjection getRoute();
}
