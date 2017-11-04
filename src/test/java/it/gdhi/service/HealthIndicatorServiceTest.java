package it.gdhi.service;

import it.gdhi.dto.CategoryHealthScoreDto;
import it.gdhi.dto.CountryHealthScoreDto;
import it.gdhi.model.*;
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HealthIndicatorServiceTest {

    @InjectMocks
    HealthIndicatorService healthIndicatorService;

    @Mock
    IHealthIndicatorRepository iHealthIndicatorRepository;

    private void dataSet(String countryId1, int categoryId1, int categoryId2,  int indicatorId1, int indicatorId2, int indicatorId3) {
        Integer indicatorScore1 = 3;
        Integer indicatorScore2 = 4;
        Integer indicatorScore3 = 1;

        HealthIndicatorId healthIndicatorId1 = new HealthIndicatorId(countryId1,categoryId1,indicatorId1);
        Country country1 = new Country("IND", "India");
        Category category1 = new Category(categoryId1, "Leadership and Governance");
        Indicator indicator1 = new Indicator(indicatorId1, "Indicator 1", "Definition");
        HealthIndicator healthIndicator1 = new HealthIndicator(healthIndicatorId1, country1, category1, indicator1,  indicatorScore1 );

        HealthIndicatorId healthIndicatorId2 = new HealthIndicatorId(countryId1,categoryId2,indicatorId2);
        Category category2 = new Category(categoryId2, "Category2");
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 2", "Definition");
        Indicator indicator3 = new Indicator(indicatorId3, "Indicator 3", "Definition");

        HealthIndicator healthIndicator2 = new HealthIndicator(healthIndicatorId2, country1, category2, indicator2,  indicatorScore2 );
        HealthIndicator healthIndicator3 = new HealthIndicator(healthIndicatorId2, country1, category2, indicator3,  indicatorScore3 );

        List<HealthIndicator> healthIndicatorsForCountry = asList(healthIndicator1, healthIndicator2, healthIndicator3);

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
        HealthIndicator healthIndicator1 = new HealthIndicator(healthIndicatorId1, country1, category1, indicator1,  null);

        HealthIndicatorId healthIndicatorId2 = new HealthIndicatorId(countryId1,categoryId1,indicatorId2);
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 1", "Definition");
        Integer indicatorScore = 2;
        HealthIndicator healthIndicator2 = new HealthIndicator(healthIndicatorId2, country1, category1, indicator2,  indicatorScore);

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
        HealthIndicator healthIndicator1 = new HealthIndicator(healthIndicatorId1, country1, category1, indicator1,  null);

        HealthIndicatorId healthIndicatorId2 = new HealthIndicatorId(countryId1,categoryId1,indicatorId2);
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 1", "Definition");
        HealthIndicator healthIndicator2 = new HealthIndicator(healthIndicatorId2, country1, category2, indicator2,  2);

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
        HealthIndicator healthIndicator1 = new HealthIndicator(healthIndicatorId1, country1, category1, indicator1,  null );

        HealthIndicatorId healthIndicatorId2 = new HealthIndicatorId(countryId1,categoryId1,indicatorId2);
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 1", "Definition");
        HealthIndicator healthIndicator2 = new HealthIndicator(healthIndicatorId2, country1, category1, indicator2,  null );

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
        HealthIndicator healthIndicator1 = new HealthIndicator(healthIndicatorId1, country1, category1, indicator1,  indicatorScore1 );

        HealthIndicatorId healthIndicatorId2 = new HealthIndicatorId(countryId1,categoryId1,indicatorId2);
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 1", "Definition");
        HealthIndicator healthIndicator2 = new HealthIndicator(healthIndicatorId2, country1, category1, indicator2,  indicatorScore2 );

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
        HealthIndicator healthIndicator1 = new HealthIndicator(healthIndicatorId1, country1, category1, indicator1,  indicatorScore1 );

        HealthIndicatorId healthIndicatorId2 = new HealthIndicatorId(countryId1,categoryId1,indicatorId2);
        Indicator indicator2 = new Indicator(indicatorId2, "Indicator 1", "Definition");
        HealthIndicator healthIndicator2 = new HealthIndicator(healthIndicatorId2, country1, category1, indicator2,  indicatorScore2 );

        HealthIndicatorId healthIndicatorId3 = new HealthIndicatorId(countryId1,categoryId1,indicatorId3);
        Indicator indicator3 = new Indicator(indicatorId3, "Indicator 1", "Definition");
        HealthIndicator healthIndicator3 = new HealthIndicator(healthIndicatorId3, country1, category1, indicator3,  indicatorScore3 );

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

}