package it.gdhi.dto;

import it.gdhi.model.HealthIndicator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IndicatorScoreDto {
    private Integer id;
    private String name;
    private String indicatorDescription;
    private Integer score;
    private String supportingText;
    private String scoreDescription;

    public IndicatorScoreDto(HealthIndicator healthIndicator) {
        this.id = healthIndicator.getIndicatorId();
        this.name = healthIndicator.getIndicatorName();
        this.indicatorDescription = healthIndicator.getIndicatorDescription();
        this.score = healthIndicator.getScore();
        this.supportingText = healthIndicator.getSupportingText();
        this.scoreDescription = healthIndicator.getScoreDescription();
    }
}
