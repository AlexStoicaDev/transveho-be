package com.example.transvehobe.service;

import com.example.transvehobe.common.dto.CreateTransferDataDto;
import com.example.transvehobe.common.dto.CreateTransferStepperDataDto;
import com.example.transvehobe.common.enums.PassengerStatus;
import com.example.transvehobe.common.mappers.CarMapper;
import com.example.transvehobe.common.mappers.PassengerMapper;
import com.example.transvehobe.common.mappers.RoutesMapper;
import com.example.transvehobe.common.mappers.UserMapper;
import com.example.transvehobe.entity.car.Car;
import com.example.transvehobe.entity.car.CarRepository;
import com.example.transvehobe.entity.passenger.Passenger;
import com.example.transvehobe.entity.passenger.PassengerRepository;
import com.example.transvehobe.entity.route.Route;
import com.example.transvehobe.entity.route.RouteRepository;
import com.example.transvehobe.entity.transfer.Transfer;
import com.example.transvehobe.entity.transfer.TransferRepository;
import com.example.transvehobe.entity.user.User;
import com.example.transvehobe.entity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final RoutesService routesService;
    private final PassengersService passengersService;
    private final CarsService carsService;
    private final UsersService usersService;
    private final TransferRepository transferRepository;
    //TODO move save method to services
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;
    private final CarRepository carRepository;
    private final PassengerRepository passengerRepository;

    public CreateTransferStepperDataDto getCreateTransferStepperData(List<Long> selectedPassengersIds, long routeId) {

        final CreateTransferStepperDataDto createTransferStepperDataDto = new CreateTransferStepperDataDto();
        setSelectedPassengers(createTransferStepperDataDto, selectedPassengersIds);
        setNumberOfPassengers(createTransferStepperDataDto);
        setAvailableDrivers(createTransferStepperDataDto);
        setAvailableCars(createTransferStepperDataDto,
                         createTransferStepperDataDto.getTotalNumberOfAdults() + createTransferStepperDataDto.getTotalNumberOfChildren()
                             + createTransferStepperDataDto.getTotalNumberOfInfants());
        setSelectedRoute(createTransferStepperDataDto, routeId);
        return createTransferStepperDataDto;
    }

    private void setSelectedPassengers(CreateTransferStepperDataDto createTransferStepperDataDto, List<Long> selectedPassengersIds) {
        createTransferStepperDataDto
            .setSelectedPassengers(selectedPassengersIds.stream()
                                                        .map(selectedPassengerId ->
                                                                 PassengerMapper.mapPassengerEntityToPassengerDto(
                                                                     this.passengersService.getPassengerById(selectedPassengerId)
                                                                                           .orElseThrow(() -> new EntityNotFoundException(
                                                                                               "Passenger with id: " + selectedPassengerId
                                                                                                   + " was not found in db"))))
                                                        .collect(Collectors.toList()));
    }

    private void setNumberOfPassengers(CreateTransferStepperDataDto createTransferStepperDataDto) {
        AtomicInteger totalNumberOfAdults = new AtomicInteger();
        AtomicInteger totalNumberOfChildren = new AtomicInteger();
        AtomicInteger totalNumberOfInfants = new AtomicInteger();
        createTransferStepperDataDto.getSelectedPassengers().forEach(passengerDto -> {
            totalNumberOfAdults.addAndGet(passengerDto.getNumberOfAdults());
            totalNumberOfChildren.addAndGet(passengerDto.getNumberOfChildren());
            totalNumberOfInfants.addAndGet(passengerDto.getNumberOfInfants());
        });
        createTransferStepperDataDto.setTotalNumberOfAdults(totalNumberOfAdults.get());
        createTransferStepperDataDto.setTotalNumberOfChildren(totalNumberOfChildren.get());
        createTransferStepperDataDto.setTotalNumberOfInfants(totalNumberOfInfants.get());
    }

    //TODO should be moved to a mapper
    private void setAvailableDrivers(CreateTransferStepperDataDto createTransferStepperDataDto) {
        createTransferStepperDataDto.setAvailableDrivers(this.usersService.getAllAvailableDrivers()
                                                                          .stream()
                                                                          .map(UserMapper::mapUserEntityToUserDot)
                                                                          .collect(Collectors.toList()));
    }

    private void setAvailableCars(CreateTransferStepperDataDto createTransferStepperDataDto, Integer numberOfPassengers) {
        createTransferStepperDataDto.setAvailableCars(this.carsService.getAvailableCarsWithEnoughSeats(
            numberOfPassengers)
                                                                      .stream()
                                                                      .map(CarMapper::mapCarEntityToCarDto)
                                                                      .collect(Collectors.toList()));
    }

    private void setSelectedRoute(CreateTransferStepperDataDto createTransferStepperDataDto, long routeId) {
        createTransferStepperDataDto.setSelectedRoute(RoutesMapper.mapRouteEntityToRouteDto(this.routesService.getRouteById(routeId)
                                                                                                              .orElseThrow(
                                                                                                                  () -> new EntityNotFoundException(
                                                                                                                      "Route with id: "
                                                                                                                          + routeId
                                                                                                                          + " was not found in db")
                                                                                                              )));
    }

    public Transfer createTransfer(CreateTransferDataDto createTransferDataDto) {
        Transfer newTransfer = new Transfer();
        transferRepository.save(newTransfer);
        setSelectedDriver(newTransfer, createTransferDataDto.getSelectedDriverId());
        setSelectedRoute(newTransfer, createTransferDataDto.getSelectedRouteId());
        setSelectedCar(newTransfer, createTransferDataDto.getSelectedCarId());
        setSelectedPassengers(newTransfer, createTransferDataDto.getSelectedPassengersIds());
        transferRepository.save(newTransfer);
        return newTransfer;
    }

    private void setSelectedDriver(Transfer newTransfer, long selectedDriverId) {
        final User selectedDriver = usersService.getUserById(selectedDriverId).orElseThrow(() -> new EntityNotFoundException(
            "driver with id: " + selectedDriverId + " was not found in db"));
        final List<Transfer> transfers = selectedDriver.getTransfers();
        transfers.add(newTransfer);
        selectedDriver.setTransfers(transfers);
        newTransfer.setDriver(selectedDriver);
        userRepository.save(selectedDriver);
    }

    private void setSelectedRoute(Transfer newTransfer, long selectedRouteId) {
        final Route selectedRoute =
            routesService.getRouteById(selectedRouteId).orElseThrow(() -> new EntityNotFoundException(
                "route with id: " + selectedRouteId + " was not found in db"));
        final List<Transfer> transfers = selectedRoute.getTransfers();
        transfers.add(newTransfer);
        selectedRoute.setTransfers(transfers);
        newTransfer.setRoute(selectedRoute);
        routeRepository.save(selectedRoute);
    }

    private void setSelectedCar(Transfer newTransfer, long selectedCarId) {
        final Car selectedCar =
            carsService.getCarById(selectedCarId).orElseThrow(() -> new EntityNotFoundException(
                "car with id: " + selectedCarId + " was not found in db"));
        final List<Transfer> transfers = selectedCar.getTransfers();
        transfers.add(newTransfer);
        selectedCar.setTransfers(transfers);
        newTransfer.setCar(selectedCar);
        carRepository.save(selectedCar);
    }

    private void setSelectedPassengers(Transfer newTransfer, List<Long> selectedPassengersIds) {
        List<Passenger> passengers = new ArrayList<>();
        selectedPassengersIds.forEach(passengerId -> {
            Passenger passenger = passengersService.getPassengerById(passengerId).orElseThrow(() -> new EntityNotFoundException(
                "passenger with id: " + passengerId + " was not found in db"));
            passenger.setTransfer(newTransfer);
            passenger.setStatus(PassengerStatus.Assigned);
            passengerRepository.save(passenger);
            passengers.add(passenger);
        });
        newTransfer.setPassengers(passengers);
    }

    //TODO convert properties file to yml ant hide important properties
}
