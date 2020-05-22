package com.example.transvehobe.entity.passenger;

import com.example.transvehobe.common.enums.PaymentMethod;
import com.example.transvehobe.common.enums.TransportType;
import com.example.transvehobe.entity.route.Route;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@ToString(of = {"id"})
@Table(name = "passengers")
@EqualsAndHashCode(of = {"id"})
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "mainPassenger", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<CoPassenger> coPassengers;

    @Email
    @NotBlank
    @Size(max = 40)
    private String email;

    @Size(max = 50)
    private String lastName;

    @Size(max = 50)
    private String firstName;

    @Size(max = 12)
    private String phoneNumber;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "route_id")
    private Route route;

    private LocalDateTime pickUpDateTime;

    private LocalDateTime departureDateTime;

    private LocalDateTime arrivalDateTime;

    @Size(max = 255)
    private String arrivalAirportLocation;

    @Size(max = 255)
    private String pickUpAddress;

    private boolean paidForTransfer;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private TransportType transportType;

    @Size(max = 255)
    private String notes;
}
