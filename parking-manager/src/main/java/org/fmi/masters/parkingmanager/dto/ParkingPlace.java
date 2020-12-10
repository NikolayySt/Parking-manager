package org.fmi.masters.parkingmanager.dto;

import org.fmi.masters.parkingmanager.dto.base.NamedDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingPlace extends NamedDto {

    private static final long serialVersionUID = 1L;

    private Driver driver;

    private ParkingPlaceType parkingPlaceType;

    private Parking parking;

}
