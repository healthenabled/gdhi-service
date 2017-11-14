package it.gdhi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.OptionalDouble;

import static it.gdhi.utils.ScoreUtils.convertScoreToPhase;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryHealthScoreDto {

    private String name;

    private Integer phase;

    private List<IndicatorScoreDto> indicators;

    public CategoryHealthScoreDto(String name, Integer phase) {
        this.name = name;
        this.phase = phase;
    }

    public CategoryHealthScoreDto(String categoryName, List<IndicatorScoreDto> indicatorDtos) {
        this.name = categoryName;
        this.indicators = indicatorDtos;
    }

    public Integer getPhase() {
        OptionalDouble optionalScore = this.overallScore();
        return optionalScore.isPresent() ? convertScoreToPhase(optionalScore.getAsDouble()) : null;
    }

    public OptionalDouble overallScore() {
        return indicators.stream()
                .filter(IndicatorScoreDto::hasScore)
                .mapToDouble(IndicatorScoreDto::getScore)
                .average();
    }
}
