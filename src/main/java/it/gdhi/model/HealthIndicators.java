package it.gdhi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Stream;

import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.groupingBy;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HealthIndicators {

    private List<HealthIndicator> healthIndicators;

    public Map<Integer, Double> groupByCategoryIdWithNotNullScores() {
        return excludingNullScores()
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
        return Optional.ofNullable(healthIndicators)
                .filter(indicators -> indicators.size() > 0)
                .map(indicators -> indicators.get(0).getCountry().getName())
                .orElse(null);
    }

    public Double getTotalScore() {
        return groupByCategoryIdWithNotNullScores()
                .values().stream().mapToDouble(d ->d).sum();
    }


    public Map<Category, List<HealthIndicator>> groupByCategory() {
        return this.healthIndicators.stream().collect(groupingBy(HealthIndicator::getCategory));
    }

    public Integer getCountOfCountriesWithAlteastOneScore() {
        return excludingNullScores()
                .collect(groupingBy(indicators -> indicators.getCountry().getId()))
                .size();
    }

    private Stream<HealthIndicator> excludingNullScores() {
        return healthIndicators.stream()
                .filter(healthIndicator -> healthIndicator.getScore() != null);
    }
}
