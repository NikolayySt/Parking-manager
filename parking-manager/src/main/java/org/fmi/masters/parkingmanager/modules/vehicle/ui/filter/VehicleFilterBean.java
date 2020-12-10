package org.fmi.masters.parkingmanager.modules.vehicle.ui.filter;

import java.io.Serializable;

import org.fmi.masters.parkingmanager.modules.base.vo.TextVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleFilterBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String brand;
    private String model;
    private String numberPlate;
    private TextVo vehicleType;

}
