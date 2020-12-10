package org.fmi.masters.parkingmanager.dto.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NamedDto extends BaseDto {

    private static final long serialVersionUID = 1L;

    private String name;

}
