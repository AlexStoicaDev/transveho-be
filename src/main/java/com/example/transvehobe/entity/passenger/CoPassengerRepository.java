package com.example.transvehobe.entity.passenger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoPassengerRepository extends JpaRepository<CoPassenger, Long> {

    List<CoPassenger> getAllByMainPassengerId(Long mainPassengerId);
}
