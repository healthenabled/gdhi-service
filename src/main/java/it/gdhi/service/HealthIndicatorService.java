package it.gdhi.service;

import it.gdhi.dto.*;
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
import java.util.stream.Collectors;

import static java.math.BigDecimal.*;
import static java.math.RoundingMode.CEILING;
import static java.util.stream.Collectors.toList;

@Service
public class HealthIndicatorService {

    @Autowired
    private IHealthIndicatorRepository iHealthIndicatorRepository;

    @Transactional
    public CountryHealthScoreDto fetchCountryHealthScore(String countryId) {
        HealthIndicators healthIndicators = new HealthIndicators(iHealthIndicatorRepository
                .findHealthIndicatorsFor(countryId));
        return transformToCountryHealthDto1(countryId, healthIndicators);
    }

    @Transactional
    public GlobalHealthScoreDto fetchHealthScores() {
        List<String> countriesWithHealthScores = iHealthIndicatorRepository.findCountriesWithHealthScores();
        List<CountryHealthScoreDto> globalHealthScores = countriesWithHealthScores.stream()
                .map(countryId -> fetchCountryHealthScore(countryId)).collect(toList());
        return transformToGlobalHealthDto(globalHealthScores);
    }

    private GlobalHealthScoreDto transformToGlobalHealthDto(List<CountryHealthScoreDto> globalHealthScores) {
        return new GlobalHealthScoreDto(globalHealthScores);
    }

    private CountryHealthScoreDto transformToCountryHealthDto1(String countryId, HealthIndicators healthIndicators) {
        List<CategoryHealthScoreDto> categoryDtos = healthIndicators.groupByCategory()
                .entrySet()
                .stream()
                .map(entry -> {
            String categoryName = entry.getKey().getName();
            List<IndicatorScoreDto> indicatorDtos = entry.getValue().stream()
                    .map(healthIndicator -> new IndicatorScoreDto(healthIndicator.getIndicatorName(),
                            healthIndicator.getIndicatorDescription(), healthIndicator.getScore(),
                            healthIndicator.getScoreDescription())).collect(Collectors.toList());
            return new CategoryHealthScoreDto(categoryName, indicatorDtos);
        }).collect(Collectors.toList());
        return new CountryHealthScoreDto(countryId, healthIndicators.getCountryName(), categoryDtos);
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