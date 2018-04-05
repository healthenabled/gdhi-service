package it.gdhi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String countryAlpha2Code;

    private Double overallScore;

    private List<CategoryHealthScoreDto> categories;

    private Integer countryPhase;

    private String collectedDate;

    @JsonIgnore
    public boolean hasCategories() {
        return this.getCategories().size() > 0;
    }
}
