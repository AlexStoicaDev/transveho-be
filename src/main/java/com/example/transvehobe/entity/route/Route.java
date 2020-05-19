package com.example.transvehobe.entity.route;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

//TODO create an entity for Location so the users can create new locations and also create a price entity to have different prices
// the route entity should have a set of prices that have a price typpe (shuttle/private/child, etc)
@Data
@Entity
@ToString(of = {"id"})
@Table(name = "routes")
@EqualsAndHashCode(of = {"id"})
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "return_route_id")
    private Route returnRoute;

    @Size(max = 120)
    private String toLocation;

    @Size(max = 120)
    private String fromLocation;

    private Integer distanceInKm;
    private Integer priceInRon;
    private Integer priceInEur;

    @Size(max = 255)
    private String notes;
}
