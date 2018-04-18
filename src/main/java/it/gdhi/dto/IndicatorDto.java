package it.gdhi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.gdhi.model.Indicator;
import it.gdhi.model.IndicatorScore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndicatorDto {

    private Integer indicatorId;

    private String indicatorCode;

    private String indicatorName;

    private String indicatorDefinition;

    @JsonIgnore
    private Integer indicatorRank;

    private List<ScoreDto> scores;

    public IndicatorDto(Indicator indicator) {
        this.indicatorId = indicator.getIndicatorId();
        this.indicatorCode = indicator.getCode();
        this.indicatorName = indicator.getName();
        this.indicatorDefinition = indicator.getDefinition();
        this.indicatorRank = indicator.getRank();
        this.scores = getScores(indicator);
    }

    private List<ScoreDto> getScores(Indicator indicator) {
        List<IndicatorScore> options = indicator.getOptions();
        return options != null ? options.stream().map(ScoreDto::new)
                                   .collect(toList()) : null;
    }
}
