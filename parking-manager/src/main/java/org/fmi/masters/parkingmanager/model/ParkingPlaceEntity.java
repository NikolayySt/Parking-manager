package org.fmi.masters.parkingmanager.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PARKING_PLACE")
@Getter
@Setter
@EqualsAndHashCode(exclude = { "name", "driver", "placeType" })
public class ParkingPlaceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PARKING_PLACE_ID")
    private Long id;

    @Column(name = "NAME",
            nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "DRIVER_ID",
            nullable = true)
    private DriverEntity driver;
    
    @ManyToOne
    @JoinColumn(name = "PARKING_PLACE_TYPE_ID",
            nullable = false)
    private ParkingPlaceTypeEntity placeType;

    @ManyToOne
    @JoinColumn(name = "PARKING_ID")
    private ParkingEntity parking;

}
