package com.example.transvehobe.common.mappers;

import com.example.transvehobe.common.dto.PassengerDto;
import com.example.transvehobe.common.enums.PaymentMethod;
import com.example.transvehobe.common.enums.TransportType;
import com.example.transvehobe.common.validators.Validator;
import com.example.transvehobe.entity.passenger.Passenger;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class PassengerMapper {

    public Passenger mapPassengerDtoToPassengerEntity(PassengerDto passengerDto) {
        Passenger newPassenger = new Passenger();
        newPassenger.setEmail(passengerDto.getEmail());
        newPassenger.setLastName(passengerDto.getLastName());
        newPassenger.setFirstName(passengerDto.getFirstName());
        newPassenger.setPhoneNumber(passengerDto.getPhoneNumber());

        newPassenger.setPaymentMethod(PaymentMethod.valueOf(passengerDto.getPaymentMethod()));
        newPassenger.setTransportType(TransportType.valueOf(passengerDto.getTransportType()));

        newPassenger.setPickUpAddress(passengerDto.getPickUpAddress());
        newPassenger.setDestinationAddress(passengerDto.getDestinationAddress());
        newPassenger.setPickUpDateTime(passengerDto.getPickUpDateTime());
        newPassenger.setFlightDateTime(passengerDto.getFlightDateTime());
        newPassenger.setFlightDetails(passengerDto.getFlightDetails());

        newPassenger.setReturnPickUpAddress(passengerDto.getReturnPickUpAddress());
        newPassenger.setReturnDestinationAddress(passengerDto.getReturnDestinationAddress());
        newPassenger.setReturnPickUpDateTime(passengerDto.getReturnPickUpDateTime());
        newPassenger.setReturnFlightDetails(passengerDto.getReturnFlightDetails());

        newPassenger.setReturnTransfer(passengerDto.isReturnTransfer());
        newPassenger.setPaidForTransfer(passengerDto.isPaidForTransfer());

        newPassenger.setNumberOfAdults(passengerDto.getNumberOfAdults());
        newPassenger.setNumberOfChildren(passengerDto.getNumberOfChildren());
        newPassenger.setNumberOfInfants(passengerDto.getNumberOfInfants());

        return newPassenger;
    }


    //TODO add proper validation for fields for all entities
    //TODO create a builder pattern
    public Passenger updatePassenger(Passenger passenger, PassengerDto passengerDto) {
        updateEmail(passenger, passengerDto.getEmail());
        updateLastName(passenger, passengerDto.getLastName());
        updateFirstName(passenger, passengerDto.getFirstName());
        updatePhoneNumber(passenger, passengerDto.getPhoneNumber());
        updatePaymentMethod(passenger, passengerDto.getPaymentMethod());
        updateTransportType(passenger, passengerDto.getTransportType());
        updatePickUpAddress(passenger, passengerDto.getPickUpAddress());
        updateDestinationAddress(passenger, passengerDto.getDestinationAddress());
        updatePickUpDateTime(passenger, passengerDto.getPickUpDateTime());
        updateFlightDateTime(passenger, passengerDto.getFlightDateTime());
        updateFlightDetails(passenger, passengerDto.getFlightDetails());
        updateReturnPickUpAddress(passenger, passengerDto.getReturnPickUpAddress());
        updateReturnDestinationAddress(passenger, passengerDto.getReturnDestinationAddress());
        updateReturnPickUpDateTime(passenger, passengerDto.getReturnPickUpDateTime());
        updateReturnFlightDetails(passenger, passengerDto.getReturnFlightDetails());
        updateReturnTransfer(passenger, passengerDto.isReturnTransfer());
        updatePaidForTransfer(passenger, passengerDto.isPaidForTransfer());
        passenger.setNumberOfInfants(passengerDto.getNumberOfInfants());
        passenger.setNumberOfChildren(passengerDto.getNumberOfChildren());
        passenger.setNumberOfAdults(passengerDto.getNumberOfAdults());

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

    private void updatePickUpAddress(Passenger passenger, String newPickUpAddress) {
        if (Validator.isStringPropertyValidForUpdate(passenger.getPickUpAddress(), newPickUpAddress)) {
            passenger.setPickUpAddress(newPickUpAddress);
        }
    }

    private void updateDestinationAddress(Passenger passenger, String newDestinationAddress) {
        if (Validator.isStringPropertyValidForUpdate(passenger.getDestinationAddress(), newDestinationAddress)) {
            passenger.setDestinationAddress(newDestinationAddress);
        }
    }

    private void updatePickUpDateTime(Passenger passenger, LocalDateTime newPickUpDateTime) {
        if (Validator.isLocalDateTimePropertyValidForUpdate(passenger.getPickUpDateTime(), newPickUpDateTime)) {
            passenger.setPickUpDateTime(newPickUpDateTime);
        }
    }

    private void updateFlightDateTime(Passenger passenger, LocalDateTime newFlightDateTime) {
        if (Validator.isLocalDateTimePropertyValidForUpdate(passenger.getFlightDateTime(), newFlightDateTime)) {
            passenger.setFlightDateTime(newFlightDateTime);
        }
    }

    private void updateFlightDetails(Passenger passenger, String newFlightDetails) {
        if (Validator.isStringPropertyValidForUpdate(passenger.getFlightDetails(), newFlightDetails)) {
            passenger.setFlightDetails(newFlightDetails);
        }
    }

    private void updateReturnPickUpAddress(Passenger passenger, String newReturnPickUpAddress) {
        if (Validator.isStringPropertyValidForUpdate(passenger.getReturnPickUpAddress(), newReturnPickUpAddress)) {
            passenger.setReturnPickUpAddress(newReturnPickUpAddress);
        }
    }

    private void updateReturnDestinationAddress(Passenger passenger, String newReturnDestinationAddress) {
        if (Validator.isStringPropertyValidForUpdate(passenger.getReturnDestinationAddress(), newReturnDestinationAddress)) {
            passenger.setReturnDestinationAddress(newReturnDestinationAddress);
        }
    }

    private void updateReturnPickUpDateTime(Passenger passenger, LocalDateTime newReturnPickUpDateTime) {
        if (Validator.isLocalDateTimePropertyValidForUpdate(passenger.getReturnPickUpDateTime(), newReturnPickUpDateTime)) {
            passenger.setReturnPickUpDateTime(newReturnPickUpDateTime);
        }
    }

    private void updateReturnFlightDetails(Passenger passenger, String newReturnFlightDetails) {
        if (Validator.isStringPropertyValidForUpdate(passenger.getReturnFlightDetails(), newReturnFlightDetails)) {
            passenger.setReturnFlightDetails(newReturnFlightDetails);
        }
    }

    private void updateReturnTransfer(Passenger passenger, boolean newReturnTransfer) {
        passenger.setReturnTransfer(newReturnTransfer);
    }

    private void updatePaidForTransfer(Passenger passenger, boolean newPaidForTransfer) {
        passenger.setPaidForTransfer(newPaidForTransfer);
    }
}
