package com.example.transvehobe.common.dto;

import lombok.Data;

@Data
public class RouteDto {

    private Long id;
    private Long returnRouteId;
    private String toLocation;
    private String fromLocation;
    private Integer distanceInKm;
    private Integer priceInRon;
    private Integer priceInEur;
    private boolean transitRoute;
    private String notes;
}
