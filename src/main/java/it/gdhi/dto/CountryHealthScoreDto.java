package it.gdhi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CountryHealthScoreDto {

    private String countryId;

    private String countryName;

    private String countryAlpha2Code;

    private List<CategoryHealthScoreDto> categories;

    private Integer countryPhase;

    private String collectedDate;

    @JsonIgnore
    public boolean hasCategories() {
        return this.getCategories().size() > 0;
    }

    public void translateCountryName(String translatedCountryName) {
        if(translatedCountryName != null && translatedCountryName != "")
            this.countryName = translatedCountryName;
    }
}
