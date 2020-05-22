package com.example.transvehobe.entity.passenger;

import com.example.transvehobe.common.enums.PassengerType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@ToString(of = {"id"})
@Table(name = "co_passengers")
@EqualsAndHashCode(of = {"id"})
public class CoPassenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PassengerType passengerType;

    @JoinColumn(name = "main_passenger_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Passenger mainPassenger;
}
