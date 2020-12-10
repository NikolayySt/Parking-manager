package org.fmi.masters.parkingmanager.dto;

import org.fmi.masters.parkingmanager.dto.base.BaseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vehicle extends BaseDto {

    private static final long serialVersionUID = 1L;

    private String brand;
    private String model;
    private String numberPlate;
    private VehicleType vehicleType;

}
