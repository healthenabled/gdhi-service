package it.gdhi.model;

import it.gdhi.model.id.HealthIndicatorId;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class HealthIndicatorsTest {

    private List<HealthIndicator> healthIndicators;

    @Before
    public void setUp() throws Exception {
        String india = "IND";
        HealthIndicator healthIndicator = HealthIndicator.builder()
                .healthIndicatorId(new HealthIndicatorId(india, 1, 1))
                .country(new Country(india, "india"))
                .category(Category.builder().id(1).build())
                .indicator(Indicator.builder().indicatorId(1).build())
                .score(5)
                .build();

        HealthIndicator healthIndicator1 = HealthIndicator.builder()
                .country(new Country(india, "india"))
                .healthIndicatorId(new HealthIndicatorId(india, 1, 2))
                .category(Category.builder().id(1).build())
                .indicator(Indicator.builder().indicatorId(2).build())
                .score(3)
                .build();


        String japan = "JAP";
        HealthIndicator healthIndicatorJap = HealthIndicator.builder()
                .healthIndicatorId(new HealthIndicatorId(japan, 1, 1))
                .country(new Country(japan, "japan"))
                .category(Category.builder().id(1).build())
                .indicator(Indicator.builder().indicatorId(1).build())
                .score(2)
                .build();

        HealthIndicator healthIndicator1Jap = HealthIndicator.builder()
                .healthIndicatorId(new HealthIndicatorId(japan, 1, 2))
                .country(new Country(japan, "japan"))
                .category(Category.builder().id(2).build())
                .indicator(Indicator.builder().indicatorId(3).build())
                .score(null)
                .build();

        String uk = "UK";
        HealthIndicator healthIndicatorUK = HealthIndicator.builder()
                .healthIndicatorId(new HealthIndicatorId(uk, 1, 1))
                .country(new Country(uk, "uk"))
                .category(Category.builder().id(1).build())
                .indicator(Indicator.builder().indicatorId(1).build())
                .score(null)
                .build();

        HealthIndicator healthIndicator1UK = HealthIndicator.builder()
                .country(new Country(uk, "uk"))
                .healthIndicatorId(new HealthIndicatorId(uk, 1, 2))
                .category(Category.builder().id(2).build())
                .indicator(Indicator.builder().indicatorId(3).build())
                .score(null)
                .build();

        this.healthIndicators = asList(healthIndicator, healthIndicator1, healthIndicator1Jap, healthIndicatorJap, healthIndicatorUK, healthIndicator1UK);

    }

    @Test
    public void shouldReturnCountOfCountriesWithAlteastOneIndicatorScore() throws Exception {
        assertEquals(2, new HealthIndicators(healthIndicators).getCountOfCountriesWithAlteastOneScore().intValue());
    }

    @Test
    public void shouldGetTotalScore() throws Exception {
        assertEquals(new Double(3.33), new HealthIndicators(healthIndicators).getTotalScore(), 0.01);
    }

    @Test
    public void shouldGetScoresOf() throws Exception {
    }
}