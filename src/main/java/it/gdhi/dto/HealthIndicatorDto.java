package it.gdhi.dto;

import it.gdhi.model.HealthIndicator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class HealthIndicatorDto {

    private Integer categoryId;
    private Integer indicatorId;
    private Integer score;
    private String supportingText;

    public HealthIndicatorDto(HealthIndicator healthIndicator) {
        this.categoryId = healthIndicator.getHealthIndicatorId().getCategoryId();
        this.indicatorId = healthIndicator.getHealthIndicatorId().getIndicatorId();
        this.score = healthIndicator.getScore();
        this.supportingText = healthIndicator.getSupportingText();
    }
}
