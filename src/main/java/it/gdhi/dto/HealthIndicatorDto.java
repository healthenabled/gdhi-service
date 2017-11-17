package it.gdhi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HealthIndicatorDto {

    private Integer categoryId;
    private Integer indicatorId;
    private Integer score;
    private String supportingText;
}
