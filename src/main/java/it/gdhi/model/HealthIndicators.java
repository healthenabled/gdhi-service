package it.gdhi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

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

    public Map<Category, Double> joinNullHealthScoresWith(Map<Category, Double> categoryScoreMap) {
        List<Integer> categoryIdsWithNullScores = categoryScoreMap.keySet().stream().map(k -> k.getId())
                                                  .collect(toList());
        Map<Category, Double> categoryScoreMapWithNullValues = new HashMap<>();
        categoryScoreMapWithNullValues.putAll(categoryScoreMap);
        getHealthIndicatorsWithNullScores(categoryIdsWithNullScores).forEach(nullIndicatorScore -> {
            categoryScoreMapWithNullValues.put(nullIndicatorScore.getCategory(), null);
        });
        return categoryScoreMapWithNullValues;
    }

    private List<HealthIndicator> getHealthIndicatorsWithNullScores(List<Integer> allCategoryIds) {
        return healthIndicators.stream()
               .filter(healthIndicator -> healthIndicator.getScore() == null)
               .filter(healthIndicator -> !allCategoryIds.contains(healthIndicator.getCategory().getId()))
               .collect(toList());
    }

    public String getCountryName() {
        return healthIndicators!= null && healthIndicators.size() != 0 ? healthIndicators.get(0).getCountry().getName()
                                                                       : null;
    }

}
