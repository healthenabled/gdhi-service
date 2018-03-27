package it.gdhi.model;

import it.gdhi.model.id.CountryHealthIndicatorId;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class CountryHealthIndicatorsTest {

    private List<CountryHealthIndicator> countryHealthIndicators;

    @Before
    public void setUp() throws Exception {
        String india = "IND";
        CountryHealthIndicator countryHealthIndicator = CountryHealthIndicator.builder()
                .countryHealthIndicatorId(new CountryHealthIndicatorId(india, 1, 1))
                .country(new Country(india, "india"))
                .category(Category.builder().id(1).build())
                .indicator(Indicator.builder().indicatorId(1).build())
                .score(5)
                .build();

        CountryHealthIndicator countryHealthIndicator1 = CountryHealthIndicator.builder()
                .country(new Country(india, "india"))
                .countryHealthIndicatorId(new CountryHealthIndicatorId(india, 1, 2))
                .category(Category.builder().id(1).build())
                .indicator(Indicator.builder().indicatorId(2).build())
                .score(3)
                .build();


        String japan = "JAP";
        CountryHealthIndicator countryHealthIndicatorJap = CountryHealthIndicator.builder()
                .countryHealthIndicatorId(new CountryHealthIndicatorId(japan, 1, 1))
                .country(new Country(japan, "japan"))
                .category(Category.builder().id(1).build())
                .indicator(Indicator.builder().indicatorId(1).build())
                .score(2)
                .build();

        CountryHealthIndicator countryHealthIndicator1Jap = CountryHealthIndicator.builder()
                .countryHealthIndicatorId(new CountryHealthIndicatorId(japan, 1, 2))
                .country(new Country(japan, "japan"))
                .category(Category.builder().id(2).build())
                .indicator(Indicator.builder().indicatorId(3).build())
                .score(null)
                .build();

        String uk = "UK";
        CountryHealthIndicator countryHealthIndicatorUK = CountryHealthIndicator.builder()
                .countryHealthIndicatorId(new CountryHealthIndicatorId(uk, 1, 1))
                .country(new Country(uk, "uk"))
                .category(Category.builder().id(1).build())
                .indicator(Indicator.builder().indicatorId(1).build())
                .score(null)
                .build();

        CountryHealthIndicator countryHealthIndicator1UK = CountryHealthIndicator.builder()
                .country(new Country(uk, "uk"))
                .countryHealthIndicatorId(new CountryHealthIndicatorId(uk, 1, 2))
                .category(Category.builder().id(2).build())
                .indicator(Indicator.builder().indicatorId(3).build())
                .score(null)
                .build();

        this.countryHealthIndicators = asList(countryHealthIndicator, countryHealthIndicator1, countryHealthIndicator1Jap, countryHealthIndicatorJap, countryHealthIndicatorUK, countryHealthIndicator1UK);

    }

    @Test
    public void shouldReturnCountOfCountriesWithAlteastOneIndicatorScore() throws Exception {
        assertEquals(2, new CountryHealthIndicators(countryHealthIndicators).getCountOfCountriesWithAlteastOneScore().intValue());
    }

    @Test
    public void shouldGetTotalScore() throws Exception {
        assertEquals(3.33, new CountryHealthIndicators(countryHealthIndicators).getTotalScore(), 0.01);
    }
}