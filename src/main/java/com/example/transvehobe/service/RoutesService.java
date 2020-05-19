package com.example.transvehobe.service;

import com.example.transvehobe.common.Mapper;
import com.example.transvehobe.common.dto.RouteDto;
import com.example.transvehobe.entity.route.Route;
import com.example.transvehobe.entity.route.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class RoutesService {

    private final RouteRepository routeRepository;

    public Optional<Route> getRouteById(Long routeId) {
        return routeRepository.findById(routeId);
    }

    public List<Route> getAllRoutes() {
        return this.routeRepository.findAll();
    }

    public Route createRoute(RouteDto routeDto) {
        Route newRoute = Mapper.updateRoute(new Route(), routeDto);
        newRoute.setReturnRoute(createReturnRoute(newRoute, routeDto));
        routeRepository.save(newRoute);
        return newRoute;
    }

    private Route createReturnRoute(Route route, RouteDto routeDto) {
        Route newReturnRoute = Mapper.updateRoute(new Route(), switchLocationsForReturnRoute(routeDto));
        newReturnRoute.setReturnRoute(route);
        return newReturnRoute;
    }

    private RouteDto switchLocationsForReturnRoute(RouteDto routeDto) {
        final String fromLocation = routeDto.getToLocation();
        routeDto.setToLocation(routeDto.getFromLocation());
        routeDto.setFromLocation(fromLocation);
        return routeDto;
    }

    public Optional<Route> updateRoute(RouteDto routeDto) {
        Route oldRoute = routeRepository.findById(routeDto.getId())
                                        .orElseThrow(() -> new EntityNotFoundException(
                                            "Route with id: " + routeDto.getId() + " was not found in db"));

        Route updatedRoute = Mapper.updateRoute(oldRoute, routeDto);
        Route updatedReturnRoute = updateReturnRoute(updatedRoute.getReturnRoute(), routeDto);
        updatedRoute.setReturnRoute(updatedReturnRoute);
        routeRepository.save(updatedRoute);

        return routeRepository.findById(updatedRoute.getId());
    }

    public Route updateReturnRoute(Route returnRoute, RouteDto routeDto) {
        return Mapper.updateRoute(returnRoute, switchLocationsForReturnRoute(routeDto));
    }

    public void deleteRoute(Long routeId) {
        Route routeToBeDeleted = routeRepository.findById(routeId)
                                                .orElseThrow(() -> new EntityNotFoundException(
                                                    "Route with id: " + routeId + " was not found"));
        routeRepository.delete(routeToBeDeleted);
    }
}
