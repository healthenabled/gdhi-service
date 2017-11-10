package it.gdhi.service;

import it.gdhi.dto.AllCountriesHealthScoreDto;
import it.gdhi.dto.CategoryHealthScoreDto;
import it.gdhi.dto.CountryHealthScoreDto;
import it.gdhi.dto.GlobalHealthScoreDto;
import it.gdhi.model.Category;
import it.gdhi.dto.*;
import it.gdhi.model.HealthIndicators;
import it.gdhi.repository.IHealthIndicatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static it.gdhi.dto.CountryHealthScoreDto.getCountryAverage;
import static it.gdhi.utils.ScoreUtils.convertScoreToPhase;
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
    public AllCountriesHealthScoreDto fetchHealthScores() {
        List<String> countriesWithHealthScores = iHealthIndicatorRepository.findCountriesWithHealthScores();
        List<CountryHealthScoreDto> globalHealthScores = countriesWithHealthScores.stream()
                .map(countryId -> fetchCountryHealthScore(countryId)).collect(toList());
        return transformToGlobalHealthDto(globalHealthScores);
    }

    @Transactional
    public GlobalHealthScoreDto getGlobalHealthIndicator() {
        HealthIndicators healthIndicators = new HealthIndicators(iHealthIndicatorRepository.findAll());
        List<CategoryHealthScoreDto> categories = new ArrayList<>();
        Map<Category, Double> categoryDoubleMap = healthIndicators.groupByCategoryWithNotNullScores();

        double overAllCountryScore = 0.0;
        for (Map.Entry<Category, Double> categoryDoubleEntry : categoryDoubleMap.entrySet()) {
            HashMap<Category, Double> categoryScoreMap = new HashMap<Category, Double>() {{
                put(categoryDoubleEntry.getKey(), categoryDoubleEntry.getValue());
            }};

            overAllCountryScore += getCountryAverage(categoryScoreMap);
            CategoryHealthScoreDto categoryHealthScoreDto =
                    new CategoryHealthScoreDto(categoryDoubleEntry.getKey().getId(),
                            categoryDoubleEntry.getKey().getName(),
                            convertScoreToPhase(categoryDoubleEntry.getValue()));
            categories.add(categoryHealthScoreDto);
        }
        return new GlobalHealthScoreDto(convertScoreToPhase(overAllCountryScore / categoryDoubleMap.size()), categories);
    }

    private AllCountriesHealthScoreDto transformToGlobalHealthDto(List<CountryHealthScoreDto> globalHealthScores) {
        return new AllCountriesHealthScoreDto(globalHealthScores);
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