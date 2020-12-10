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
@Table(name = "VEHICLE")
@Getter
@Setter
@EqualsAndHashCode(exclude = { "brand", "model", "numberPlate", "vehicleType" })
public class VehicleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "VEHICLE_ID",
            nullable = false,
            unique = true)
    private Long id;

    @Column(name = "BRAND",
            nullable = false)
    private String brand;

    @Column(name = "MODEL",
            nullable = false)
    private String model;

    @Column(name = "NUMBER_PLATE",
            length = 10,
            nullable = false,
            unique = true)
    private String numberPlate;

    @ManyToOne
    @JoinColumn(name = "VEHICLE_TYPE_ID")
    private VehicleTypeEntity vehicleType;

}
