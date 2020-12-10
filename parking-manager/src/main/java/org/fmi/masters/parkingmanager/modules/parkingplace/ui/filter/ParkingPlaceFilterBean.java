package org.fmi.masters.parkingmanager.modules.parkingplace.ui.filter;

import java.io.Serializable;

import org.fmi.masters.parkingmanager.modules.base.vo.TextVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingPlaceFilterBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private TextVo parking;
    private TextVo driver;
    private TextVo placeType;

}
