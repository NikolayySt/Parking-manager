package org.fmi.masters.parkingmanager.dto;

import org.fmi.masters.parkingmanager.dto.base.BaseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Driver extends BaseDto {

    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private Vehicle vehicle;

}
