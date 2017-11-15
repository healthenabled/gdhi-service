package it.gdhi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.OptionalDouble;

import static it.gdhi.utils.ScoreUtils.convertScoreToPhase;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CountryHealthScoreDto {

    private String countryId;

    private String countryName;

    private Double overallScore;

    private List<CategoryHealthScoreDto> categories;

    private Integer countryPhase;

    public CountryHealthScoreDto(String countryId, String countryName, List<CategoryHealthScoreDto> categories) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.categories = categories;
    }

    public Double getOverallScore() {
        OptionalDouble optionalScore = this.categories.stream()
                .filter(category -> category.overallScore().isPresent())
                .mapToDouble(category -> category.overallScore().getAsDouble())
                .average();
        return optionalScore.isPresent() ? optionalScore.getAsDouble() : null;
    }

    public Integer getCountryPhase() {
        return convertScoreToPhase(this.getOverallScore());
    }
}
