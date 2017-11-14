package it.gdhi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HealthIndicators {

    private List<HealthIndicator> healthIndicators;

    public String getCountryName() {
        return healthIndicators!= null && healthIndicators.size() != 0 ? healthIndicators.get(0).getCountry().getName()
                                                                       : null;
    }

    public Map<Category, List<HealthIndicator>> groupByCategory() {
        return this.healthIndicators.stream().collect(groupingBy(HealthIndicator::getCategory));
    }
}
