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

    private Integer countryPhase;

}
