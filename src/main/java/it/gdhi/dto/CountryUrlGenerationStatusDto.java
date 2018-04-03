package it.gdhi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.gdhi.utils.FormStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryUrlGenerationStatusDto {

    private String countryId;
//    private String msg;

    private boolean success;
    private FormStatus existingStatus;

}
