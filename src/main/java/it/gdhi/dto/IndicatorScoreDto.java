package it.gdhi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.gdhi.model.CountryHealthIndicator;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class IndicatorScoreDto {
    private Integer id;
    private String code;
    private String name;
    private String indicatorDescription;
    @JsonIgnore
    private Integer rank;
    private Integer score;
    private String supportingText;
    private String scoreDescription;

    IndicatorScoreDto(CountryHealthIndicator countryHealthIndicator) {
        this.id = countryHealthIndicator.getIndicatorId();
        this.code = countryHealthIndicator.getIndicatorCode();
        this.name = countryHealthIndicator.getIndicatorName();
        this.indicatorDescription = countryHealthIndicator.getIndicatorDescription();
        this.score = countryHealthIndicator.getScore();
        this.supportingText = countryHealthIndicator.getSupportingText();
        this.scoreDescription = countryHealthIndicator.getScoreDescription();
        this.rank = countryHealthIndicator.getIndicatorRank();
    }

    public void translateIndicator(String translatedName, String translatedDefinition) {
        if(translatedName != null && !translatedName.isEmpty())
            this.name = translatedName;

        if(translatedDefinition != null && !translatedDefinition.isEmpty())
            this.indicatorDescription = translatedDefinition;
    }

    public void translateScoreDefinition(String translatedScore) {
        if(translatedScore != null && !translatedScore.isEmpty()) this.scoreDescription = translatedScore;
    }
}
