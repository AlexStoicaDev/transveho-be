package com.example.transvehobe.service;

import com.example.transvehobe.common.dto.CoPassengerDto;
import com.example.transvehobe.common.dto.PassengerDto;
import com.example.transvehobe.common.mappers.PassengerMapper;
import com.example.transvehobe.entity.passenger.CoPassenger;
import com.example.transvehobe.entity.passenger.CoPassengerRepository;
import com.example.transvehobe.entity.passenger.Passenger;
import com.example.transvehobe.entity.passenger.PassengerRepository;
import com.example.transvehobe.entity.route.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class PassengersService {

    private final CoPassengerRepository coPassengerRepository;
    private final PassengerRepository passengerRepository;
    private final RoutesService routesService;

    public Optional<Passenger> getPassengerById(Long passengerId) {
        return passengerRepository.findById(passengerId);
    }

    public List<CoPassenger> getCoPassengers(Long passengerId) {
        return coPassengerRepository.getAllByMainPassengerId(passengerId);
    }

    public List<Passenger> getAllPassengers() {
        return this.passengerRepository.findAll();
    }

    public CoPassenger createCoPassenger(Long mainPassengerId, CoPassengerDto coPassengerDto) {
        Passenger mainPassenger =
            passengerRepository.findById(mainPassengerId)
                               .orElseThrow(() -> new EntityNotFoundException("Passenger with id: " + mainPassengerId + " was not found"));
        CoPassenger newCoPassenger = PassengerMapper.mapCoPassengerDtoToCoPassengerEntity(coPassengerDto, mainPassenger);
        coPassengerRepository.save(newCoPassenger);
        return newCoPassenger;
    }

    public Passenger createPassenger(PassengerDto passengerDto) {
        Passenger newPassenger = PassengerMapper.mapPassengerDtoToPassengerEntity(passengerDto);
        Route passengerRoute = routesService.getRouteById(passengerDto.getRouteId())
                                            .orElseThrow(() -> new EntityNotFoundException(
                                                "Route with id: " + passengerDto.getRouteId() + " was not found in the db"));
        newPassenger.setRoute(passengerRoute);
        passengerRepository.save(newPassenger);
        return newPassenger;
    }

    public Optional<Passenger> updatePassenger(PassengerDto passengerDto) {
        Passenger passenger =
            passengerRepository.findById(passengerDto.getId())
                               .orElseThrow(() -> new EntityNotFoundException(
                                   "Passenger with id: " + passengerDto.getId() + " was not found"));
        passengerRepository.save(PassengerMapper.updatePassenger(passenger, passengerDto));
        return passengerRepository.findById(passengerDto.getId());
    }

    public void deleteCoPassenger(Long id) {
        CoPassenger coPassenger =
            coPassengerRepository.findById(id)
                                 .orElseThrow(() -> new EntityNotFoundException("Co passenger with id: " + id + " was not found"));
        coPassengerRepository.delete(coPassenger);
    }

    public void deletePassenger(Long id) {
        Passenger passenger =
            passengerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Passenger with id: " + id + " was not found"));
        passengerRepository.delete(passenger);
    }

    public void deleteAllCoPassengers(Long mainPassengerId) {
        Passenger mainPassenger =
            passengerRepository.findById(mainPassengerId)
                               .orElseThrow(() -> new EntityNotFoundException("Passenger with id: " + mainPassengerId + " was not found"));
        final List<CoPassenger> coPassengers = mainPassenger.getCoPassengers();
        mainPassenger.setCoPassengers(new ArrayList<>());
        coPassengers.forEach(coPassengerRepository::delete);
        passengerRepository.save(mainPassenger);
    }
}
