package it.gdhi.service;

import it.gdhi.dto.*;
import it.gdhi.model.HealthIndicators;
import it.gdhi.repository.IHealthIndicatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Service
public class HealthIndicatorService {

    @Autowired
    private IHealthIndicatorRepository iHealthIndicatorRepository;

    @Transactional
    public CountryHealthScoreDto fetchCountryHealthScore(String countryId) {
        HealthIndicators healthIndicators = new HealthIndicators(iHealthIndicatorRepository
                .findHealthIndicatorsFor(countryId));
        return transformToCountryHealthDto(countryId, healthIndicators);
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

    private CountryHealthScoreDto transformToCountryHealthDto(String countryId, HealthIndicators healthIndicators) {
        List<CategoryHealthScoreDto> categoryDtos = healthIndicators.groupByCategory()
                .entrySet()
                .stream()
                .map(entry -> {
                    String categoryName = entry.getKey().getName();
                    Integer categoryId = entry.getKey().getId();
                    List<IndicatorScoreDto> indicatorDtos = entry.getValue()
                            .stream()
                            .map(healthIndicator -> new IndicatorScoreDto(healthIndicator.getIndicatorId(),
                                    healthIndicator.getIndicatorName(),
                                    healthIndicator.getIndicatorDescription(),
                                    healthIndicator.getScore(),
                                    healthIndicator.getScoreDescription()))
                            .sorted(comparing(IndicatorScoreDto::getId))
                            .collect(Collectors.toList());
                    return new CategoryHealthScoreDto(categoryId, categoryName, indicatorDtos);
                })
                .sorted(comparing(CategoryHealthScoreDto::getId))
                .collect(Collectors.toList());
        return new CountryHealthScoreDto(countryId, healthIndicators.getCountryName(), categoryDtos);
    }
}