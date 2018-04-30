package it.gdhi.controller;

import it.gdhi.dto.CountriesHealthScoreDto;
import it.gdhi.dto.CountryHealthScoreDto;
import it.gdhi.dto.GlobalHealthScoreDto;
import it.gdhi.service.CountryHealthIndicatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HealthIndicatorControllerTest {

    @InjectMocks
    private HealthIndicatorController healthIndicatorController;

    @Mock
    private CountryHealthIndicatorService countryHealthIndicatorService;

    @Test
    public void shouldInvokeGetGlobalHealthIndicator() {
        GlobalHealthScoreDto expected = mock(GlobalHealthScoreDto.class);

        when(countryHealthIndicatorService.getGlobalHealthIndicator(null, 2)).thenReturn(expected);
        GlobalHealthScoreDto actual = healthIndicatorController.getGlobalHealthIndicator(null, 2);
        assertThat(expected, is(actual));
        verify(countryHealthIndicatorService).getGlobalHealthIndicator(null, 2);
    }

    @Test
    public void shouldInvokeFetchHealthScoresOnGettingGlobalInfo() {
        CountriesHealthScoreDto mockGlobalHealthScore = mock(CountriesHealthScoreDto.class);

        CountryHealthScoreDto countryHealthScoreDto = mock(CountryHealthScoreDto.class);
        when(countryHealthScoreDto.getCountryId()).thenReturn("ARG");
        when(mockGlobalHealthScore.getCountryHealthScores()).thenReturn(singletonList(countryHealthScoreDto));
        when(countryHealthIndicatorService.fetchCountriesHealthScores(4, null)).thenReturn(mockGlobalHealthScore);
        CountriesHealthScoreDto globalHealthIndicators = healthIndicatorController.getCountriesHealthIndicatorScores(4, null);
        int size = globalHealthIndicators.getCountryHealthScores().size();
        assertThat(size, is(1));
        assertThat(globalHealthIndicators.getCountryHealthScores().get(0).getCountryId(), is(countryHealthScoreDto.getCountryId()));
        verify(countryHealthIndicatorService).fetchCountriesHealthScores(4, null);
    }
}