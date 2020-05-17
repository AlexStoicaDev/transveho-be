package com.example.transvehobe.rest;

import com.example.transvehobe.common.dto.CarDto;
import com.example.transvehobe.common.projection.CarProjection;
import com.example.transvehobe.common.projection.CustomProjectionFactory;
import com.example.transvehobe.entity.car.Car;
import com.example.transvehobe.service.CarsService;
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
@RequestMapping("/transveho/cars")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CarsController {

    private final CarsService carsService;
    private final CustomProjectionFactory factory;

    @GetMapping("/{plateNumber}")
    @PreAuthorize("hasAnyAuthority()")
    public CarProjection getCarByPlateNumber(@PathVariable(value = "plateNumber") String plateNumber) {
        Car car = carsService.getCarByPlateNumber(plateNumber)
                             .orElseThrow(() -> new EntityNotFoundException(
                                 "the car with the plate number: " + plateNumber + " was not found in the db!"));
        return factory.createProjection(CarProjection.class, car);
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
    public List<CarProjection> getAllCars() {
        return carsService.getAllCars()
                          .stream()
                          .map(car -> factory.createProjection(CarProjection.class, car))
                          .collect(Collectors.toList());
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
    public CarProjection createCar(@RequestBody CarDto carDto) {
        Car newCar = carsService.createCar(carDto).orElseThrow(() -> new EntityNotFoundException("car with plate number: "
                                                                                                     + carDto.getPlateNumber()
                                                                                                     + " was not found in db"));
        return factory.createProjection(CarProjection.class, newCar);
    }

    @PutMapping("/{plateNumber}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
    public CarProjection updateCar(@PathVariable("plateNumber") String plateNumber, @RequestBody CarDto carDto) {
        Car updatedCar = carsService.updateCar(plateNumber, carDto).orElseThrow(() -> new EntityNotFoundException("car with plate number: "
                                                                                                                      + plateNumber
                                                                                                                      + " was not found in db"));
        return factory.createProjection(CarProjection.class, updatedCar);
    }

    @DeleteMapping("/{plateNumber}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
    public void deleteCar(@PathVariable("plateNumber") String plateNumber) {
        carsService.deleteCar(plateNumber);
    }
}
