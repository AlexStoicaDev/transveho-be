package com.example.transvehobe.rest;

import com.example.transvehobe.common.dto.RouteDto;
import com.example.transvehobe.common.projection.CustomProjectionFactory;
import com.example.transvehobe.common.projection.RouteProjection;
import com.example.transvehobe.entity.route.Route;
import com.example.transvehobe.service.RoutesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transveho/routes")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoutesController {

    private final RoutesService routesService;
    private final CustomProjectionFactory factory;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority()")
    public RouteProjection getRouteById(@PathVariable(value = "id") Long id) {
        Route route =
            routesService.getRouteById(id).orElseThrow(() -> new EntityNotFoundException("Route with id: " + id + " was not found"));
        return factory.createProjection(RouteProjection.class, route);
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
    public List<RouteProjection> getAllRoutes() {
        return routesService.getAllRoutes()
                            .stream()
                            .map(route -> factory.createProjection(RouteProjection.class, route))
                            .collect(Collectors.toList());
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
    public RouteProjection createRoute(@RequestBody RouteDto routeDto) {
        return factory.createProjection(RouteProjection.class, routesService.createRoute(routeDto));
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
    public RouteProjection updateRoute(@RequestBody RouteDto routeDto) {
        Route updatedRoute = routesService.updateRoute(routeDto)
                                          .orElseThrow(() -> new EntityNotFoundException(
                                              "Route with id: " + routeDto.getId() + " was not found"));
        return factory.createProjection(RouteProjection.class, updatedRoute);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
    public void deleteRoute(@PathVariable(value = "id") Long id) {
        routesService.deleteRoute(id);
    }

}
