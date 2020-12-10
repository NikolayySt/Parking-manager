package org.fmi.masters.parkingmanager.modules.base.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "text")
public class TextVo implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final TextVo empty = new TextVo(-1L, "");

    private Long id;
    private String text;

}
