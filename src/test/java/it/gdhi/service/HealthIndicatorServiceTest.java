package it.gdhi.service;

import it.gdhi.dto.*;
import it.gdhi.model.*;
import it.gdhi.model.id.HealthIndicatorId;
import it.gdhi.repository.IHealthIndicatorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HealthIndicatorServiceTest {

    @InjectMocks
    HealthIndicatorService healthIndicatorService;

    @Mock
    IHealthIndicatorRepository iHealthIndicatorRepository;

    @Mock
    private ExcelUtilService excelUtilService;

    private void dataSet(String countryId1, int categoryId1, int categoryId2,  int indicatorId1, int indicatorId2, int indicatorId3) {
        Integer score1 = 3;
        Integer score2 = 4;
        Integer score3 = 1;

        HealthIndicatorId healthIndicatorId1 = new HealthIndicatorId(countryId1,categoryId1,indicatorId1);
        Country country1 = new Country("IND", "India");
        Category category1 = new Category(categoryId1, "Leadership and Governance");
        Indicator indicator1 = new Indicator(indicatorId1, "Indicator 1", "Definition1");
        IndicatorScore indicatorScore1 = IndicatorScore.builder().id(1l).indicatorId(indicatorId2).score(score2).definition("score 1").build();
        HealthIndicator healthIndicator1 = new HealthIndicator(healthIndicatorId1, country1, category1, indicator1, indicatorScore1, score1,  "st1");

        HealthIndicatorId healthIndicatorId2 = new HealthIndicatorId(countryId1,categoryId2,indicatorId2);
        Category category2 = new Category(categoryId2, "Category2");
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 2", "Definition2");
        Indicator indicator3 = new Indicator(indicatorId3, "Indicator 3", "Definition3");

        IndicatorScore indicatorScore2 = IndicatorScore.builder().id(1l).indicatorId(indicatorId2).score(score2).definition("score 2").build();
        HealthIndicator healthIndicator2 = new HealthIndicator(healthIndicatorId2, country1, category2, indicator2, indicatorScore2,  score2, "st2" );
        IndicatorScore indicatorScore3 = IndicatorScore.builder().id(2l).indicatorId(indicatorId3).score(score3).definition("score 3").build();
        HealthIndicator healthIndicator3 = new HealthIndicator(healthIndicatorId2, country1, category2, indicator3, indicatorScore3,  score3, "st3" );

        List<HealthIndicator> healthIndicatorsForCountry = asList(healthIndicator3, healthIndicator2, healthIndicator1);

        when(iHealthIndicatorRepository.findHealthIndicatorsFor(countryId1)).thenReturn(healthIndicatorsForCountry);

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
        CountryHealthScoreDto healthScoreForACountry = healthIndicatorService.fetchCountryHealthScore(countryId);
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

        String countryId1 = "IND";
        String countryName = "India";
        HealthIndicatorId healthIndicatorId1 = new HealthIndicatorId(countryId1,categoryId1,indicatorId1);
        Country country1 = new Country(countryId, countryName);
        Category category1 = new Category(categoryId1, categoryName);
        Indicator indicator1 = new Indicator(indicatorId1, "Indicator 1", "Definition");
        HealthIndicator healthIndicator1 = new HealthIndicator(healthIndicatorId1, country1, category1, indicator1, IndicatorScore.builder().build(),  null, null);

        HealthIndicatorId healthIndicatorId2 = new HealthIndicatorId(countryId1,categoryId1,indicatorId2);
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 1", "Definition");
        Integer indicatorScore = 2;
        HealthIndicator healthIndicator2 = new HealthIndicator(healthIndicatorId2, country1, category1, indicator2, IndicatorScore.builder().build(),  indicatorScore, "st1");

        List<HealthIndicator> healthIndicatorsForCountry = asList(healthIndicator1, healthIndicator2);

        when(iHealthIndicatorRepository.findHealthIndicatorsFor(countryId1)).thenReturn(healthIndicatorsForCountry);

        CountryHealthScoreDto healthScoreForACountry = healthIndicatorService.fetchCountryHealthScore(countryId);
        assertSet4(healthScoreForACountry, countryId, countryName);
    }

    private void assertSet4(CountryHealthScoreDto healthScoreForACountry, String countryId, String  countryName){
        assertThat(healthScoreForACountry.getCountryId(), is(countryId));
        assertThat(healthScoreForACountry.getCountryName(), is(countryName));
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
        HealthIndicatorId healthIndicatorId1 = new HealthIndicatorId(countryId1,categoryId1,indicatorId1);
        Country country1 = new Country(countryId, countryName);
        Category category1 = new Category(categoryId1, categoryName );
        Category category2 = new Category(categoryId2, categoryName2 );
        Indicator indicator1 = new Indicator(indicatorId1, "Indicator 1", "Definition");
        HealthIndicator healthIndicator1 = new HealthIndicator(healthIndicatorId1, country1, category1, indicator1, IndicatorScore.builder().build(),  null, "st1");

        HealthIndicatorId healthIndicatorId2 = new HealthIndicatorId(countryId1,categoryId1,indicatorId2);
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 1", "Definition");
        HealthIndicator healthIndicator2 = new HealthIndicator(healthIndicatorId2, country1, category2, indicator2, IndicatorScore.builder().build(),  2, "st2");

        List<HealthIndicator> healthIndicatorsForCountry = asList(healthIndicator1, healthIndicator2);

        when(iHealthIndicatorRepository.findHealthIndicatorsFor(countryId1)).thenReturn(healthIndicatorsForCountry);

        CountryHealthScoreDto healthScoreForACountry = healthIndicatorService.fetchCountryHealthScore(countryId);
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
        HealthIndicatorId healthIndicatorId1 = new HealthIndicatorId(countryId1,categoryId1,indicatorId1);
        Country country1 = new Country(countryId, countryName);
        Category category1 = new Category(categoryId1, categoryName );
        Indicator indicator1 = new Indicator(indicatorId1, "Indicator 1", "Definition");
        HealthIndicator healthIndicator1 = new HealthIndicator(healthIndicatorId1, country1, category1, indicator1, IndicatorScore.builder().build(),  null, null );

        HealthIndicatorId healthIndicatorId2 = new HealthIndicatorId(countryId1,categoryId1,indicatorId2);
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 1", "Definition");
        HealthIndicator healthIndicator2 = new HealthIndicator(healthIndicatorId2, country1, category1, indicator2, IndicatorScore.builder().build(),  null, null );

        List<HealthIndicator> healthIndicatorsForCountry = asList(healthIndicator1, healthIndicator2);

        when(iHealthIndicatorRepository.findHealthIndicatorsFor(countryId1)).thenReturn(healthIndicatorsForCountry);

        CountryHealthScoreDto healthScoreForACountry = healthIndicatorService.fetchCountryHealthScore(countryId);
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
        HealthIndicatorId healthIndicatorId1 = new HealthIndicatorId(countryId1,categoryId1,indicatorId1);
        Country country1 = new Country(countryId, countryName);
        Category category1 = new Category(categoryId1, categoryName );
        Indicator indicator1 = new Indicator(indicatorId1, "Indicator 1", "Definition");
        HealthIndicator healthIndicator1 = new HealthIndicator(healthIndicatorId1, country1, category1, indicator1, IndicatorScore.builder().build(),  indicatorScore1, "st1" );

        HealthIndicatorId healthIndicatorId2 = new HealthIndicatorId(countryId1,categoryId1,indicatorId2);
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 1", "Definition");
        HealthIndicator healthIndicator2 = new HealthIndicator(healthIndicatorId2, country1, category1, indicator2, IndicatorScore.builder().build(),  indicatorScore2, "st2" );

        List<HealthIndicator> healthIndicatorsForCountry = asList(healthIndicator1, healthIndicator2);

        when(iHealthIndicatorRepository.findHealthIndicatorsFor(countryId1)).thenReturn(healthIndicatorsForCountry);

        CountryHealthScoreDto healthScoreForACountry = healthIndicatorService.fetchCountryHealthScore(countryId);
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
        HealthIndicatorId healthIndicatorId1 = new HealthIndicatorId(countryId1,categoryId1,indicatorId1);
        Country country1 = new Country(countryId, countryName);
        Category category1 = new Category(categoryId1, categoryName );
        Indicator indicator1 = new Indicator(indicatorId1, "Indicator 1", "Definition");
        HealthIndicator healthIndicator1 = new HealthIndicator(healthIndicatorId1, country1, category1, indicator1, IndicatorScore.builder().build(),  indicatorScore1, "st1" );

        HealthIndicatorId healthIndicatorId2 = new HealthIndicatorId(countryId1,categoryId1,indicatorId2);
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 1", "Definition");
        HealthIndicator healthIndicator2 = new HealthIndicator(healthIndicatorId2, country1, category1, indicator2, IndicatorScore.builder().build(),  indicatorScore2, "st2" );

        HealthIndicatorId healthIndicatorId3 = new HealthIndicatorId(countryId1,categoryId1,indicatorId3);
        Indicator indicator3 = new Indicator(indicatorId3, "Indicator 1", "Definition");
        HealthIndicator healthIndicator3 = new HealthIndicator(healthIndicatorId3, country1, category1, indicator3, IndicatorScore.builder().build(),  indicatorScore3, "st3" );

        List<HealthIndicator> healthIndicatorsForCountry = asList(healthIndicator1, healthIndicator2, healthIndicator3);

        when(iHealthIndicatorRepository.findHealthIndicatorsFor(countryId1)).thenReturn(healthIndicatorsForCountry);

        CountryHealthScoreDto healthScoreForACountry = healthIndicatorService.fetchCountryHealthScore(countryId);
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
    public void shouldFetchGlobalHealthScores() {
        String USA = "USA";
        String India = "IND";

        Category category1 = Category.builder().id(9).name("Category 1").build();
        Country country1 = new Country("IND", "India");
        Indicator indicator1 = Indicator.builder().indicatorId(1).build();
        HealthIndicator mock1 = HealthIndicator.builder().country(country1).category(category1).indicator(indicator1).score(1).build();

        Category category2 = Category.builder().id(8).name("Category 2").build();
        Country country2 = new Country("USA", "United States");
        Indicator indicator2 = Indicator.builder().indicatorId(2).build();
        HealthIndicator mock2 = HealthIndicator.builder().country(country2).category(category2).indicator(indicator2).score(2).build();

        Category category3 = Category.builder().id(7).name("Category 4").build();
        Indicator indicator3 = Indicator.builder().indicatorId(3).build();
        HealthIndicator mock3 = HealthIndicator.builder().country(country2).category(category3).indicator(indicator3).score(3).build();

        Indicator indicator4 = Indicator.builder().indicatorId(4).build();
        HealthIndicator mock4 = HealthIndicator.builder().country(country2).category(category3).indicator(indicator4).score(4).build();
        when(iHealthIndicatorRepository.find(null)).thenReturn(asList(mock1, mock2, mock3, mock4));

        CountriesHealthScoreDto countriesHealthScoreDto = healthIndicatorService.fetchHealthScores(null, null);

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
    }

    @Test
    public void shouldReturnEmptyListWhenPhaseForGivenCategoryIsNotPresentWhileFetchingGlobalHealthScores() {
        String USA = "USA";
        String India = "IND";

        Category category1 = Category.builder().id(9).name("Category 1").build();
        Country country1 = new Country(India, "India");
        Indicator indicator1 = Indicator.builder().indicatorId(1).build();
        HealthIndicator mock1 = HealthIndicator.builder().country(country1).category(category1).indicator(indicator1).score(1).build();

        Category category2 = Category.builder().id(8).name("Category 2").build();
        Country country2 = new Country(USA, "United States");
        Indicator indicator2 = Indicator.builder().indicatorId(2).build();
        HealthIndicator mock2 = HealthIndicator.builder().country(country2).category(category2).indicator(indicator2).score(2).build();

        when(iHealthIndicatorRepository.find(1)).thenReturn(asList(mock1, mock2));

        CountriesHealthScoreDto countriesHealthScoreDto = healthIndicatorService.fetchHealthScores(1, 5);

        assertThat(countriesHealthScoreDto.getCountryHealthScores().size(), is(0));
    }

    @Test
    public void shouldFetchIndicatorsBasedOnFilters() throws Exception {
        Category category3 = Category.builder().id(7).name("Category 4").build();
        Country country1 = new Country("IND", "India");
        Indicator indicator1 = Indicator.builder().indicatorId(1).build();
        HealthIndicator mock1 = HealthIndicator.builder().country(country1).category(category3).indicator(indicator1).score(1).build();

        Country country2 = new Country("USA", "United States");

        Indicator indicator3 = Indicator.builder().indicatorId(3).build();
        HealthIndicator mock3 = HealthIndicator.builder().country(country2).category(category3).indicator(indicator3).score(3).build();

        Indicator indicator4 = Indicator.builder().indicatorId(4).build();
        HealthIndicator mock4 = HealthIndicator.builder().country(country2).category(category3).indicator(indicator4).score(4).build();
        when(iHealthIndicatorRepository.find(3)).thenReturn(asList(mock1, mock3, mock4));

        CountriesHealthScoreDto countriesHealthScoreDto = healthIndicatorService.fetchHealthScores(3, 4);

        assertThat(countriesHealthScoreDto.getCountryHealthScores().size(), is(1));
        assertThat(countriesHealthScoreDto.getCountryHealthScores().get(0).getOverallScore(), is(3.5));
        assertThat(countriesHealthScoreDto.getCountryHealthScores().get(0).getCategories().size(), is(1));
        assertThat(countriesHealthScoreDto.getCountryHealthScores().get(0).getCategories().get(0).getOverallScore(), is(3.5));
        assertThat(countriesHealthScoreDto.getCountryHealthScores().get(0).getCategories().get(0).getPhase(), is(4));
    }

    @Test
    public void shouldFetchOverAllCategoriesWithScore() {
        Category category = Category.builder().id(9).name("Category 1").build();
        Category category1 = Category.builder().id(3).name("Category 2").build();
        HealthIndicator healthIndicator = HealthIndicator.builder()
                .country(new Country("IND", "India"))
                .indicator(Indicator.builder().indicatorId(1).build())
                .category(category).score(5).build();

        HealthIndicator healthIndicator1 = HealthIndicator.builder()
                .country(new Country("IND", "India"))
                .indicator(Indicator.builder().indicatorId(1).build())
                .category(category1).score(2).build();

        HealthIndicator healthIndicator2 = HealthIndicator.builder()
                .country(new Country("USA", "USA"))
                .indicator(Indicator.builder().indicatorId(1).build())
                .category(category).score(2).build();

        HealthIndicator healthIndicator3 = HealthIndicator.builder()
                .country(new Country("USA", "USA"))
                .indicator(Indicator.builder().indicatorId(1).build())
                .category(category1).score(null).build();

        HealthIndicator healthIndicator4 = HealthIndicator.builder()
                .country(new Country("UK", "UK"))
                .indicator(Indicator.builder().indicatorId(1).build())
                .category(category1).score(0).build();

        List<HealthIndicator> healthIndicators = asList(healthIndicator, healthIndicator1, healthIndicator2, healthIndicator3, healthIndicator4);
        when(iHealthIndicatorRepository.find(null)).thenReturn(healthIndicators);

        GlobalHealthScoreDto globalHealthIndicator = healthIndicatorService.getGlobalHealthIndicator(null, null);

        assertEquals(2,globalHealthIndicator.getCategories().size());
        CategoryHealthScoreDto actualCategory = globalHealthIndicator.getCategories().stream().filter(cat -> cat.getId().equals(category.getId())).findFirst().get();
        assertEquals(4, actualCategory.getPhase().intValue());
        actualCategory = globalHealthIndicator.getCategories().stream().filter(cat -> cat.getId().equals(category1.getId())).findFirst().get();
        assertEquals(1, actualCategory.getPhase().intValue());
        assertThat(globalHealthIndicator.getOverAllScore(), is(3));
    }

    @Test
    public void fetchGlobalHealthScoresShouldIgnoreCountryWithAllIndicatorsMissing() {
        HealthIndicator mock1 = mock(HealthIndicator.class);
        when(mock1.getScore()).thenReturn(2);
        Country mockCountry1 = mock(Country.class);
        Category mock = mock(Category.class);

        when(mock1.getCountryId()).thenReturn("IND");
        when(mock1.getCountry()).thenReturn(mockCountry1);
        when(mockCountry1.getId()).thenReturn("IND");
        when(mock1.getCategory()).thenReturn(mock);
        when(mock1.getCategory().getId()).thenReturn(9);
        when(mock1.getCategory().getName()).thenReturn("Category 1");

        HealthIndicator mock2 = mock(HealthIndicator.class);
        when(mock2.getScore()).thenReturn(null);
        Country mockCountry2 = mock(Country.class);

        when(mock2.getCountry()).thenReturn(mockCountry2);
        when(mock2.getCountryId()).thenReturn("USA");
        when(mockCountry2.getId()).thenReturn("USA");
        when(mock2.getCategory()).thenReturn(mock);
        when(mock2.getCategory().getId()).thenReturn(9);
        when(mock2.getCategory().getName()).thenReturn("Category 1");

        HealthIndicator mock3 = mock(HealthIndicator.class);
        when(mock3.getScore()).thenReturn(null);

        when(mock3.getCountry()).thenReturn(mockCountry2);
        when(mock3.getCountryId()).thenReturn("USA");
        when(mockCountry2.getId()).thenReturn("USA");
        when(mock3.getCategory()).thenReturn(mock);
        when(mock3.getCategory().getId()).thenReturn(8);
        when(mock3.getCategory().getName()).thenReturn("Category 2");


        List<HealthIndicator> healthIndicators = new ArrayList<>();
        healthIndicators.add(mock1);
        healthIndicators.add(mock2);

        when(iHealthIndicatorRepository.find(1)).thenReturn(healthIndicators);
        when(iHealthIndicatorRepository.findCountriesWithHealthScores()).thenReturn(asList("IND", "USA"));
        when(iHealthIndicatorRepository.findHealthIndicatorsFor("IND")).thenReturn(asList(mock1));
        when(iHealthIndicatorRepository.findHealthIndicatorsFor("USA")).thenReturn(asList(mock2, mock3));

        GlobalHealthScoreDto globalHealthIndicator = healthIndicatorService.getGlobalHealthIndicator(1, 2);

        assertEquals(1,globalHealthIndicator.getCategories().size());
        assertThat(globalHealthIndicator.getOverAllScore(), is(2));
    }

    @Test
    public void shouldConsiderOnlyCategoriesWithGivenPhase() throws Exception {
        Category category = Category.builder().id(9).name("Category 1").build();

        HealthIndicator healthIndicator = HealthIndicator.builder()
                .country(new Country("IND", "India"))
                .indicator(Indicator.builder().indicatorId(1).build())
                .category(category).score(5).build();

        HealthIndicator healthIndicator1 = HealthIndicator.builder()
                .country(new Country("IND", "India"))
                .indicator(Indicator.builder().indicatorId(2).build())
                .category(category).score(2).build();

        HealthIndicator healthIndicator2 = HealthIndicator.builder()
                .country(new Country("USA", "USA"))
                .indicator(Indicator.builder().indicatorId(1).build())
                .category(category).score(2).build();

        HealthIndicator healthIndicator3 = HealthIndicator.builder()
                .country(new Country("USA", "USA"))
                .indicator(Indicator.builder().indicatorId(2).build())
                .category(category).score(1).build();

        HealthIndicator healthIndicator4 = HealthIndicator.builder()
                .country(new Country("USA", "USA"))
                .indicator(Indicator.builder().indicatorId(3).build())
                .category(category).score(null).build();
        when(iHealthIndicatorRepository.find(category.getId())).thenReturn(asList(healthIndicator, healthIndicator1, healthIndicator2, healthIndicator3, healthIndicator4));

        GlobalHealthScoreDto globalHealthIndicator = healthIndicatorService.getGlobalHealthIndicator(category.getId(), 2);

        assertEquals(2, globalHealthIndicator.getOverAllScore().intValue());
        assertEquals(1, globalHealthIndicator.getCategories().size());
        assertEquals(1.5, globalHealthIndicator.getCategories().get(0).getOverallScore().doubleValue(), 0.01);
        assertEquals(2, globalHealthIndicator.getCategories().get(0).getPhase().intValue());
    }

    @Test
    public void shouldInvokeConvertExcelOnGlobalExport() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(iHealthIndicatorRepository.findCountriesWithHealthScores()).thenReturn(asList());
        healthIndicatorService.createGlobalHealthIndicatorInExcel(request, response);
        verify(excelUtilService).convertListToExcel(anyList());
        verify(excelUtilService).downloadFile(request, response);
    }

    @Test
    public void shouldInvokeConvertExcelOnExportOfSingleCountry() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(iHealthIndicatorRepository.findCountriesWithHealthScores()).thenReturn(asList());
        healthIndicatorService.createHealthIndicatorInExcelFor("IND", request, response);
        verify(excelUtilService).convertListToExcel(anyList());
        verify(excelUtilService).downloadFile(request, response);
    }

}