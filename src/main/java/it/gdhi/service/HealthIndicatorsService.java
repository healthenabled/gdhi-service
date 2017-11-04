package it.gdhi.service;

import it.gdhi.dto.CategoryHealthScoreDto;
import it.gdhi.dto.CountryHealthScoreDto;
import it.gdhi.model.Category;
import it.gdhi.model.HealthIndicators;
import it.gdhi.repository.IHealthIndicatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ROUND_HALF_EVEN;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.CEILING;
import static java.util.stream.Collectors.toList;

@Service
public class HealthIndicatorsService {

    @Autowired
    private IHealthIndicatorRepository iHealthIndicatorRepository;

    @Transactional
    public CountryHealthScoreDto fetchCountryHealthScore(String countryId) {
        HealthIndicators healthIndicators = new HealthIndicators(iHealthIndicatorRepository
                .findHealthIndicatorsFor(countryId));
        CountryHealthScoreDto countryHealthScoreDto = transformToDto(countryId, healthIndicators);
        return countryHealthScoreDto;
    }

    private CountryHealthScoreDto transformToDto(String countryId, HealthIndicators healthIndicators) {
        Map<Category, Double> notNullHealthScores = healthIndicators.groupByCategoryWithNotNullScores();
        Map<Category, Double> nullHealthScores = healthIndicators.joinNullHealthScoresWith(notNullHealthScores);
        Double countryAverage = getCountryAverage(notNullHealthScores);
        return new CountryHealthScoreDto(countryId, healthIndicators.getCountryName(), countryAverage,
                convertScoreToPhase(countryAverage), transformCategoryMapToList(nullHealthScores), "");
    }

    private Double getCountryAverage(Map<Category, Double> categoryScoreMap) {
        OptionalDouble optionalDouble = categoryScoreMap.values().stream()
                .mapToDouble(Double::doubleValue).average();
        return optionalDouble.isPresent() ? optionalDouble.getAsDouble() : null;
    }

    private List<CategoryHealthScoreDto> transformCategoryMapToList(Map<Category, Double> categoryScoreMap) {
        return categoryScoreMap.entrySet().stream().map(entry ->
                new CategoryHealthScoreDto(entry.getKey().getName(), convertScoreToPhase(entry.getValue())))
                .collect(toList());
    }

    private Integer convertScoreToPhase(Double overallScore) {
        Integer result = null;
        if(overallScore != null) {
            BigDecimal overallScoreInBigDecimal = new BigDecimal(overallScore);
            BigDecimal ceiledScore = overallScoreInBigDecimal.setScale(1, ROUND_HALF_EVEN)
                    .setScale(0, CEILING);
            BigDecimal phase = ceiledScore.equals(ZERO) ? ceiledScore.add(ONE) : ceiledScore;
            result = phase.intValue();
        }
        return result;
    }

}