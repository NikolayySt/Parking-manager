package org.fmi.masters.parkingmanager.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PARKING_PLACE_TYPE")
@Getter
@Setter
@EqualsAndHashCode(exclude = { "name", "vehicleTypes" })
public class ParkingPlaceTypeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PARKING_PLACE_TYPE_ID",
            nullable = false,
            unique = true)
    private Long id;

    @Column(name = "NAME",
            nullable = false,
            unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PARKING_PLACE_TYPE_VEHICLE_TYPES",
            joinColumns = @JoinColumn(name = "PARKING_PLACE_TYPE_ID"),
            inverseJoinColumns = @JoinColumn(name = "VEHICLE_TYPE_ID"))
    private Set<VehicleTypeEntity> vehicleTypes;

}
