package it.gdhi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GdhiQuestionnaire {

    private String countryId;

    private CountrySummaryDetailDto countrySummaryDetailDto;

    private List<HealthIndicatorDto> healthIndicatorDto;

}
