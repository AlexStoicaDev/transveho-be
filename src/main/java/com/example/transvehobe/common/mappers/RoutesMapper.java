package com.example.transvehobe.common.mappers;

import com.example.transvehobe.common.dto.RouteDto;
import com.example.transvehobe.entity.route.Route;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoutesMapper {

    public Route mapRouteDtoToRouteEntity(Route route, RouteDto routeDto) {

        if (routeDto.getToLocation() != null && !routeDto.getToLocation().equals("")) {
            route.setToLocation(routeDto.getToLocation());
        }
        if (routeDto.getFromLocation() != null && !routeDto.getFromLocation().equals("")) {
            route.setFromLocation(routeDto.getFromLocation());
        }
        if (routeDto.getDistanceInKm() != null && routeDto.getDistanceInKm() > 0) {
            route.setDistanceInKm(routeDto.getDistanceInKm());
        }
        if (routeDto.getPriceInRon() != null && routeDto.getPriceInRon() > 0) {
            route.setPriceInRon(routeDto.getPriceInRon());
        }
        if (routeDto.getPriceInEur() != null && routeDto.getPriceInEur() > 0) {
            route.setPriceInEur(routeDto.getPriceInEur());
        }
        if (routeDto.getNotes() != null && !routeDto.getNotes().equals("")) {
            route.setNotes(routeDto.getNotes());
        }
        route.setTransitRoute(routeDto.isTransitRoute());
        return route;
    }
}
