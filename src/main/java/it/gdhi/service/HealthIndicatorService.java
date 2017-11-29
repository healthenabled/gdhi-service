package it.gdhi.service;

import it.gdhi.dto.*;
import it.gdhi.model.Category;
import it.gdhi.model.HealthIndicators;
import it.gdhi.repository.IHealthIndicatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static it.gdhi.utils.ScoreUtils.ZERO_SCORE;
import static it.gdhi.utils.ScoreUtils.convertScoreToPhase;
import static java.util.Comparator.comparing;
import static java.util.Comparator.nullsLast;
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
                .map(countryId -> fetchCountryHealthScore(countryId))
                .sorted(comparing(CountryHealthScoreDto::getCountryName,
                        nullsLast(Comparator.naturalOrder())))
                .collect(toList());
        return transformToGlobalHealthDto(globalHealthScores);
    }

    @Transactional
    public GlobalHealthScoreDto getGlobalHealthIndicator() {
        HealthIndicators healthIndicators = new HealthIndicators(iHealthIndicatorRepository.findAll());
        List<CategoryHealthScoreDto> categories = new ArrayList<>();
        Map<Category, Double> categoryDoubleMap = healthIndicators.groupByCategoryWithNotNullScores();

        for (Map.Entry<Category, Double> categoryDoubleEntry : categoryDoubleMap.entrySet()) {
            CategoryHealthScoreDto categoryHealthScoreDto =
                    new CategoryHealthScoreDto(categoryDoubleEntry.getKey().getId(),
                            categoryDoubleEntry.getKey().getName(),
                            convertScoreToPhase(categoryDoubleEntry.getValue()));
            categories.add(categoryHealthScoreDto);
        }

        AllCountriesHealthScoreDto allCountriesData = fetchHealthScores();
        double score = ZERO_SCORE;
        List<CountryHealthScoreDto> globalHealthScoresWitOutNullScore = allCountriesData.getCountryHealthScores()
                .stream()
                .filter(country -> country.getOverallScore() != null)
                .collect(Collectors.toList());
        for (CountryHealthScoreDto countryHealthScore : globalHealthScoresWitOutNullScore) {
            score += countryHealthScore.getOverallScore();
        }

        int size = globalHealthScoresWitOutNullScore.size();
        List<CategoryHealthScoreDto> sortedCategories = categories.stream()
                .sorted(comparing(CategoryHealthScoreDto::getId)).collect(toList());
        return new GlobalHealthScoreDto((convertScoreToPhase(score / size)), sortedCategories);
    }

    private AllCountriesHealthScoreDto transformToGlobalHealthDto(List<CountryHealthScoreDto> globalHealthScores) {
        return new AllCountriesHealthScoreDto(globalHealthScores);
    }

    private CountryHealthScoreDto transformToCountryHealthDto(String countryId, HealthIndicators healthIndicators) {
        Map<Integer, Double> nonNullCategoryScore = healthIndicators.groupByCategoryIdWithNotNullScores();
        List<CategoryHealthScoreDto> categoryDtos = healthIndicators.groupByCategory()
                .entrySet()
                .stream()
                .map(entry -> {
                    String categoryName = entry.getKey().getName();
                    Integer categoryId = entry.getKey().getId();
                    Double categoryScore = nonNullCategoryScore.get(categoryId);
                    List<IndicatorScoreDto> indicatorDtos = entry.getValue()
                            .stream()
                            .map(IndicatorScoreDto::new)
                            .sorted(comparing(IndicatorScoreDto::getId))
                            .collect(Collectors.toList());
                    return new CategoryHealthScoreDto(categoryId, categoryName, categoryScore,
                            convertScoreToPhase(categoryScore), indicatorDtos);
                })
                .sorted(comparing(CategoryHealthScoreDto::getId))
                .collect(Collectors.toList());
        Double overallScore = healthIndicators.getOverallScore();
        return new CountryHealthScoreDto(countryId, healthIndicators.getCountryName(), overallScore, categoryDtos,
                convertScoreToPhase(overallScore));
    }

    public void createGlobalHealthIndicatorInExcel() {
        new ExcelUtilService().convertListToExcel(fetchHealthScores().getCountryHealthScores());
    }
    public void createHealthIndicatorInExcelFor(String countryId){
        fetchCountryHealthScore(countryId);
        List countryHealthScoreDtoAsList = new ArrayList<CountryHealthScoreDto>();
        countryHealthScoreDtoAsList.add(fetchCountryHealthScore(countryId));
        new ExcelUtilService().convertListToExcel(countryHealthScoreDtoAsList);
    }
}