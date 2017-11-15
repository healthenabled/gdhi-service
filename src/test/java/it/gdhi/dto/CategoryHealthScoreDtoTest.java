package it.gdhi.dto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryHealthScoreDtoTest {
    @Test
    public void shouldGetOverallScoreForTheCategory() throws Exception {
        IndicatorScoreDto indicator = new IndicatorScoreDto(1, "Indicator", "indicator desc", 5, "score desc");
        IndicatorScoreDto indicator1 = new IndicatorScoreDto(2, "Indicator name", "indicator name desc", 2, "score1 desc");
        List<IndicatorScoreDto> indicators = asList(indicator, indicator1);
        CategoryHealthScoreDto category = new CategoryHealthScoreDto(1, "cat1", indicators);
        assertEquals(new Double(3.5), Double.valueOf(category.overallScore().getAsDouble()));
        assertEquals(new Integer(4), category.getPhase());
    }

    @Test
    public void shouldNotConsiderNullIndicatorsForAverage() throws Exception {
        IndicatorScoreDto indicator = new IndicatorScoreDto(1, "Indicator", "indicator desc", 5, "score desc");
        IndicatorScoreDto indicator1 = new IndicatorScoreDto(2, "Indicator name", "indicator name desc", null, "score1 desc");
        IndicatorScoreDto indicator2 = new IndicatorScoreDto(3, "Indicator1 name", "indicator name desc", 0, "score0 desc");
        List<IndicatorScoreDto> indicators = asList(indicator, indicator1, indicator2);
        CategoryHealthScoreDto category = new CategoryHealthScoreDto(1, "cat1", indicators);
        assertEquals(new Double(2.5), Double.valueOf(category.overallScore().getAsDouble()));
        assertEquals(new Integer(3), category.getPhase());
    }

    @Test
    public void shouldReturnNullIfNoScoreIsPresent() throws Exception {
        IndicatorScoreDto indicator = new IndicatorScoreDto(1, "Indicator", "indicator desc", null, "score desc");
        IndicatorScoreDto indicator1 = new IndicatorScoreDto(2, "Indicator name", "indicator name desc", null, "score1 desc");
        List<IndicatorScoreDto> indicators = asList(indicator, indicator1);
        CategoryHealthScoreDto category = new CategoryHealthScoreDto(1, "cat1", indicators);
        assertFalse(category.overallScore().isPresent());
        assertNull(category.getPhase());
    }
}