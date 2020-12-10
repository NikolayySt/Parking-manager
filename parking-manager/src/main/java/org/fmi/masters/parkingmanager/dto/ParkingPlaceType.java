package org.fmi.masters.parkingmanager.dto;

import java.util.Set;

import org.fmi.masters.parkingmanager.dto.base.NamedDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingPlaceType extends NamedDto {

    private static final long serialVersionUID = 1L;

    private Set<VehicleType> vehicleTypes;

}
