package it.gdhi.service;

import it.gdhi.dto.CategoryHealthScoreDto;
import it.gdhi.dto.CountryHealthScoreDto;
import it.gdhi.dto.GlobalHealthScoreDto;
import it.gdhi.dto.IndicatorScoreDto;
import it.gdhi.model.*;
import it.gdhi.model.id.HealthIndicatorId;
import it.gdhi.model.id.IndicatorScoreId;
import it.gdhi.repository.IHealthIndicatorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HealthIndicatorServiceTest {

    @InjectMocks
    HealthIndicatorService healthIndicatorService;

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
        IndicatorScoreId indicatorScoreId1 = IndicatorScoreId.builder().score(score2).indicatorId(indicatorId2).build();
        IndicatorScore indicatorScore1 = IndicatorScore.builder().id(indicatorScoreId1).indicator(indicator1).definition("score 1").build();
        HealthIndicator healthIndicator1 = new HealthIndicator(healthIndicatorId1, country1, category1, indicatorScore1, score1);

        HealthIndicatorId healthIndicatorId2 = new HealthIndicatorId(countryId1,categoryId2,indicatorId2);
        Category category2 = new Category(categoryId2, "Category2");
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 2", "Definition2");
        Indicator indicator3 = new Indicator(indicatorId3, "Indicator 3", "Definition3");

        IndicatorScoreId indicatorScoreId2 = IndicatorScoreId.builder().score(score2).indicatorId(indicatorId2).build();
        IndicatorScore indicatorScore2 = IndicatorScore.builder().id(indicatorScoreId2).indicator(indicator2).definition("score 2").build();
        HealthIndicator healthIndicator2 = new HealthIndicator(healthIndicatorId2, country1, category2, indicatorScore2,  score2 );
        IndicatorScoreId indicatorScoreId3 = IndicatorScoreId.builder().score(score3).indicatorId(indicatorId3).build();
        IndicatorScore indicatorScore3 = IndicatorScore.builder().id(indicatorScoreId3).indicator(indicator3).definition("score 3").build();
        HealthIndicator healthIndicator3 = new HealthIndicator(healthIndicatorId2, country1, category2, indicatorScore3,  score3 );

        List<HealthIndicator> healthIndicatorsForCountry = asList(healthIndicator1, healthIndicator2, healthIndicator3);

        when(iHealthIndicatorRepository.findHealthIndicatorsFor(countryId1)).thenReturn(healthIndicatorsForCountry);

    }

    @Test
    public void shouldReturnCountryHealthScoreAtCategoryLevelAndIndicatorLevel() throws Exception {
        String countryId = "IND";
        Integer categoryId1 = 1;
        Integer categoryId2 = 2;
        Integer indicatorId1 = 1;
        Integer indicatorId2 = 2;
        Integer indicatorId3 =3;

        dataSet(countryId, categoryId1, categoryId2, indicatorId1, indicatorId2, indicatorId3);
        CountryHealthScoreDto healthScoreForACountry = healthIndicatorService.fetchCountryHealthScore(countryId);

        assertEquals(2, healthScoreForACountry.getCategories().size());
        assertEquals(new Double(2.75), healthScoreForACountry.getOverallScore());
        assertEquals(new Integer(3), healthScoreForACountry.getPhase());

        CategoryHealthScoreDto category1 = healthScoreForACountry.getCategories().stream().filter(category -> category.getName().equals("Leadership and Governance")).findFirst().get();
        assertEquals(1, category1.getIndicators().size());
        assertEquals(new Integer(3), category1.getPhase());
        assertEquals(1, category1.getIndicators().size());

        CategoryHealthScoreDto category2 = healthScoreForACountry.getCategories().stream().filter(category -> category.getName().equals("Category2")).findFirst().get();
        assertEquals(2, category2.getIndicators().size());
        assertEquals(new Integer(3), category2.getPhase());
        assertEquals(2, category2.getIndicators().size());

        IndicatorScoreDto indicator1 = category1.getIndicators().stream().filter(indicator -> indicator.getName().equals("Indicator 1")).findFirst().get();
        assertEquals(new Integer(3), indicator1.getScore());
        assertEquals("Definition1", indicator1.getIndicatorDescription());
        assertEquals("score 1", indicator1.getScoreDescription());

        IndicatorScoreDto indicator2 = category2.getIndicators().stream().filter(indicator -> indicator.getName().equals("Indicator 2")).findFirst().get();
        assertEquals(new Integer(4), indicator2.getScore());
        assertEquals("Definition2", indicator2.getIndicatorDescription());
        assertEquals("score 2", indicator2.getScoreDescription());

        IndicatorScoreDto indicator3 = category2.getIndicators().stream().filter(indicator -> indicator.getName().equals("Indicator 3")).findFirst().get();
        assertEquals(new Integer(1), indicator3.getScore());
        assertEquals("Definition3", indicator3.getIndicatorDescription());
        assertEquals("score 3", indicator3.getScoreDescription());
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
        List<CategoryHealthScoreDto> leadership = healthScoreForACountry.getCategories().stream().filter(a -> a.getName().equals("Leadership and Governance")).collect(toList());
        List<CategoryHealthScoreDto> category2 = healthScoreForACountry.getCategories().stream().filter(a -> a.getName().equals("Category2")).collect(toList());
        assertThat(leadership.size(), is(1));
        assertThat(leadership.get(0).getPhase(), is(3));
        assertThat(category2.size(), is(1));
        assertThat(category2.get(0).getPhase(), is(3));
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
        HealthIndicator healthIndicator1 = new HealthIndicator(healthIndicatorId1, country1, category1, IndicatorScore.builder().indicator(indicator1).build(),  null);

        HealthIndicatorId healthIndicatorId2 = new HealthIndicatorId(countryId1,categoryId1,indicatorId2);
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 1", "Definition");
        Integer indicatorScore = 2;
        HealthIndicator healthIndicator2 = new HealthIndicator(healthIndicatorId2, country1, category1, IndicatorScore.builder().indicator(indicator2).build(),  indicatorScore);

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
        HealthIndicator healthIndicator1 = new HealthIndicator(healthIndicatorId1, country1, category1, IndicatorScore.builder().indicator(indicator1).build(),  null);

        HealthIndicatorId healthIndicatorId2 = new HealthIndicatorId(countryId1,categoryId1,indicatorId2);
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 1", "Definition");
        HealthIndicator healthIndicator2 = new HealthIndicator(healthIndicatorId2, country1, category2, IndicatorScore.builder().indicator(indicator2).build(),  2);

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
        HealthIndicator healthIndicator1 = new HealthIndicator(healthIndicatorId1, country1, category1, IndicatorScore.builder().indicator(indicator1).build(),  null );

        HealthIndicatorId healthIndicatorId2 = new HealthIndicatorId(countryId1,categoryId1,indicatorId2);
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 1", "Definition");
        HealthIndicator healthIndicator2 = new HealthIndicator(healthIndicatorId2, country1, category1, IndicatorScore.builder().indicator(indicator2).build(),  null );

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
        HealthIndicator healthIndicator1 = new HealthIndicator(healthIndicatorId1, country1, category1, IndicatorScore.builder().indicator(indicator1).build(),  indicatorScore1 );

        HealthIndicatorId healthIndicatorId2 = new HealthIndicatorId(countryId1,categoryId1,indicatorId2);
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 1", "Definition");
        HealthIndicator healthIndicator2 = new HealthIndicator(healthIndicatorId2, country1, category1, IndicatorScore.builder().indicator(indicator2).build(),  indicatorScore2 );

        List<HealthIndicator> healthIndicatorsForCountry = asList(healthIndicator1, healthIndicator2);

        when(iHealthIndicatorRepository.findHealthIndicatorsFor(countryId1)).thenReturn(healthIndicatorsForCountry);

        CountryHealthScoreDto healthScoreForACountry = healthIndicatorService.fetchCountryHealthScore(countryId);
        assertSet2(healthScoreForACountry, countryId, countryName);
    }

    private void assertSet2(CountryHealthScoreDto healthScoreForACountry, String countryId, String countryName){
        assertThat(healthScoreForACountry.getCountryId(), is(countryId));
        assertThat(healthScoreForACountry.getCountryName(), is(countryName));
        assertThat(healthScoreForACountry.getCategories().size(), is(1));
        assertThat(healthScoreForACountry.getPhase(), is(4));
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
        HealthIndicator healthIndicator1 = new HealthIndicator(healthIndicatorId1, country1, category1, IndicatorScore.builder().indicator(indicator1).build(),  indicatorScore1 );

        HealthIndicatorId healthIndicatorId2 = new HealthIndicatorId(countryId1,categoryId1,indicatorId2);
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 1", "Definition");
        HealthIndicator healthIndicator2 = new HealthIndicator(healthIndicatorId2, country1, category1, IndicatorScore.builder().indicator(indicator2).build(),  indicatorScore2 );

        HealthIndicatorId healthIndicatorId3 = new HealthIndicatorId(countryId1,categoryId1,indicatorId3);
        Indicator indicator3 = new Indicator(indicatorId3, "Indicator 1", "Definition");
        HealthIndicator healthIndicator3 = new HealthIndicator(healthIndicatorId3, country1, category1, IndicatorScore.builder().indicator(indicator3).build(),  indicatorScore3 );

        List<HealthIndicator> healthIndicatorsForCountry = asList(healthIndicator1, healthIndicator2, healthIndicator3);

        when(iHealthIndicatorRepository.findHealthIndicatorsFor(countryId1)).thenReturn(healthIndicatorsForCountry);

        CountryHealthScoreDto healthScoreForACountry = healthIndicatorService.fetchCountryHealthScore(countryId);
        assertSet3(healthScoreForACountry, countryId, countryName);
    }

    private void assertSet3(CountryHealthScoreDto healthScoreForACountry, String countryId, String countryName){
        assertThat(healthScoreForACountry.getCountryId(), is(countryId));
        assertThat(healthScoreForACountry.getCountryName(), is(countryName));
        assertThat(healthScoreForACountry.getCategories().size(), is(1));
        assertThat(healthScoreForACountry.getPhase(), is(2));
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
        GlobalHealthScoreDto globalHealthScoreDto = healthIndicatorService.fetchHealthScores();
        assertThat(globalHealthScoreDto.getCountryHealthScores().size(), is(2));
    }

}