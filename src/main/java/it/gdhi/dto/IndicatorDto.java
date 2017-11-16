package it.gdhi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.gdhi.model.Indicator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndicatorDto {

    private Integer indicatorId;

    private String indicatorName;

    private String indicatorDefinition;

    private List<ScoreDto> scores;

    public IndicatorDto(Integer indicatorId, String indicatorName, String indicatorDefinition) {
        this.indicatorId = indicatorId;
        this.indicatorName = indicatorName;
        this.indicatorDefinition = indicatorDefinition;
    }

    public IndicatorDto(Indicator indicator) {
        this.indicatorId = indicator.getIndicatorId();
        this.indicatorName = indicator.getName();
        this.indicatorDefinition = indicator.getDefinition();
        this.scores = indicator.getOptions().stream().map(option -> new ScoreDto(option)).collect(Collectors.toList());
    }
}
