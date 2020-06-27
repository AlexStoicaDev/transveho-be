package com.example.transvehobe.service;

import com.example.transvehobe.common.dto.CreateTransferDataDto;
import com.example.transvehobe.common.dto.CreateTransferStepperDataDto;
import com.example.transvehobe.common.enums.CarStatus;
import com.example.transvehobe.common.enums.PassengerStatus;
import com.example.transvehobe.common.enums.UserStatus;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
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

    public List<Transfer> getAllTransfers()
    {
        final List<Transfer> all = transferRepository.findAll();
        if(all.size()>0){
            all.sort((a,b)->b.getPassengers().get(0).getStatus().compareTo(a.getPassengers().get(0).getStatus()));
            return all;
        }
        return new ArrayList<>();
    }

    public Transfer getCurrentTransferForDriver(Long driverId) {
        final List<Transfer> allTransfersForDriver = getAllTransfersForDriver(driverId);
        final LocalDateTime now = LocalDateTime.now();

        AtomicReference<LocalDateTime> transferDate = new AtomicReference<>();
        AtomicReference<Transfer> currentTransfer = new AtomicReference<>();
        allTransfersForDriver.forEach(transfer -> {
            final Passenger passenger = transfer.getPassengers().get(0);
            final LocalDateTime pickUpDateTime = passenger.getPickUpDateTime();
            if (transferDate.get() != null && pickUpDateTime.getDayOfYear() >= now.getDayOfYear() && (
                passenger.getStatus().equals(PassengerStatus.Assigned) || passenger.getStatus().equals(PassengerStatus.OnRoute))) {
                transferDate.set(pickUpDateTime);
                currentTransfer.set(transfer);
            } else {
                if (pickUpDateTime.getDayOfYear() >= now.getDayOfYear() && (
                    passenger.getStatus().equals(PassengerStatus.Assigned) || passenger.getStatus().equals(PassengerStatus.OnRoute))) {
                    transferDate.set(pickUpDateTime);
                    currentTransfer.set(transfer);
                }
            }
        });

        return currentTransfer.get();
    }

    public List<Transfer> getAllTransfersForDriver(Long driverId) {
        User driver = userRepository.findById(driverId).orElseThrow(() -> new EntityNotFoundException("entity was not found"));
        final List<Transfer> transfersByDriver = transferRepository.getTransfersByDriver(driver);
        return transfersByDriver;
    }

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

    public void startTransfer(Long passengerId, Long carId, Long driverId) {
        final Optional<Passenger> passengerById = passengerRepository.findById(passengerId);
        if (passengerById.isPresent()) {
            final Passenger passenger = passengerById.get();
            passenger.setStatus(PassengerStatus.OnRoute);
            passengerRepository.save(passenger);
        }

        final Optional<Car> carById = carRepository.findById(carId);
        if (carById.isPresent()) {
            final Car car = carById.get();
            car.setStatus(CarStatus.OnRoute);
            carRepository.save(car);
        }

        final Optional<User> driverById = userRepository.findById(driverId);
        if (driverById.isPresent()) {
            final User user = driverById.get();
            user.setUserStatus(UserStatus.OnRoute);
            userRepository.save(user);
        }
    }

    public void finishTransfer(Long passengerId, Long carId, Long driverId) {
        final Optional<Passenger> passengerById = passengerRepository.findById(passengerId);
        if (passengerById.isPresent()) {
            final Passenger passenger = passengerById.get();
            passenger.setStatus(PassengerStatus.TransferDone);
            passengerRepository.save(passenger);
        }

        final Optional<Car> carById = carRepository.findById(carId);
        if (carById.isPresent()) {
            final Car car = carById.get();
            car.setStatus(CarStatus.Available);
            carRepository.save(car);
        }

        final Optional<User> driverById = userRepository.findById(driverId);
        if (driverById.isPresent()) {
            final User user = driverById.get();
            user.setUserStatus(UserStatus.Available);
            userRepository.save(user);
        }
    }

    //TODO convert properties file to yml ant hide important properties
}
