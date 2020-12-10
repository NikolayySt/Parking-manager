package org.fmi.masters.parkingmanager.modules.driver.ui.filter;

import java.io.Serializable;

import org.fmi.masters.parkingmanager.modules.base.vo.TextVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverFilterBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private TextVo vehicle;

}
