package org.fmi.masters.parkingmanager.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PARKING")
@Getter
@Setter
@EqualsAndHashCode(exclude = { "name" })
public class ParkingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PARKING_ID",
            nullable = false,
            unique = true)
    private Long id;

    @Column(name = "NAME",
            nullable = false,
            unique = true)
    private String name;

}
