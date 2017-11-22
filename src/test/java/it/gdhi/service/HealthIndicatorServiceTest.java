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

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HealthIndicatorServiceTest {

    @InjectMocks
    HealthIndicatorService healthIndicatorService;

    @Mock
    CountryHealthScoreDto countryHealthScoreDto;

    @Mock
    IHealthIndicatorRepository iHealthIndicatorRepository;

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
        List<String> countryIds = asList(USA, India);

        when(iHealthIndicatorRepository.findCountriesWithHealthScores()).thenReturn(countryIds);

        HealthIndicator mock1 = mock(HealthIndicator.class);
        when(mock1.getScore()).thenReturn(1);
        Country mockCountry1 = mock(Country.class);
        when(mock1.getCountry()).thenReturn(mockCountry1);
        when(mockCountry1.getId()).thenReturn("IND");
        when(mockCountry1.getName()).thenReturn("India");
        when(mock1.getCategory()).thenReturn(mock(Category.class));
        when(mock1.getCategory().getId()).thenReturn(9);
        when(mock1.getCategory().getName()).thenReturn("Category 1");

        HealthIndicator mock2 = mock(HealthIndicator.class);
        when(mock2.getScore()).thenReturn(2);
        Country mockCountry2 = mock(Country.class);
        when(mock2.getCountry()).thenReturn(mockCountry2);
        when(mockCountry2.getId()).thenReturn("USA");
        when(mockCountry2.getName()).thenReturn("United States");
        when(mock2.getCategory()).thenReturn(mock(Category.class));
        when(mock2.getCategory().getId()).thenReturn(8);
        when(mock2.getCategory().getName()).thenReturn("Category 2");

        when(iHealthIndicatorRepository.findHealthIndicatorsFor(India)).thenReturn(asList(mock1, mock2));
        HealthIndicator mock3 = mock(HealthIndicator.class);
        Country mockCountry3 = mock(Country.class);
        when(mock3.getCountry()).thenReturn(mockCountry3);
        when(mockCountry2.getId()).thenReturn("USA");
        when(mockCountry2.getName()).thenReturn("United States");
        when(mock3.getCategory()).thenReturn(mock(Category.class));
        when(mock3.getCategory().getId()).thenReturn(7);
        when(mock3.getCategory().getName()).thenReturn("Category 4");
        when(mock3.getScore()).thenReturn(3);

        HealthIndicator mock4 = mock(HealthIndicator.class);
        Country mockCountry4 = mock(Country.class);
        when(mock4.getCountry()).thenReturn(mockCountry4);
        when(mockCountry4.getId()).thenReturn("USA");
        when(mockCountry4.getName()).thenReturn("United States");
        when(mock4.getCategory()).thenReturn(mock(Category.class));
        when(mock4.getCategory().getId()).thenReturn(6);
        when(mock4.getCategory().getName()).thenReturn("Category 5");
        when(mock4.getScore()).thenReturn(4);

        when(iHealthIndicatorRepository.findHealthIndicatorsFor(USA)).thenReturn(asList(mock3, mock4));
        AllCountriesHealthScoreDto allCountriesHealthScoreDto = healthIndicatorService.fetchHealthScores();
        assertThat(allCountriesHealthScoreDto.getCountryHealthScores().size(), is(2));
    }

    @Test
    public void shouldFetchOverAllCategoriesWithScore() {
        HealthIndicator mock1 = mock(HealthIndicator.class);
        when(mock1.getScore()).thenReturn(5);
        Country mockCountry1 = mock(Country.class);
        Category mock = mock(Category.class);

        when(mock1.getCountry()).thenReturn(mockCountry1);
        when(mockCountry1.getId()).thenReturn("IND");
        when(mock1.getCategory()).thenReturn(mock);
        when(mock1.getCategory().getId()).thenReturn(9);
        when(mock1.getCategory().getName()).thenReturn("Category 1");

        HealthIndicator mock2 = mock(HealthIndicator.class);
        when(mock2.getScore()).thenReturn(2);
        Country mockCountry2 = mock(Country.class);

        when(mock2.getCountry()).thenReturn(mockCountry2);
        when(mockCountry2.getId()).thenReturn("USA");
        when(mock2.getCategory()).thenReturn(mock);
        when(mock2.getCategory().getId()).thenReturn(9);
        when(mock2.getCategory().getName()).thenReturn("Category 1");


        List<HealthIndicator> healthIndicators = new ArrayList<>();
        healthIndicators.add(mock1);
        healthIndicators.add(mock2);

        when(iHealthIndicatorRepository.findAll()).thenReturn(healthIndicators);
        when(iHealthIndicatorRepository.findCountriesWithHealthScores()).thenReturn(asList("IND", "USA"));
        when(iHealthIndicatorRepository.findHealthIndicatorsFor("IND")).thenReturn(asList(mock1));
        when(iHealthIndicatorRepository.findHealthIndicatorsFor("USA")).thenReturn(asList(mock2));
        GlobalHealthScoreDto globalHealthIndicator = healthIndicatorService.getGlobalHealthIndicator();

        assertEquals(1,globalHealthIndicator.getCategories().size());
        assertThat(globalHealthIndicator.getOverAllScore(), is(4));
    }
}