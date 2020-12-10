package org.fmi.masters.parkingmanager.service.filter;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleTypeFilter implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    
}
