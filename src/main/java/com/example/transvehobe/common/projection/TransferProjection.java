package com.example.transvehobe.common.projection;

import com.example.transvehobe.entity.transfer.Transfer;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "transfer", types = {Transfer.class})
public interface TransferProjection {

    Long getId();

    PassengerProjection getPassenger();


}
