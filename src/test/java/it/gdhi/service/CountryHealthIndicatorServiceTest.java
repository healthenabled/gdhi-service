package it.gdhi.service;

import it.gdhi.dto.*;
import it.gdhi.model.*;
import it.gdhi.model.id.CountryHealthIndicatorId;
import it.gdhi.repository.ICountryHealthIndicatorRepository;
import it.gdhi.repository.ICountrySummaryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static it.gdhi.utils.FormStatus.PUBLISHED;
import static it.gdhi.utils.ListUtils.findFirst;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CountryHealthIndicatorServiceTest {

    @InjectMocks
    CountryHealthIndicatorService countryHealthIndicatorService;

    @Mock
    ICountryHealthIndicatorRepository iCountryHealthIndicatorRepository;


    @Mock
    private ExcelUtilService excelUtilService;

    @Mock
    private ICountrySummaryRepository iCountrySummaryRepository;

    private void dataSet(String countryId1, int categoryId1, int categoryId2,  int indicatorId1, int indicatorId2, int indicatorId3) {
        Integer score1 = 3;
        Integer score2 = 4;
        Integer score3 = 1;
        String status ="PUBLISHED";

        CountryHealthIndicatorId countryHealthIndicatorId1 = new CountryHealthIndicatorId(countryId1,categoryId1,indicatorId1, status);
        Country country1 = new Country("Ind", "India",UUID.randomUUID(), "IN");
        Category category1 = new Category(categoryId1, "Leadership and Governance");
        Indicator indicator1 = new Indicator(indicatorId1, "Indicator 1", "Definition1", 1);
        IndicatorScore indicatorScore1 = IndicatorScore.builder().id(1L).indicatorId(indicatorId2).score(score2).definition("score 1").build();
        CountryHealthIndicator countryHealthIndicator1 = new CountryHealthIndicator(countryHealthIndicatorId1, country1,
                category1, indicator1, indicatorScore1, score1,  "st1", new Date(), null);

        CountryHealthIndicatorId countryHealthIndicatorId2 = new CountryHealthIndicatorId(countryId1,categoryId2,indicatorId2 , status);
        Category category2 = new Category(categoryId2, "Category2");
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 2", "Definition2", 2);
        Indicator indicator3 = new Indicator(indicatorId3, "Indicator 3", "Definition3", 3);

        IndicatorScore indicatorScore2 = IndicatorScore.builder().id(1L).indicatorId(indicatorId2).score(score2).definition("score 2").build();
        CountryHealthIndicator countryHealthIndicator2 = new CountryHealthIndicator(countryHealthIndicatorId2,
                country1, category2, indicator2, indicatorScore2,  score2, "st2" ,new Date(), null);
        IndicatorScore indicatorScore3 = IndicatorScore.builder().id(2L).indicatorId(indicatorId3).score(score3).definition("score 3").build();
        CountryHealthIndicator countryHealthIndicator3 = new CountryHealthIndicator(countryHealthIndicatorId2,
                country1, category2, indicator3, indicatorScore3,  score3, "st3", new Date(), null);

        List<CountryHealthIndicator> countryHealthIndicatorsForCountry = asList(countryHealthIndicator3, countryHealthIndicator2, countryHealthIndicator1);

        when(iCountryHealthIndicatorRepository.findHealthIndicatorsFor(countryId1)).thenReturn(countryHealthIndicatorsForCountry);

    }

    @Test
    public void shouldReturnCountryHealthScoreGivenCountryIdForDifferentCategory() {
        String countryId = "IND";
        Integer categoryId1 = 1;
        Integer categoryId2 = 2;
        Integer indicatorId1 = 1;
        Integer indicatorId2 = 2;
        Integer indicatorId3 =3;

        dataSet(countryId, categoryId1, categoryId2, indicatorId1, indicatorId2, indicatorId3);
        CountryHealthScoreDto healthScoreForACountry = countryHealthIndicatorService.fetchCountryHealthScore(countryId);
        String countryName = "India";
        assertSet1(healthScoreForACountry, countryId, countryName);
    }

    private void assertSet1(CountryHealthScoreDto healthScoreForACountry, String countryId, String  countryName){
        assertThat(healthScoreForACountry.getCountryId(), is(countryId));
        assertThat(healthScoreForACountry.getCountryName(), is(countryName));
        assertThat(healthScoreForACountry.getCategories().size(), is(2));
        CategoryHealthScoreDto leadership = healthScoreForACountry.getCategories().stream().filter(a -> a.getName().equals("Leadership and Governance")).findFirst().get();
        CategoryHealthScoreDto category2 = healthScoreForACountry.getCategories().stream().filter(a -> a.getName().equals("Category2")).findFirst().get();
        assertThat(leadership.getPhase(), is(3));
        assertThat(leadership.getIndicators().size(), is(1));
        IndicatorScoreDto indicator = leadership.getIndicators().stream().filter(ind -> ind.getId().equals(1)).findFirst().get();
        assertThat(indicator.getName(), is("Indicator 1"));
        assertThat(indicator.getScore(), is(3));
        assertThat(indicator.getIndicatorDescription(), is("Definition1"));
        assertThat(indicator.getScoreDescription(), is("score 1"));
        assertThat(indicator.getSupportingText(), is("st1"));
        assertThat(category2.getPhase(), is(3));
        assertThat(category2.getIndicators().size(), is(2));
        indicator = category2.getIndicators().stream().filter(ind -> ind.getId().equals(2)).findFirst().get();
        assertThat(indicator.getName(), is("Indicator 2"));
        assertThat(indicator.getScore(), is(4));
        assertThat(indicator.getIndicatorDescription(), is("Definition2"));
        assertThat(indicator.getScoreDescription(), is("score 2"));
        assertThat(indicator.getSupportingText(), is("st2"));
        indicator = category2.getIndicators().stream().filter(ind -> ind.getId().equals(3)).findFirst().get();
        assertThat(indicator.getName(), is("Indicator 3"));
        assertThat(indicator.getScore(), is(1));
        assertThat(indicator.getIndicatorDescription(), is("Definition3"));
        assertThat(indicator.getScoreDescription(), is("score 3"));
        assertThat(indicator.getSupportingText(), is("st3"));
    }

    @Test
    public void shouldReturnCountryHealthScoreGivenCountryIdForDifferentNullIndicatorScoreForSameCategory() {
        String countryId = "IND";
        Integer categoryId1 = 1;
        Integer indicatorId1 = 1;
        Integer indicatorId2 = 2;
        String categoryName = "Leadership and Governance";
        String status = "PUBLISHED";

        String countryId1 = "IND";
        String countryName = "India";
        CountryHealthIndicatorId countryHealthIndicatorId1 = new CountryHealthIndicatorId(countryId1,categoryId1,
                indicatorId1, status);
        String countryAlpha2Code = "IN";
        Country country1 = new Country(countryId, countryName, UUID.randomUUID(), countryAlpha2Code);
        Category category1 = new Category(categoryId1, categoryName);
        Indicator indicator1 = new Indicator(indicatorId1, "Indicator 1", "Definition", 1);
        CountryHealthIndicator countryHealthIndicator1 = new CountryHealthIndicator(countryHealthIndicatorId1,
                country1, category1, indicator1, IndicatorScore.builder().build(),  null, null, new Date(), null);

        CountryHealthIndicatorId countryHealthIndicatorId2 = new CountryHealthIndicatorId(countryId1,categoryId1,indicatorId2,status);
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 1", "Definition", 2);
        Integer indicatorScore = 2;
        CountryHealthIndicator countryHealthIndicator2 = new CountryHealthIndicator(countryHealthIndicatorId2,
                country1, category1, indicator2, IndicatorScore.builder().build(),  indicatorScore, "st1",new Date(), null);

        List<CountryHealthIndicator> countryHealthIndicatorsForCountry = asList(countryHealthIndicator1, countryHealthIndicator2);

        when(iCountryHealthIndicatorRepository.findHealthIndicatorsFor(countryId1)).thenReturn(countryHealthIndicatorsForCountry);

        CountryHealthScoreDto healthScoreForACountry = countryHealthIndicatorService.fetchCountryHealthScore(countryId);
        assertSet4(healthScoreForACountry, countryId, countryName, countryAlpha2Code);
    }

    private void assertSet4(CountryHealthScoreDto healthScoreForACountry, String countryId, String  countryName,
                            String countryAlpha2Code){
        assertThat(healthScoreForACountry.getCountryId(), is(countryId));
        assertThat(healthScoreForACountry.getCountryName(), is(countryName));
        assertThat(healthScoreForACountry.getCountryAlpha2Code(), is(countryAlpha2Code));
        assertThat(healthScoreForACountry.getCategories().size(), is(1));
        List<CategoryHealthScoreDto> leadership = healthScoreForACountry.getCategories().stream().filter(a -> a.getName().equals("Leadership and Governance")).collect(toList());
        assertThat(leadership.size(), is(1));
        assertThat(leadership.get(0).getPhase(), is(2));
    }

    @Test
    public void shouldReturnCountryHealthScoreGivenCountryIdForANullCategoryScore() {
        String countryId = "IND";
        Integer categoryId1 = 1;
        Integer categoryId2 = 2;
        Integer indicatorId1 = 1;
        Integer indicatorId2 = 2;
        String categoryName = "Leadership and Governance";
        String categoryName2 = "Category2";

        String countryId1 = "IND";
        String countryName = "India";
        String status = "PUBLISHED";
        CountryHealthIndicatorId countryHealthIndicatorId1 = new CountryHealthIndicatorId(countryId1,categoryId1,indicatorId1, status);
        Country country1 = new Country(countryId, countryName,UUID.randomUUID(), "IN");
        Category category1 = new Category(categoryId1, categoryName );
        Category category2 = new Category(categoryId2, categoryName2 );
        Indicator indicator1 = new Indicator(indicatorId1, "Indicator 1", "Definition", 1);
        CountryHealthIndicator countryHealthIndicator1 = new CountryHealthIndicator(countryHealthIndicatorId1,
                country1, category1, indicator1, IndicatorScore.builder().build(),  null, "st1", new Date(), null);

        CountryHealthIndicatorId countryHealthIndicatorId2 = new CountryHealthIndicatorId(countryId1,categoryId1,indicatorId2,status);
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 1", "Definition", 2);
        CountryHealthIndicator countryHealthIndicator2 = new CountryHealthIndicator(countryHealthIndicatorId2,
                country1, category2, indicator2, IndicatorScore.builder().build(),  2, "st2", new Date(), null);

        List<CountryHealthIndicator> countryHealthIndicatorsForCountry = asList(countryHealthIndicator1, countryHealthIndicator2);

        when(iCountryHealthIndicatorRepository.findHealthIndicatorsFor(countryId1)).thenReturn(countryHealthIndicatorsForCountry);

        CountryHealthScoreDto healthScoreForACountry = countryHealthIndicatorService.fetchCountryHealthScore(countryId);
        assertSet5(healthScoreForACountry, countryId, countryName);
    }

    private void assertSet5(CountryHealthScoreDto healthScoreForACountry, String countryId, String  countryName){
        assertThat(healthScoreForACountry.getCountryId(), is(countryId));
        assertThat(healthScoreForACountry.getCountryName(), is(countryName));
        assertThat(healthScoreForACountry.getCategories().size(), is(2));
        assertThat(healthScoreForACountry.getOverallScore(), is(2.0));
        List<CategoryHealthScoreDto> leadership = healthScoreForACountry.getCategories().stream().filter(a -> a.getName().equals("Leadership and Governance")).collect(toList());
        List<CategoryHealthScoreDto> category2 = healthScoreForACountry.getCategories().stream().filter(a -> a.getName().equals("Category2")).collect(toList());
        assertThat(leadership.size(), is(1));
        assertNull(leadership.get(0).getPhase());
        assertThat(category2.size(), is(1));
        assertThat(category2.get(0).getPhase(), is(2));
    }


    @Test
    public void shouldReturnCountryHealthScoreGivenCountryIdForNullCountryScore() {
        String countryId = "IND";
        Integer categoryId1 = 1;
        Integer indicatorId1 = 1;
        Integer indicatorId2 = 2;
        String categoryName = "Leadership and Governance";

        String countryId1 = "IND";
        String countryName = "India";
        String status = "PUBLISHED";
        CountryHealthIndicatorId countryHealthIndicatorId1 = new CountryHealthIndicatorId(countryId1,categoryId1,indicatorId1,status);
        Country country1 = new Country(countryId, countryName,UUID.randomUUID(), "IN");
        Category category1 = new Category(categoryId1, categoryName );
        Indicator indicator1 = new Indicator(indicatorId1, "Indicator 1", "Definition", 1);
        CountryHealthIndicator countryHealthIndicator1 = new CountryHealthIndicator(countryHealthIndicatorId1,
                country1, category1, indicator1, IndicatorScore.builder().build(),  null, null , new Date(), null);

        CountryHealthIndicatorId countryHealthIndicatorId2 = new CountryHealthIndicatorId(countryId1,categoryId1,indicatorId2,status);
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 1", "Definition", 2);
        CountryHealthIndicator countryHealthIndicator2 = new CountryHealthIndicator(countryHealthIndicatorId2,
                country1, category1, indicator2, IndicatorScore.builder().build(),  null, null , new Date(), null);

        List<CountryHealthIndicator> countryHealthIndicatorsForCountry = asList(countryHealthIndicator1, countryHealthIndicator2);

        when(iCountryHealthIndicatorRepository.findHealthIndicatorsFor(countryId1)).thenReturn(countryHealthIndicatorsForCountry);

        CountryHealthScoreDto healthScoreForACountry = countryHealthIndicatorService.fetchCountryHealthScore(countryId);
        assertSet6(healthScoreForACountry, countryId, countryName);
    }

    private void assertSet6(CountryHealthScoreDto healthScoreForACountry, String countryId, String  countryName){
        assertThat(healthScoreForACountry.getCountryId(), is(countryId));
        assertThat(healthScoreForACountry.getCountryName(), is(countryName));
        assertThat(healthScoreForACountry.getCategories().size(), is(1));
        assertNull(healthScoreForACountry.getOverallScore());
        List<CategoryHealthScoreDto> leadership = healthScoreForACountry.getCategories().stream().filter(a -> a.getName().equals("Leadership and Governance")).collect(toList());
        assertThat(leadership.size(), is(1));
        assertNull(leadership.get(0).getPhase());
    }

    @Test
    public void shouldReturnCountryHealthScoreGivenCountryIdForSameCategory() {
        String countryId = "IND";
        Integer categoryId1 = 1;
        String categoryName = "Leadership and Governance";
        Integer indicatorId1 = 1;
        Integer indicatorId2 = 2;

        Integer indicatorScore1 = 3;
        Integer indicatorScore2 = 4;

        String countryId1 = "IND";
        String countryName = "India";
        String status = "PUBLISHED";
        CountryHealthIndicatorId countryHealthIndicatorId1 = new CountryHealthIndicatorId(countryId1,categoryId1,indicatorId1, status);
        Country country1 = new Country(countryId, countryName,UUID.randomUUID(), "IN");
        Category category1 = new Category(categoryId1, categoryName );
        Indicator indicator1 = new Indicator(indicatorId1, "Indicator 1", "Definition", 1);
        CountryHealthIndicator countryHealthIndicator1 = new CountryHealthIndicator(countryHealthIndicatorId1,
                country1, category1, indicator1, IndicatorScore.builder().build(),  indicatorScore1, "st1" , new Date(), null);

        CountryHealthIndicatorId countryHealthIndicatorId2 = new CountryHealthIndicatorId(countryId1,categoryId1,indicatorId2,status);
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 1", "Definition", 2);
        CountryHealthIndicator countryHealthIndicator2 = new CountryHealthIndicator(countryHealthIndicatorId2,
                country1, category1, indicator2, IndicatorScore.builder().build(),  indicatorScore2, "st2" , new Date(), null);

        List<CountryHealthIndicator> countryHealthIndicatorsForCountry = asList(countryHealthIndicator1, countryHealthIndicator2);

        when(iCountryHealthIndicatorRepository.findHealthIndicatorsFor(countryId1)).thenReturn(countryHealthIndicatorsForCountry);

        CountryHealthScoreDto healthScoreForACountry = countryHealthIndicatorService.fetchCountryHealthScore(countryId);
        assertSet2(healthScoreForACountry, countryId, countryName);
    }

    private void assertSet2(CountryHealthScoreDto healthScoreForACountry, String countryId, String countryName){
        assertThat(healthScoreForACountry.getCountryId(), is(countryId));
        assertThat(healthScoreForACountry.getCountryName(), is(countryName));
        assertThat(healthScoreForACountry.getCategories().size(), is(1));
        assertThat(healthScoreForACountry.getCountryPhase(), is(4));
        assertThat(healthScoreForACountry.getOverallScore(), is(3.5));
        List<CategoryHealthScoreDto> leadership = healthScoreForACountry.getCategories().stream().filter(a -> a.getName().equals("Leadership and Governance")).collect(toList());
        assertThat(leadership.size(), is(1));
        assertThat(leadership.get(0).getPhase(), is(4));

    }

    @Test
    public void shouldReturnCountryHealthScoreGivenCountryIdForSameCategoryCheckingSinglePrecision() {
        String countryId = "IND";
        Integer categoryId1 = 1;
        String categoryName = "Leadership and Governance";
        Integer indicatorId1 = 1;
        Integer indicatorId2 = 2;
        Integer indicatorId3 = 3;

        Integer indicatorScore1 = 5;
        Integer indicatorScore2 = 0;
        Integer indicatorScore3 = 0;

        String countryId1 = "IND";
        String countryName = "India";
        String status = "PUBLISHED";
        CountryHealthIndicatorId countryHealthIndicatorId1 = new CountryHealthIndicatorId(countryId1,categoryId1,indicatorId1, status);
        Country country1 = new Country(countryId, countryName,UUID.randomUUID(), "IN");
        Category category1 = new Category(categoryId1, categoryName );
        Indicator indicator1 = new Indicator(indicatorId1, "Indicator 1", "Definition", 1);
        CountryHealthIndicator countryHealthIndicator1 = new CountryHealthIndicator(countryHealthIndicatorId1,
                country1, category1, indicator1, IndicatorScore.builder().build(),  indicatorScore1, "st1" , new Date(), null);

        CountryHealthIndicatorId countryHealthIndicatorId2 = new CountryHealthIndicatorId(countryId1,categoryId1,indicatorId2, status);
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 1", "Definition", 2);
        CountryHealthIndicator countryHealthIndicator2 = new CountryHealthIndicator(countryHealthIndicatorId2,
                country1, category1, indicator2, IndicatorScore.builder().build(),  indicatorScore2, "st2" , new Date(), null);

        CountryHealthIndicatorId countryHealthIndicatorId3 = new CountryHealthIndicatorId(countryId1,categoryId1,indicatorId3, status);
        Indicator indicator3 = new Indicator(indicatorId3, "Indicator 1", "Definition", 3);
        CountryHealthIndicator countryHealthIndicator3 = new CountryHealthIndicator(countryHealthIndicatorId3,
                country1, category1, indicator3, IndicatorScore.builder().build(),  indicatorScore3, "st3" , new Date(), null);

        List<CountryHealthIndicator> countryHealthIndicatorsForCountry = asList(countryHealthIndicator1, countryHealthIndicator2, countryHealthIndicator3);

        when(iCountryHealthIndicatorRepository.findHealthIndicatorsFor(countryId1)).thenReturn(countryHealthIndicatorsForCountry);

        CountryHealthScoreDto healthScoreForACountry = countryHealthIndicatorService.fetchCountryHealthScore(countryId);
        assertSet3(healthScoreForACountry, countryId, countryName);
    }


    private void assertSet3(CountryHealthScoreDto healthScoreForACountry, String countryId, String countryName){
        assertThat(healthScoreForACountry.getCountryId(), is(countryId));
        assertThat(healthScoreForACountry.getCountryName(), is(countryName));
        assertThat(healthScoreForACountry.getCategories().size(), is(1));
        assertThat(healthScoreForACountry.getCountryPhase(), is(2));
        assertThat(healthScoreForACountry.getOverallScore(), is(1.6666666666666667));
        List<CategoryHealthScoreDto> leadership = healthScoreForACountry.getCategories().stream().filter(a -> a.getName().equals("Leadership and Governance")).collect(toList());
        assertThat(leadership.size(), is(1));
        assertThat(leadership.get(0).getPhase(), is(2));

    }

    @Test
    public void shouldFetchGlobalHealthScores() throws Exception {
        Category category1 = Category.builder().id(9).name("Category 1").build();
        Country country1 = new Country("IND", "India",UUID.randomUUID(), "IN");
        Indicator indicator1 = Indicator.builder().indicatorId(1).rank(1).build();
        CountryHealthIndicator mock1 = CountryHealthIndicator.builder().country(country1).category(category1).indicator(indicator1).score(1).build();

        Category category2 = Category.builder().id(8).name("Category 2").build();
        Country country2 = new Country("USA", "United States",UUID.randomUUID(),"US");
        Indicator indicator2 = Indicator.builder().indicatorId(2).rank(2).build();
        CountryHealthIndicator mock2 = CountryHealthIndicator.builder().country(country2).category(category2).indicator(indicator2).score(2).build();

        Category category3 = Category.builder().id(7).name("Category 4").build();
        Indicator indicator3 = Indicator.builder().indicatorId(3).rank(3).build();
        CountryHealthIndicator mock3 = CountryHealthIndicator.builder().country(country2).category(category3).indicator(indicator3).score(3).build();

        Indicator indicator4 = Indicator.builder().indicatorId(4).rank(4).build();
        CountryHealthIndicator mock4 = CountryHealthIndicator.builder().country(country2).category(category3).indicator(indicator4).score(4).build();
        when(iCountryHealthIndicatorRepository.findByStatus(null,PUBLISHED.name())).thenReturn(asList(mock1, mock2, mock3, mock4));

        CountrySummary countrySummary = CountrySummary.builder().collectedDate(new SimpleDateFormat("yyyy-MM-dd").parse("2018-04-04")).build();
        when(iCountrySummaryRepository.findByCountryAndStatus(anyString(),anyString())).thenReturn(countrySummary);


        CountriesHealthScoreDto countriesHealthScoreDto = countryHealthIndicatorService.fetchCountriesHealthScores(null, null);

        assertThat(countriesHealthScoreDto.getCountryHealthScores().size(), is(2));
        CountryHealthScoreDto actualInd = countriesHealthScoreDto.getCountryHealthScores().stream().filter(c -> c.getCountryId().equals("IND")).findFirst().get();
        CountryHealthScoreDto actualUSA = countriesHealthScoreDto.getCountryHealthScores().stream().filter(c -> c.getCountryId().equals("USA")).findFirst().get();
        assertThat(actualInd.getOverallScore(), is(1.0));
        assertThat(actualUSA.getOverallScore(), is(2.75));
        assertThat(actualUSA.getCategories().size(), is(2));
        CategoryHealthScoreDto actualCategory3 = actualUSA.getCategories().stream().filter(c -> c.getId().equals(category3.getId())).findFirst().get();
        assertThat(actualCategory3.getOverallScore(), is(3.5));
        assertThat(actualCategory3.getIndicators().size(), is(2));
        CategoryHealthScoreDto actualCategory2 = actualUSA.getCategories().stream().filter(c -> c.getId().equals(category2.getId())).findFirst().get();
        assertThat(actualCategory2.getOverallScore(), is(2.0));
        assertThat(actualInd.getCollectedDate(), is("April 2018"));
    }

    @Test
    public void shouldReturnEmptyListWhenPhaseForGivenCategoryIsNotPresentWhileFetchingGlobalHealthScores() throws Exception {
        String USA = "USA";
        String India = "IND";

        Category category1 = Category.builder().id(9).name("Category 1").build();
        Country country1 = new Country(India, "India",UUID.randomUUID(), "IN");
        Indicator indicator1 = Indicator.builder().indicatorId(1).build();
        CountryHealthIndicator mock1 = CountryHealthIndicator.builder().country(country1).category(category1).indicator(indicator1).score(1).build();

        Category category2 = Category.builder().id(8).name("Category 2").build();
        Country country2 = new Country(USA, "United States",UUID.randomUUID(), "US");
        Indicator indicator2 = Indicator.builder().indicatorId(2).build();
        CountryHealthIndicator mock2 = CountryHealthIndicator.builder().country(country2).category(category2).indicator(indicator2).score(2).build();

        when(iCountryHealthIndicatorRepository.findByStatus(1,PUBLISHED.name())).thenReturn(asList(mock1, mock2));

        CountrySummary countrySummary = CountrySummary.builder().collectedDate(new SimpleDateFormat("yyyy-MM-dd").parse("2018-04-04")).build();
        when(iCountrySummaryRepository.findByCountryAndStatus(anyString(),anyString())).thenReturn(countrySummary);


        CountriesHealthScoreDto countriesHealthScoreDto = countryHealthIndicatorService.fetchCountriesHealthScores(1, 5);

        assertThat(countriesHealthScoreDto.getCountryHealthScores().size(), is(0));
    }

    @Test
    public void shouldFetchIndicatorsBasedOnFilters() throws Exception {
        Category category3 = Category.builder().id(7).name("Category 4").build();
        Country country1 = new Country("Ind", "India",UUID.randomUUID(), "IN");
        Indicator indicator1 = Indicator.builder().indicatorId(1).rank(1).build();
        CountryHealthIndicator mock1 = CountryHealthIndicator.builder().country(country1).category(category3).indicator(indicator1).score(1).build();

        Country country2 = new Country("USA", "United States",UUID.randomUUID(), "US");

        Indicator indicator3 = Indicator.builder().indicatorId(3).rank(3).build();
        CountryHealthIndicator mock3 = CountryHealthIndicator.builder().country(country2).category(category3).indicator(indicator3).score(3).build();

        Indicator indicator4 = Indicator.builder().indicatorId(4).rank(4).build();
        CountryHealthIndicator mock4 = CountryHealthIndicator.builder().country(country2).category(category3).indicator(indicator4).score(4).build();
        when(iCountryHealthIndicatorRepository.findByStatus(3,PUBLISHED.name())).thenReturn(asList(mock1, mock3, mock4));

        CountrySummary countrySummary = CountrySummary.builder().collectedDate(new SimpleDateFormat("yyyy-MM-dd").parse("2018-04-04")).build();
        when(iCountrySummaryRepository.findByCountryAndStatus(anyString(),anyString())).thenReturn(countrySummary);


        CountriesHealthScoreDto countriesHealthScoreDto = countryHealthIndicatorService.fetchCountriesHealthScores(3, 4);

        assertThat(countriesHealthScoreDto.getCountryHealthScores().size(), is(1));
        assertThat(countriesHealthScoreDto.getCountryHealthScores().get(0).getOverallScore(), is(3.5));
        assertThat(countriesHealthScoreDto.getCountryHealthScores().get(0).getCategories().size(), is(1));
        assertThat(countriesHealthScoreDto.getCountryHealthScores().get(0).getCategories().get(0).getOverallScore(), is(3.5));
        assertThat(countriesHealthScoreDto.getCountryHealthScores().get(0).getCategories().get(0).getPhase(), is(4));
    }

    @Test
    public void shouldFilterAtCountryLevelIfCategoryIsNull() throws Exception {
        Country country1 = new Country("Ind", "India",UUID.randomUUID(), "IN");
        Country country2 = new Country("USA", "United States",UUID.randomUUID(), "US");
        Category category2 = Category.builder().id(6).name("Category 4").build();
        Category category3 = Category.builder().id(7).name("Category 4").build();
        Indicator indicator1 = Indicator.builder().indicatorId(1).rank(1).build();
        Indicator indicator3 = Indicator.builder().indicatorId(3).rank(3).build();
        Indicator indicator4 = Indicator.builder().indicatorId(4).rank(4).build();
        Indicator indicator5 = Indicator.builder().indicatorId(5).rank(5).build();

        CountryHealthIndicator mock1 = CountryHealthIndicator.builder().country(country1).category(category3).indicator(indicator1).score(1).build();
        CountryHealthIndicator mock2 = CountryHealthIndicator.builder().country(country2).category(category3).indicator(indicator4).score(2).build();
        CountryHealthIndicator mock3 = CountryHealthIndicator.builder().country(country2).category(category3).indicator(indicator3).score(3).build();
        CountryHealthIndicator mock4 = CountryHealthIndicator.builder().country(country2).category(category2).indicator(indicator5).score(5).build();
        when(iCountryHealthIndicatorRepository.findByStatus(null, PUBLISHED.name())).thenReturn(asList(mock1, mock2, mock3, mock4));

        CountrySummary countrySummary = CountrySummary.builder().collectedDate(new SimpleDateFormat("yyyy-MM-dd").parse("2018-04-04")).build();
        when(iCountrySummaryRepository.findByCountryAndStatus(anyString(),anyString())).thenReturn(countrySummary);


        CountriesHealthScoreDto countriesHealthScoreDto = countryHealthIndicatorService.fetchCountriesHealthScores(null, 4);

        assertThat(countriesHealthScoreDto.getCountryHealthScores().size(), is(1));
        assertThat(countriesHealthScoreDto.getCountryHealthScores().get(0).getOverallScore(), is(3.75));
        List<CategoryHealthScoreDto> categories = countriesHealthScoreDto.getCountryHealthScores().get(0).getCategories();
        assertThat(categories.size(), is(2));
        assertThat(findFirst(categories, c -> c.getId().equals(category3.getId())).getOverallScore(), is(2.5));
        assertThat(findFirst(categories, c -> c.getId().equals(category3.getId())).getPhase(), is(3));
        assertThat(findFirst(categories, c -> c.getId().equals(category2.getId())).getOverallScore(), is(5.0));
        assertThat(findFirst(categories, c -> c.getId().equals(category2.getId())).getPhase(), is(5));
    }

    @Test
    public void shouldFetchOverAllCategoriesWithScore() {
        Category category = Category.builder().id(9).name("Category 1").build();
        Category category1 = Category.builder().id(3).name("Category 2").build();
        CountryHealthIndicator countryHealthIndicator = CountryHealthIndicator.builder()
                .country(new Country("Ind", "India",UUID.randomUUID(), "IN"))
                .indicator(Indicator.builder().indicatorId(1).build())
                .category(category).score(5).build();

        CountryHealthIndicator countryHealthIndicator1 = CountryHealthIndicator.builder()
                .country(new Country("Ind", "India",UUID.randomUUID(), "IN"))
                .indicator(Indicator.builder().indicatorId(1).build())
                .category(category1).score(2).build();

        CountryHealthIndicator countryHealthIndicator2 = CountryHealthIndicator.builder()
                .country(new Country("USA", "USA",UUID.randomUUID(), "US"))
                .indicator(Indicator.builder().indicatorId(1).build())
                .category(category).score(2).build();

        CountryHealthIndicator countryHealthIndicator3 = CountryHealthIndicator.builder()
                .country(new Country("USA", "USA",UUID.randomUUID(), "US"))
                .indicator(Indicator.builder().indicatorId(1).build())
                .category(category1).score(null).build();

        CountryHealthIndicator countryHealthIndicator4 = CountryHealthIndicator.builder()
                .country(new Country("UK", "UK",UUID.randomUUID(),"UK"))
                .indicator(Indicator.builder().indicatorId(1).build())
                .category(category1).score(0).build();

        List<CountryHealthIndicator> countryHealthIndicators = asList(countryHealthIndicator, countryHealthIndicator1, countryHealthIndicator2, countryHealthIndicator3, countryHealthIndicator4);
        when(iCountryHealthIndicatorRepository.findByStatus(null,PUBLISHED.name())).thenReturn(countryHealthIndicators);

        GlobalHealthScoreDto globalHealthIndicator = countryHealthIndicatorService.getGlobalHealthIndicator(null, null);

        assertEquals(2,globalHealthIndicator.getCategories().size());
        CategoryHealthScoreDto actualCategory = globalHealthIndicator.getCategories().stream().filter(cat -> cat.getId().equals(category.getId())).findFirst().get();
        assertEquals(4, actualCategory.getPhase().intValue());
        actualCategory = globalHealthIndicator.getCategories().stream().filter(cat -> cat.getId().equals(category1.getId())).findFirst().get();
        assertEquals(1, actualCategory.getPhase().intValue());
        assertThat(globalHealthIndicator.getOverAllScore(), is(3));
    }

    @Test
    public void fetchGlobalHealthScoresShouldIgnoreCountryWithAllIndicatorsMissing() {
        CountryHealthIndicator mock1 = mock(CountryHealthIndicator.class);
        when(mock1.getScore()).thenReturn(2);
        Country mockCountry1 = mock(Country.class);
        Category mock = mock(Category.class);

        when(mock1.getCountryId()).thenReturn("IND");
        when(mock1.getCountry()).thenReturn(mockCountry1);
        when(mockCountry1.getId()).thenReturn("IND");
        when(mock1.getCategory()).thenReturn(mock);
        when(mock1.getCategory().getId()).thenReturn(9);
        when(mock1.getCategory().getName()).thenReturn("Category 1");

        CountryHealthIndicator mock2 = mock(CountryHealthIndicator.class);
        when(mock2.getScore()).thenReturn(null);
        Country mockCountry2 = mock(Country.class);

        when(mock2.getCountry()).thenReturn(mockCountry2);
        when(mock2.getCountryId()).thenReturn("USA");
        when(mockCountry2.getId()).thenReturn("USA");
        when(mock2.getCategory()).thenReturn(mock);
        when(mock2.getCategory().getId()).thenReturn(9);
        when(mock2.getCategory().getName()).thenReturn("Category 1");

        CountryHealthIndicator mock3 = mock(CountryHealthIndicator.class);
        when(mock3.getScore()).thenReturn(null);

        when(mock3.getCountry()).thenReturn(mockCountry2);
        when(mock3.getCountryId()).thenReturn("USA");
        when(mockCountry2.getId()).thenReturn("USA");
        when(mock3.getCategory()).thenReturn(mock);
        when(mock3.getCategory().getId()).thenReturn(8);
        when(mock3.getCategory().getName()).thenReturn("Category 2");


        List<CountryHealthIndicator> countryHealthIndicators = new ArrayList<>();
        countryHealthIndicators.add(mock1);
        countryHealthIndicators.add(mock2);

        when(iCountryHealthIndicatorRepository.findByStatus(1,PUBLISHED.name())).thenReturn(countryHealthIndicators);
        when(iCountryHealthIndicatorRepository.findCountriesWithHealthScores()).thenReturn(asList("IND", "USA"));
        when(iCountryHealthIndicatorRepository.findHealthIndicatorsFor("IND")).thenReturn(asList(mock1));
        when(iCountryHealthIndicatorRepository.findHealthIndicatorsFor("USA")).thenReturn(asList(mock2, mock3));

        GlobalHealthScoreDto globalHealthIndicator = countryHealthIndicatorService.getGlobalHealthIndicator(1, 2);

        assertEquals(1,globalHealthIndicator.getCategories().size());
        assertThat(globalHealthIndicator.getOverAllScore(), is(2));
    }

    @Test
    public void shouldConsiderOnlyCategoriesWithGivenPhase() throws Exception {
        Category category = Category.builder().id(9).name("Category 1").build();

        CountryHealthIndicator countryHealthIndicator = CountryHealthIndicator.builder()
                .country(new Country("Ind", "India",UUID.randomUUID(),"IN"))
                .countryHealthIndicatorId(
                        new CountryHealthIndicatorId("Ind", category.getId(),1, PUBLISHED.name()))
                .indicator(Indicator.builder().indicatorId(1).rank(1).build())
                .category(category).score(5).build();

        CountryHealthIndicator countryHealthIndicator1 = CountryHealthIndicator.builder()
                .country(new Country("Ind", "India",UUID.randomUUID(),"IN"))
                .countryHealthIndicatorId(
                        new CountryHealthIndicatorId("Ind", category.getId(),1, PUBLISHED.name()))
                .indicator(Indicator.builder().indicatorId(2).rank(2).build())
                .category(category).score(2).build();

        CountryHealthIndicator countryHealthIndicator2 = CountryHealthIndicator.builder()
                .country(new Country("USA", "USA",UUID.randomUUID(),"US"))
                .countryHealthIndicatorId(
                        new CountryHealthIndicatorId("USA", category.getId(),1, PUBLISHED.name()))
                .indicator(Indicator.builder().indicatorId(1).rank(3).build())
                .category(category).score(2).build();

        CountryHealthIndicator countryHealthIndicator3 = CountryHealthIndicator.builder()
                .country(new Country("USA", "USA",UUID.randomUUID(), "US"))
                .countryHealthIndicatorId(
                        new CountryHealthIndicatorId("USA", category.getId(),1, PUBLISHED.name()))
                .indicator(Indicator.builder().indicatorId(2).rank(4).build())
                .category(category).score(1).build();

        CountryHealthIndicator countryHealthIndicator4 = CountryHealthIndicator.builder()
                .country(new Country("USA", "USA",UUID.randomUUID(), "US"))
                .countryHealthIndicatorId(
                        new CountryHealthIndicatorId("USA", category.getId(),1, PUBLISHED.name()))
                .indicator(Indicator.builder().indicatorId(3).rank(5).build())
                .category(category).score(null).build();
        when(iCountryHealthIndicatorRepository.findByStatus(category.getId(),PUBLISHED.name())).thenReturn(asList(countryHealthIndicator, countryHealthIndicator1, countryHealthIndicator2, countryHealthIndicator3, countryHealthIndicator4));

        GlobalHealthScoreDto globalHealthIndicator = countryHealthIndicatorService.getGlobalHealthIndicator(category.getId(), 2);

        assertEquals(2, globalHealthIndicator.getOverAllScore().intValue());
        assertEquals(1, globalHealthIndicator.getCategories().size());
        assertEquals(1.5, globalHealthIndicator.getCategories().get(0).getOverallScore(), 0.01);
        assertEquals(2, globalHealthIndicator.getCategories().get(0).getPhase().intValue());
    }

    @Test
    public void shouldInvokeConvertExcelOnGlobalExport() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(iCountryHealthIndicatorRepository.findCountriesWithHealthScores()).thenReturn(asList());
        countryHealthIndicatorService.createGlobalHealthIndicatorInExcel(request, response);
        verify(excelUtilService).convertListToExcel(anyList());
        verify(excelUtilService).downloadFile(request, response);
    }

    @Test
    public void shouldInvokeConvertExcelOnExportOfSingleCountry() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(iCountryHealthIndicatorRepository.findCountriesWithHealthScores()).thenReturn(asList());
        countryHealthIndicatorService.createHealthIndicatorInExcelFor("IND", request, response);
        verify(excelUtilService).convertListToExcel(anyList());
        verify(excelUtilService).downloadFile(request, response);
    }



}