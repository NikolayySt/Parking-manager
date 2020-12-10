package org.fmi.masters.parkingmanager.service.filter;

import java.io.Serializable;

import javax.ws.rs.QueryParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingPlaceFilter implements Serializable {

    private static final long serialVersionUID = 1L;

    @QueryParam("name")
    private String name;

    @QueryParam("parkingId")
    private Long parkingId;

    @QueryParam("driverId")
    private Long driverId;

    @QueryParam("placeTypeId")
    private Long placeTypeId;

}
