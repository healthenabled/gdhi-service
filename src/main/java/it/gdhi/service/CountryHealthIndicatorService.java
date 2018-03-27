package it.gdhi.service;

import it.gdhi.dto.CategoryHealthScoreDto;
import it.gdhi.dto.CountriesHealthScoreDto;
import it.gdhi.dto.CountryHealthScoreDto;
import it.gdhi.dto.GlobalHealthScoreDto;
import it.gdhi.model.Category;
import it.gdhi.model.CountryHealthIndicator;
import it.gdhi.model.CountryHealthIndicators;
import it.gdhi.repository.ICountryHealthIndicatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Predicate;

import static it.gdhi.controller.strategy.FilterStrategy.getCategoryPhaseFilter;
import static it.gdhi.controller.strategy.FilterStrategy.getCountryPhaseFilter;
import static it.gdhi.utils.ScoreUtils.convertScoreToPhase;
import static java.util.Comparator.comparing;
import static java.util.Comparator.nullsLast;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
public class CountryHealthIndicatorService {

    @Autowired
    private ICountryHealthIndicatorRepository iCountryHealthIndicatorRepository;

    @Autowired
    private ExcelUtilService excelUtilService;

    @Transactional
    public CountryHealthScoreDto fetchCountryHealthScore(String countryId) {
        CountryHealthIndicators countryHealthIndicators = new CountryHealthIndicators(iCountryHealthIndicatorRepository
                .findHealthIndicatorsFor(countryId));
        return constructCountryHealthScore(countryId, countryHealthIndicators,
                getCategoryPhaseFilter(null, null));
    }

    public CountriesHealthScoreDto fetchCountriesHealthScores() {
        return
                this.fetchCountriesHealthScores(null, null);
    }

    @Transactional
    public CountriesHealthScoreDto fetchCountriesHealthScores(Integer categoryId, Integer phase) {
        CountryHealthIndicators countryHealthIndicators =
                new CountryHealthIndicators(iCountryHealthIndicatorRepository.find(categoryId));

        Map<String, List<CountryHealthIndicator>> groupByCountry = countryHealthIndicators.groupByCountry();
        List<CountryHealthScoreDto> globalHealthScores = groupByCountry
                .entrySet()
                .stream()
                .map(entry -> constructCountryHealthScore(entry.getKey(), new CountryHealthIndicators(entry.getValue()),
                                                          getCategoryPhaseFilter(categoryId, phase)))
                .filter(getCountryPhaseFilter(categoryId, phase))
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
                .collect(groupingBy(CategoryHealthScoreDto::getId));
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
                .flatMap(Collection::stream)
                .collect(toList());
    }

    private Double getAverageCategoryScore(List<CategoryHealthScoreDto> categoriesHealthScore) {
        OptionalDouble optionalGlobalScore = categoriesHealthScore.stream()
                .filter(c -> !isNull(c.getOverallScore()))
                .mapToDouble(CategoryHealthScoreDto::getOverallScore)
                .average();
        return optionalGlobalScore.isPresent() ? optionalGlobalScore.getAsDouble() : null;
    }

    private CountryHealthScoreDto constructCountryHealthScore(String countryId,
                                                              CountryHealthIndicators countryHealthIndicators,
                                                              Predicate<? super CategoryHealthScoreDto> phaseFilter) {
        List<CategoryHealthScoreDto> categoryDtos = getCategoriesWithIndicators(countryHealthIndicators, phaseFilter);
        Double overallScore = countryHealthIndicators.getOverallScore();
        return new CountryHealthScoreDto(countryId, countryHealthIndicators.getCountryName(),
                overallScore, categoryDtos, convertScoreToPhase(overallScore));
    }

    private List<CategoryHealthScoreDto> getCategoriesWithIndicators(CountryHealthIndicators countryHealthIndicators,
                                                                     Predicate<? super CategoryHealthScoreDto>
                                                                             phaseFilter) {
        Map<Integer, Double> nonNullCategoryScore = countryHealthIndicators.groupByCategoryIdWithNotNullScores();
        return countryHealthIndicators.groupByCategory()
                .entrySet()
                .stream()
                .map(entry -> {
                    Category category = entry.getKey();
                    List<CountryHealthIndicator> indicators = entry.getValue();
                    return new CategoryHealthScoreDto(category, nonNullCategoryScore.get(category.getId()), indicators);
                })
                .filter(phaseFilter)
                .sorted(comparing(CategoryHealthScoreDto::getId))
                .collect(toList());
    }

}