package it.gdhi.dto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CountryHealthScoreDtoTest {
    @Test
    public void shouldGetOverallScoreForTheCountry() throws Exception {
        IndicatorScoreDto indicator = new IndicatorScoreDto(1, "Indicator", "indicator desc", 5, "score desc");
        IndicatorScoreDto indicator1 = new IndicatorScoreDto(2, "Indicator name", "indicator name desc", 2, "score1 desc");
        CategoryHealthScoreDto category = new CategoryHealthScoreDto(1, "cat1", asList(indicator));
        CategoryHealthScoreDto category1 = new CategoryHealthScoreDto(2, "cat2", asList(indicator1));

        CountryHealthScoreDto country = new CountryHealthScoreDto("IND", "India", asList(category, category1));

        assertEquals(new Double(3.5), country.getOverallScore());
        assertEquals(new Integer(4), country.getCountryPhase());
    }

    @Test
    public void shouldNotConsiderNullIndicatorsForAverage() throws Exception {
        IndicatorScoreDto indicator = new IndicatorScoreDto(1, "Indicator", "indicator desc", 5, "score desc");
        IndicatorScoreDto indicator1 = new IndicatorScoreDto(2, "Indicator name", "indicator name desc", null, "score1 desc");
        IndicatorScoreDto indicator2 = new IndicatorScoreDto(3, "Indicator name", "indicator name desc", 0, "score1 desc");
        CategoryHealthScoreDto category = new CategoryHealthScoreDto(1, "cat1", asList(indicator));
        CategoryHealthScoreDto category1 = new CategoryHealthScoreDto(2, "cat2", asList(indicator1));
        CategoryHealthScoreDto category2 = new CategoryHealthScoreDto(3, "cat3", asList(indicator2));

        CountryHealthScoreDto country = new CountryHealthScoreDto("IND", "India", asList(category, category1, category2));

        assertEquals(new Double(2.5), country.getOverallScore());
        assertEquals(new Integer(3), country.getCountryPhase());
    }

    @Test
    public void shouldReturnNullIfNoScoreIsPresent() throws Exception {
        IndicatorScoreDto indicator = new IndicatorScoreDto(1, "Indicator", "indicator desc", null, "score desc");
        IndicatorScoreDto indicator1 = new IndicatorScoreDto(2, "Indicator name", "indicator name desc", null, "score1 desc");
        CategoryHealthScoreDto category = new CategoryHealthScoreDto(1, "cat1", asList(indicator));
        CategoryHealthScoreDto category1 = new CategoryHealthScoreDto(2, "cat2", asList(indicator1));

        CountryHealthScoreDto country = new CountryHealthScoreDto("IND", "India", asList(category, category1));
        assertNull(country.getOverallScore());
        assertNull(country.getCountryPhase());
    }

}