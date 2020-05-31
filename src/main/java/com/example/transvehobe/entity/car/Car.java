package com.example.transvehobe.entity.car;

import com.example.transvehobe.common.enums.CarStatus;
import com.example.transvehobe.common.enums.EngineType;
import com.example.transvehobe.entity.transfer.Transfer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "cars")
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 10)
    private String plateNumber;

    @NotBlank
    @Size(max = 255)
    private String model;

    private Integer numberOfSeats;

    private String chassisNumber;

    @Enumerated(EnumType.STRING)
    private EngineType engineType;

    private boolean isRented = false;
    private boolean isInTransit = false;

    @Enumerated(EnumType.STRING)
    private CarStatus status;

    private LocalDate itpExpirationDate;
    private LocalDate rovignetteExpirationDate;
    private LocalDate huvignetteExpirationDate;
    private LocalDate rcaExpirationDate;

    @Size(max = 255)
    private String others;

    @JsonIgnore
    @OneToMany(mappedBy = "car")
    private List<Transfer> transfers = new ArrayList<>();
}
