package com.example.transvehobe.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateTransferDataDto {

    private long selectedCarId;
    private long selectedDriverId;
    private long selectedRouteId;
    private List<Long> selectedPassengersIds;
}
