package org.fmi.masters.parkingmanager.dto.base;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

}
