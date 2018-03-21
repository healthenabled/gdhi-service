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
public class CountryHealthIndicators {

    private List<CountryHealthIndicator> countryHealthIndicators;

    public Map<Integer, Double> groupByCategoryIdWithNotNullScores() {
        return excludingNullScores()
                .collect(groupingBy(h -> h.getCategory().getId(),
                        averagingInt(CountryHealthIndicator::getScore)));
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
        return Optional.ofNullable(countryHealthIndicators)
                .filter(indicators -> indicators.size() > 0)
                .map(indicators -> indicators.get(0).getCountry().getName())
                .orElse(null);
    }

    public Double getTotalScore() {
        return groupByCategoryIdWithNotNullScores()
                .values().stream().mapToDouble(d ->d).sum();
    }


    public Map<Category, List<CountryHealthIndicator>> groupByCategory() {
        return this.countryHealthIndicators.stream().collect(groupingBy(CountryHealthIndicator::getCategory));
    }

    public Integer getCountOfCountriesWithAlteastOneScore() {
        return excludingNullScores()
                .collect(groupingBy(indicators -> indicators.getCountry().getId()))
                .size();
    }

    private Stream<CountryHealthIndicator> excludingNullScores() {
        return countryHealthIndicators.stream()
                .filter(healthIndicator -> healthIndicator.getScore() != null);
    }

    public Map<String, List<CountryHealthIndicator>> groupByCountry() {
        return this.countryHealthIndicators.stream().collect(groupingBy(CountryHealthIndicator::getCountryId));
    }
}
