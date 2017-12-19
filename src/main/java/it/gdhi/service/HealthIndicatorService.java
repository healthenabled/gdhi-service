package it.gdhi.service;

import it.gdhi.dto.CategoryHealthScoreDto;
import it.gdhi.dto.CountriesHealthScoreDto;
import it.gdhi.dto.CountryHealthScoreDto;
import it.gdhi.dto.GlobalHealthScoreDto;
import it.gdhi.model.Category;
import it.gdhi.model.HealthIndicator;
import it.gdhi.model.HealthIndicators;
import it.gdhi.repository.IHealthIndicatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import static it.gdhi.utils.ScoreUtils.convertScoreToPhase;
import static java.util.Comparator.comparing;
import static java.util.Comparator.nullsLast;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;
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
        return transformToCountryHealth(countryId, healthIndicators, null);
    }

    public CountriesHealthScoreDto fetchCountriesHealthScores() {
        return this.fetchCountriesHealthScores(null, null);
    }

    @Transactional
    public CountriesHealthScoreDto fetchCountriesHealthScores(Integer categoryId, Integer phase) {
        HealthIndicators healthIndicators = new HealthIndicators(iHealthIndicatorRepository.find(categoryId));

        Map<String, List<HealthIndicator>> groupByCountry = healthIndicators.groupByCountry();
        List<CountryHealthScoreDto> globalHealthScores = groupByCountry
                .entrySet().stream()
                .map(entry -> transformToCountryHealth(entry.getKey(), new HealthIndicators(entry.getValue()), phase))
                .filter(CountryHealthScoreDto::hasCategories)
                .sorted(comparing(CountryHealthScoreDto::getCountryName, nullsLast(Comparator.naturalOrder())))
                .collect(toList());
        return new CountriesHealthScoreDto(globalHealthScores);
    }

    @Transactional
    public GlobalHealthScoreDto getGlobalHealthIndicator(Integer categoryId, Integer phase) {
        CountriesHealthScoreDto countries = this.fetchCountriesHealthScores(categoryId, phase);
        List<CategoryHealthScoreDto> categories = getCategoriesInCountries(countries);
        Map<Integer, List<CategoryHealthScoreDto>> groupByCategory = categories.stream()
                .collect(groupingBy(cat -> cat.getId()));
        List<CategoryHealthScoreDto> categoryHealthScores = groupByCategory.entrySet().stream()
                .map(this::getCategoryHealthScoreDto).collect(toList());
        Integer globalPhase = convertScoreToPhase(getAverageCategoryScore(categoryHealthScores));
        return new GlobalHealthScoreDto(globalPhase, categoryHealthScores);
    }

    public void createGlobalHealthIndicatorInExcel(HttpServletRequest request,
                                                   HttpServletResponse response) throws IOException {
        excelUtilService.convertListToExcel(fetchCountriesHealthScores().getCountryHealthScores());
        excelUtilService.downloadFile(request, response);
    }

    public void createHealthIndicatorInExcelFor(String countryId, HttpServletRequest request,
                                                HttpServletResponse response) throws IOException {
        List countryHealthScoreDtoAsList = new ArrayList<CountryHealthScoreDto>();
        countryHealthScoreDtoAsList.add(fetchCountryHealthScore(countryId));
        excelUtilService.convertListToExcel(countryHealthScoreDtoAsList);
        excelUtilService.downloadFile(request, response);
    }

    private CategoryHealthScoreDto getCategoryHealthScoreDto(Entry<Integer, List<CategoryHealthScoreDto>> entry) {
        List<CategoryHealthScoreDto> categoriesHealthScore = entry.getValue();
        Double globalScore = getAverageCategoryScore(categoriesHealthScore);
        CategoryHealthScoreDto categoryHealthScoreDto = categoriesHealthScore.stream().findFirst()
                .orElse(new CategoryHealthScoreDto());
        return new CategoryHealthScoreDto(entry.getKey(), categoryHealthScoreDto.getName(), globalScore,
                convertScoreToPhase(globalScore), null);
    }

    private List<CategoryHealthScoreDto> getCategoriesInCountries(CountriesHealthScoreDto countries) {
        return countries.getCountryHealthScores()
                .stream()
                .map(CountryHealthScoreDto::getCategories)
                .flatMap(l -> l.stream())
                .collect(toList());
    }

    private Double getAverageCategoryScore(List<CategoryHealthScoreDto> categoriesHealthScore) {
        OptionalDouble optionalGlobalScore = categoriesHealthScore.stream()
                .filter(c -> !isNull(c.getOverallScore()))
                .mapToDouble(cat -> cat.getOverallScore())
                .average();
        return optionalGlobalScore.isPresent() ? optionalGlobalScore.getAsDouble() : null;
    }

    private CountryHealthScoreDto transformToCountryHealth(String countryId, HealthIndicators healthIndicators,
                                                           Integer score) {
        List<CategoryHealthScoreDto> categoryDtos = getSortedCategoriesWithIndicators(healthIndicators, score);

        Double overallScore = healthIndicators.getOverallScore();
        return new CountryHealthScoreDto(countryId, healthIndicators.getCountryName(), overallScore, categoryDtos,
                convertScoreToPhase(overallScore));
    }

    private List<CategoryHealthScoreDto> getSortedCategoriesWithIndicators(HealthIndicators healthIndicators,
                                                                           Integer phase) {
        Map<Integer, Double> nonNullCategoryScore = healthIndicators.groupByCategoryIdWithNotNullScores();
        return healthIndicators.groupByCategory()
                .entrySet()
                .stream()
                .map(entry -> {
                    Category category = entry.getKey();
                    List<HealthIndicator> indicators = entry.getValue();
                    return new CategoryHealthScoreDto(category, nonNullCategoryScore.get(category.getId()), indicators);
                })
                .filter(cat -> this.filterCategoriesWithPhase(cat, phase))
                .sorted(comparing(CategoryHealthScoreDto::getId))
                .collect(toList());
    }

    private boolean filterCategoriesWithPhase(CategoryHealthScoreDto category, Integer phase) {
        return  phase == null ? true : ofNullable(category.getPhase())
                                       .map(ph -> ph.equals(phase))
                                       .orElse(false);
    }
}