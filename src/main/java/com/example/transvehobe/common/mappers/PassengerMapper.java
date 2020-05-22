package com.example.transvehobe.common.mappers;

import com.example.transvehobe.common.dto.CoPassengerDto;
import com.example.transvehobe.common.dto.PassengerDto;
import com.example.transvehobe.common.enums.PassengerType;
import com.example.transvehobe.common.enums.PaymentMethod;
import com.example.transvehobe.common.enums.TransportType;
import com.example.transvehobe.common.validators.Validator;
import com.example.transvehobe.entity.passenger.CoPassenger;
import com.example.transvehobe.entity.passenger.Passenger;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class PassengerMapper {

    public Passenger mapPassengerDtoToPassengerEntity(PassengerDto passengerDto) {
        Passenger newPassenger = new Passenger();
        newPassenger.setEmail(passengerDto.getEmail());
        newPassenger.setLastName(passengerDto.getLastName());
        newPassenger.setFirstName(passengerDto.getFirstName());
        newPassenger.setPhoneNumber(passengerDto.getPhoneNumber());
        newPassenger.setPickUpDateTime(passengerDto.getPickUpDateTime());
        newPassenger.setDepartureDateTime(passengerDto.getDepartureDateTime());
        newPassenger.setArrivalDateTime(passengerDto.getArrivalDateTime());
        newPassenger.setArrivalAirportLocation(passengerDto.getArrivalAirportLocation());
        newPassenger.setPickUpAddress(passengerDto.getPickUpAddress());
        newPassenger.setPaidForTransfer(passengerDto.isPaidForTransfer());
        newPassenger.setPaymentMethod(PaymentMethod.valueOf(passengerDto.getPaymentMethod()));
        newPassenger.setTransportType(TransportType.valueOf(passengerDto.getTransportType()));
        newPassenger.setNotes(passengerDto.getNotes());
        newPassenger.setCoPassengers(mapCoPassengerDtoListToCoPassengerList(passengerDto.getCoPassengers(), newPassenger));
        return newPassenger;
    }

    public List<CoPassenger> mapCoPassengerDtoListToCoPassengerList(List<CoPassengerDto> coPassengerDtos, Passenger mainPassenger) {
        if (coPassengerDtos != null) {
            return coPassengerDtos.stream()
                                  .map(coPassengerDto -> mapCoPassengerDtoToCoPassengerEntity(coPassengerDto, mainPassenger))
                                  .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public CoPassenger mapCoPassengerDtoToCoPassengerEntity(CoPassengerDto coPassengerDto, Passenger mainPassenger) {
        CoPassenger newCoPassenger = new CoPassenger();
        // should set new Passenger?
        newCoPassenger.setMainPassenger(mainPassenger);
        newCoPassenger.setPassengerType(PassengerType.valueOf(coPassengerDto.getPassengerType()));
        return newCoPassenger;
    }

    //TODO create a builder pattern
    public Passenger updatePassenger(Passenger passenger, PassengerDto passengerDto) {
        updateEmail(passenger, passengerDto.getEmail());
        updateLastName(passenger, passengerDto.getLastName());
        updateFirstName(passenger, passengerDto.getFirstName());
        updatePhoneNumber(passenger, passengerDto.getPhoneNumber());
        updatePickUpDateTime(passenger, passengerDto.getPickUpDateTime());
        updateDepartureDateTime(passenger, passengerDto.getDepartureDateTime());
        updateArrivalDateTime(passenger, passengerDto.getArrivalDateTime());
        updateArrivalAirportLocation(passenger, passengerDto.getArrivalAirportLocation());
        updatePickUpAddress(passenger, passengerDto.getPickUpAddress());
        updatePaidForTransfer(passenger, passengerDto.isPaidForTransfer());
        updatePaymentMethod(passenger, passengerDto.getPaymentMethod());
        updateTransportType(passenger, passengerDto.getTransportType());
        updateNotes(passenger, passengerDto.getNotes());
        return passenger;
    }

    private void updateEmail(Passenger passenger, String newEmail) {
        if (Validator.isStringPropertyValidForUpdate(passenger.getEmail(), newEmail)) {
            passenger.setEmail(newEmail);
        }
    }

    private void updateLastName(Passenger passenger, String newLastName) {
        if (Validator.isStringPropertyValidForUpdate(passenger.getLastName(), newLastName)) {
            passenger.setLastName(newLastName);
        }
    }

    private void updateFirstName(Passenger passenger, String newFirstName) {
        if (Validator.isStringPropertyValidForUpdate(passenger.getFirstName(), newFirstName)) {
            passenger.setFirstName(newFirstName);
        }
    }

    private void updatePhoneNumber(Passenger passenger, String newPhoneNumber) {
        if (Validator.isStringPropertyValidForUpdate(passenger.getPhoneNumber(), newPhoneNumber)) {
            passenger.setPhoneNumber(newPhoneNumber);
        }
    }

    private void updatePickUpDateTime(Passenger passenger, LocalDateTime newPickUpDateTime) {
        if (Validator.isLocalDateTimePropertyValidForUpdate(passenger.getPickUpDateTime(), newPickUpDateTime)) {
            passenger.setPickUpDateTime(newPickUpDateTime);
        }
    }

    private void updateDepartureDateTime(Passenger passenger, LocalDateTime newDepartureDateTime) {
        if (Validator.isLocalDateTimePropertyValidForUpdate(passenger.getDepartureDateTime(), newDepartureDateTime)) {
            passenger.setDepartureDateTime(newDepartureDateTime);
        }
    }

    private void updateArrivalDateTime(Passenger passenger, LocalDateTime newArrivalDateTime) {
        if (Validator.isLocalDateTimePropertyValidForUpdate(passenger.getArrivalDateTime(), newArrivalDateTime)) {
            passenger.setArrivalDateTime(newArrivalDateTime);
        }
    }

    private void updateArrivalAirportLocation(Passenger passenger, String newArrivalAirportLocation) {
        if (Validator.isStringPropertyValidForUpdate(passenger.getArrivalAirportLocation(), newArrivalAirportLocation)) {
            passenger.setArrivalAirportLocation(newArrivalAirportLocation);
        }
    }

    private void updatePickUpAddress(Passenger passenger, String newPickUpAddress) {
        if (Validator.isStringPropertyValidForUpdate(passenger.getPickUpAddress(), newPickUpAddress)) {
            passenger.setPickUpAddress(newPickUpAddress);
        }
    }

    private void updatePaidForTransfer(Passenger passenger, boolean newPaidForTransfer) {

        passenger.setPaidForTransfer(newPaidForTransfer);

    }

    private void updatePaymentMethod(Passenger passenger, String newPaymentMethod) {
        if (Validator.isStringPropertyValidForUpdate(passenger.getPaymentMethod().toString(), newPaymentMethod)) {
            passenger.setPaymentMethod(PaymentMethod.valueOf(newPaymentMethod));
        }
    }

    private void updateTransportType(Passenger passenger, String newTransportType) {
        if (Validator.isStringPropertyValidForUpdate(passenger.getTransportType().toString(), newTransportType)) {
            passenger.setTransportType(TransportType.valueOf(newTransportType));
        }
    }

    private void updateNotes(Passenger passenger, String newNotes) {
        if (Validator.isStringPropertyValidForUpdate(passenger.getNotes(), newNotes)) {
            passenger.setNotes(newNotes);
        }
    }
}
