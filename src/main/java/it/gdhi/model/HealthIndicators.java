package it.gdhi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.groupingBy;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HealthIndicators {

    private List<HealthIndicator> healthIndicators;

    public Map<Category, Double> groupByCategoryWithNotNullScores() {
        return healthIndicators.stream()
                .filter(healthIndicator -> healthIndicator.getScore() != null)
                .collect(groupingBy(HealthIndicator::getCategory,
                        averagingInt(HealthIndicator::getScore)));
    }

    public Map<Integer, Double> groupByCategoryIdWithNotNullScores() {
        return healthIndicators.stream()
                .filter(healthIndicator -> healthIndicator.getScore() != null)
                .collect(groupingBy(h -> h.getCategory().getId(),
                        averagingInt(HealthIndicator::getScore)));
    }

    public Double getOverallScore() {
        OptionalDouble optionalDouble = this.groupByCategoryIdWithNotNullScores()
                .values()
                .stream()
                .mapToDouble(Double::doubleValue)
                .average();
        return optionalDouble.isPresent() ? optionalDouble.getAsDouble() : null;
    }

    public String getCountryName() {
        return healthIndicators!= null && healthIndicators.size() != 0 ? healthIndicators.get(0).getCountry().getName()
                                                                       : null;
    }

    public Map<Category, List<HealthIndicator>> groupByCategory() {
        return this.healthIndicators.stream().collect(groupingBy(HealthIndicator::getCategory));
    }
}
