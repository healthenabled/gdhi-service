package it.gdhi.dto;

import it.gdhi.model.Category;
import it.gdhi.model.CountryHealthIndicator;
import it.gdhi.model.Score;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryHealthScoreDto {

    private Integer id;

    private String name;

    private Double overallScore;

    private Integer phase;

    private List<IndicatorScoreDto> indicators;

    public CategoryHealthScoreDto(Category category, Double categoryScore,
                                  List<CountryHealthIndicator> countryHealthIndicators) {
        this.id = category.getId();
        this.name = category.getName();
        this.overallScore = categoryScore;
        this.phase = (new Score(overallScore)).convertToPhase();
        this.indicators = transformAndSort(countryHealthIndicators);
    }

    private List<IndicatorScoreDto> transformAndSort(List<CountryHealthIndicator> countryHealthIndicators) {
        return countryHealthIndicators.stream()
                .map(IndicatorScoreDto::new)
                .sorted(comparing(IndicatorScoreDto::getRank))
                .collect(Collectors.toList());
    }

    public void setTranslatedName(String name) {
        this.name = name;
    }
}
