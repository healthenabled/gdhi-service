package it.gdhi.service;

import it.gdhi.dto.*;
import it.gdhi.model.HealthIndicator;
import it.gdhi.model.HealthIndicators;
import it.gdhi.repository.IHealthIndicatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static it.gdhi.utils.ScoreUtils.convertScoreToPhase;
import static java.util.Comparator.comparing;
import static java.util.Comparator.nullsLast;
import static java.util.stream.Collectors.toList;

@Service
public class HealthIndicatorService {

    @Autowired
    private IHealthIndicatorRepository iHealthIndicatorRepository;

    @Autowired
    private ExcelUtilService excelUtilService;

    @Transactional
    public CountryHealthScoreDto fetchCountryHealthScore(String countryId) {
        HealthIndicators healthIndicators = new HealthIndicators(iHealthIndicatorRepository
                .findHealthIndicatorsFor(countryId));
        return transformToCountryHealthDto(countryId, healthIndicators);
    }

    public CountriesHealthScoreDto fetchHealthScores() {
        return this.fetchHealthScores(null, null);
    }

    @Transactional
    public CountriesHealthScoreDto fetchHealthScores(Integer categoryId, Integer score) {
        HealthIndicators healthIndicators = new HealthIndicators(iHealthIndicatorRepository.find(categoryId, score));
        Map<String, List<HealthIndicator>> groupByCountry = healthIndicators.groupByCountry();
        List<CountryHealthScoreDto> globalHealthScores = groupByCountry
                .entrySet()
                .stream()
                .map(entry -> {
                    String countryId = entry.getKey();
                    List<HealthIndicator> indicators = entry.getValue();
                    return transformToCountryHealthDto(countryId, new HealthIndicators(indicators));
                })
                .sorted(comparing(CountryHealthScoreDto::getCountryName, nullsLast(Comparator.naturalOrder())))
                .collect(toList());
        return new CountriesHealthScoreDto(globalHealthScores);
    }


    @Transactional
    public GlobalHealthScoreDto getGlobalHealthIndicator(Integer categoryId, Integer score) {
        HealthIndicators healthIndicators = new HealthIndicators(iHealthIndicatorRepository.find(categoryId, score));
        List<CategoryHealthScoreDto> sortedCategoriesWithIndicators =
                getSortedCategoriesWithIndicators(healthIndicators);
        Double totalScore = healthIndicators.getTotalScore();
        Integer countryCount = healthIndicators.getCountOfCountriesWithAlteastOneScore();
        double overallScore = totalScore / countryCount;
        return new GlobalHealthScoreDto(convertScoreToPhase(overallScore), sortedCategoriesWithIndicators);
    }

    public void createGlobalHealthIndicatorInExcel(HttpServletRequest request,
                                                   HttpServletResponse response) throws IOException {
        excelUtilService.convertListToExcel(fetchHealthScores().getCountryHealthScores());
        excelUtilService.downloadFile(request, response);
    }

    public void createHealthIndicatorInExcelFor(String countryId, HttpServletRequest request,
                                                HttpServletResponse response) throws IOException {
        List countryHealthScoreDtoAsList = new ArrayList<CountryHealthScoreDto>();
        countryHealthScoreDtoAsList.add(fetchCountryHealthScore(countryId));
        excelUtilService.convertListToExcel(countryHealthScoreDtoAsList);
        excelUtilService.downloadFile(request, response);
    }

    private CountryHealthScoreDto transformToCountryHealthDto(String countryId, HealthIndicators healthIndicators) {
        List<CategoryHealthScoreDto> categoryDtos = getSortedCategoriesWithIndicators(healthIndicators);
        Double overallScore = healthIndicators.getOverallScore();
        return new CountryHealthScoreDto(countryId, healthIndicators.getCountryName(), overallScore, categoryDtos,
                convertScoreToPhase(overallScore));
    }

    private List<CategoryHealthScoreDto> getSortedCategoriesWithIndicators(HealthIndicators healthIndicators) {
        Map<Integer, Double> nonNullCategoryScore = healthIndicators.groupByCategoryIdWithNotNullScores();
        return healthIndicators.groupByCategory()
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
    }
}