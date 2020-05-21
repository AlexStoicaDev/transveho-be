package com.example.transvehobe.service;

import com.example.transvehobe.common.dto.CarDto;
import com.example.transvehobe.common.mappers.CarMapper;
import com.example.transvehobe.entity.car.Car;
import com.example.transvehobe.entity.car.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class CarsService {

    private final CarRepository carRepository;

    public Optional<Car> getCarByPlateNumber(String plateNumber) {
        return carRepository.findByPlateNumber(plateNumber);
    }

    public List<Car> getAllCars() {
        return this.carRepository.findAll();
    }

    public Optional<Car> createCar(CarDto carDto) {
        Car newCar = CarMapper.mapCarDtoToCarEntity(new Car(), carDto);
        carRepository.save(newCar);
        return carRepository.findByPlateNumber(newCar.getPlateNumber());
    }

    public Optional<Car> updateCar(String plateNumber, CarDto carDto) {
        Car oldCar = carRepository.findByPlateNumber(plateNumber).orElseThrow(() -> new EntityNotFoundException("car with plate number: "
                                                                                                                    + plateNumber
                                                                                                                    + " was not found in db"));
        Car updatedCar = CarMapper.mapCarDtoToCarEntity(oldCar, carDto);
        carRepository.save(updatedCar);
        return carRepository.findByPlateNumber(updatedCar.getPlateNumber());
    }

    public void deleteCar(String plateNumber) {
        Car carToBeDeleted =
            carRepository.findByPlateNumber(plateNumber).orElseThrow(() -> new EntityNotFoundException("car with plate number: "
                                                                                                           + plateNumber
                                                                                                           + " was not found in db"));
        carRepository.delete(carToBeDeleted);
    }
}
