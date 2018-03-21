package it.gdhi.dto;

import it.gdhi.model.CountryHealthIndicator;
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

    public IndicatorScoreDto(CountryHealthIndicator countryHealthIndicator) {
        this.id = countryHealthIndicator.getIndicatorId();
        this.name = countryHealthIndicator.getIndicatorName();
        this.indicatorDescription = countryHealthIndicator.getIndicatorDescription();
        this.score = countryHealthIndicator.getScore();
        this.supportingText = countryHealthIndicator.getSupportingText();
        this.scoreDescription = countryHealthIndicator.getScoreDescription();
    }
}
