package com.example.transvehobe.common.projection;

import com.example.transvehobe.entity.transfer.Transfer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "transfer", types = {Transfer.class})
public interface TransferProjection {

    Long getId();

    @Value("#{target.passengers.get(0)}")
    PassengerProjection getPassenger();

    @Value("#{target.car.plateNumber}")
    String getCarPlateNumber();

    @Value("#{target.driver.username}")
    String getDriverName();

    @Value("#{target.route.fromLocation +'-'+target.route.toLocation}")
    String getRoute();
}
