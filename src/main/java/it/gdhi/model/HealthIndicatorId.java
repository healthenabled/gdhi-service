package it.gdhi.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class HealthIndicatorId implements Serializable {

    private String countryId;
    private Integer categoryId;
    private Integer indicatorId;
    private LocalDate lastSurveyDate;

}
