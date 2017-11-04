package it.gdhi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CountryHealthScoreDto {

    private String countryId;

    private String countryName;

    private Double overallScore;

    private List<CategoryHealthScoreDto> categories;

    private String colorCode;

    private Integer countryPhase;

    public CountryHealthScoreDto(String countryId, String name, Double countryAverage, Integer countryPhase,
                                 List<CategoryHealthScoreDto> categories, String colorCode) {
        this.countryId = countryId;
        this.countryName  = name;
        this.overallScore  = countryAverage;
        this.countryPhase  = countryPhase;
        this.categories = categories;
        this.colorCode = colorCode;
    }

}
