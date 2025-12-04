package com.sysout.buy_zone_api.models.dto;

import com.sysout.buy_zone_api.enums.PropType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PropsDTO {

    private Long id;
    private String name;
    private String propValue;
    private PropType type;
}
