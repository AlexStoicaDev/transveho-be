package com.example.transvehobe.common.projection;

import com.example.transvehobe.entity.route.Route;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "route", types = {Route.class})
public interface RouteProjection {

    Long getId();

    @Value("#{target.returnRoute?.id}")
    Long getReturnRouteId();

    String getToLocation();

    String getFromLocation();

    Integer getDistanceInKm();

    Integer getPriceInRon();

    Integer getPriceInEur();

    String getNotes();
}
