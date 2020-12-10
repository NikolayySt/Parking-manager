package org.fmi.masters.parkingmanager.service.filter;

import java.io.Serializable;

import javax.ws.rs.QueryParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleFilter implements Serializable {

    private static final long serialVersionUID = 1L;

    @QueryParam("brand")
    private String brand;

    @QueryParam("model")
    private String model;

    @QueryParam("numberPlate")
    private String numberPlate;

    @QueryParam("vehicleTypeId")
    private Long vehicleTypeId;

}
