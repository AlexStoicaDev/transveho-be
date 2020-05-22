package com.example.transvehobe.rest;

import com.example.transvehobe.common.dto.CoPassengerDto;
import com.example.transvehobe.common.dto.PassengerDto;
import com.example.transvehobe.common.projection.CoPassengerProjection;
import com.example.transvehobe.common.projection.CustomProjectionFactory;
import com.example.transvehobe.common.projection.PassengerProjection;
import com.example.transvehobe.entity.passenger.Passenger;
import com.example.transvehobe.service.PassengersService;
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
@RequestMapping("/transveho/passengers")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PassengersController {

    private final PassengersService passengersService;
    private final CustomProjectionFactory factory;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority()")
    public PassengerProjection getPassengerById(@PathVariable(value = "id") Long id) {
        Passenger passenger =
            passengersService.getPassengerById(id)
                             .orElseThrow(() -> new EntityNotFoundException("Passenger with id: " + id + " was not found"));
        return factory.createProjection(PassengerProjection.class, passenger);
    }

    @GetMapping("/co-passengers/{id}")
    @PreAuthorize("hasAnyAuthority()")
    public List<CoPassengerProjection> getCoPassengers(@PathVariable(value = "id") Long passengerId) {

        return passengersService.getCoPassengers(passengerId)
                                .stream()
                                .map(coPassenger -> factory.createProjection(CoPassengerProjection.class, coPassenger))
                                .collect(Collectors.toList());
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
    public List<PassengerProjection> getAllPassengers() {
        return passengersService.getAllPassengers()
                                .stream()
                                .map(passenger -> factory.createProjection(PassengerProjection.class, passenger))
                                .collect(Collectors.toList());
    }

    @PostMapping("/co-passengers/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
    public CoPassengerProjection createCoPassenger(@PathVariable(value = "id") Long mainPassengerId,
                                                   @RequestBody CoPassengerDto coPassengerDto) {
        return factory.createProjection(CoPassengerProjection.class, passengersService.createCoPassenger(mainPassengerId, coPassengerDto));
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
    public PassengerProjection createPassenger(@RequestBody PassengerDto passengerDto) {
        return factory.createProjection(PassengerProjection.class, passengersService.createPassenger(passengerDto));
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
    public PassengerProjection updatePassenger(@RequestBody PassengerDto passengerDto) {
        Passenger updatedPassenger = passengersService.updatePassenger(passengerDto)
                                                      .orElseThrow(() -> new EntityNotFoundException(
                                                          "Passenger with id: " + passengerDto.getId() + " was not found"));
        return factory.createProjection(PassengerProjection.class, updatedPassenger);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
    public void deletePassenger(@PathVariable(value = "id") Long id) {
        passengersService.deletePassenger(id);
    }

    @DeleteMapping("/co-passengers/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
    public void deleteCoPassenger(@PathVariable(value = "id") Long id) {
        passengersService.deleteCoPassenger(id);
    }

    @DeleteMapping("/co-passengers/all/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
    public void deleteAllCoPassengers(@PathVariable(value = "id") Long mainPassengerId) {
        passengersService.deleteAllCoPassengers(mainPassengerId);
    }
}
