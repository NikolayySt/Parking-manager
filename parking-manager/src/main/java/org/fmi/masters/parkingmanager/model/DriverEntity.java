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
@Table(name = "DRIVER")
@Setter
@Getter
@EqualsAndHashCode(exclude = { "firstName", "lastName", "vehicle" })
public class DriverEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DRIVER_ID",
            nullable = false,
            unique = true)
    private Long id;

    @Column(name = "FIRST_NAME",
            nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME",
            nullable = false)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "VEHICLE_ID",
            nullable = false)
    private VehicleEntity vehicle;


}
