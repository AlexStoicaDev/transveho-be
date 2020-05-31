package com.example.transvehobe.entity.car;

import com.example.transvehobe.common.enums.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findByPlateNumber(String plateNumber);

    List<Car> getAllByStatus(CarStatus carStatus);
}
