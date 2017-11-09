package it.gdhi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IndicatorDto {

    private Integer indicatorId;

    private String indicatorName;

    private String indicatorDefinition;

}
