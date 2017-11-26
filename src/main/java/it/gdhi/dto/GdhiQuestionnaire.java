package it.gdhi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class GdhiQuestionnaire {

    private String countryId;

    private CountrySummaryDto countrySummary;

    private List<HealthIndicatorDto> healthIndicators;

}
