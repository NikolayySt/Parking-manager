package org.fmi.masters.parkingmanager.service.filter;

import java.io.Serializable;

import javax.ws.rs.QueryParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverFilter implements Serializable {

    private static final long serialVersionUID = 1L;

    @QueryParam("firstName")
    private String firstName;

    @QueryParam("lastName")
    private String lastName;

    @QueryParam("vehicleId")
    private Long vehicleId;

}
