package it.gdhi.controller;

import it.gdhi.dto.AllCountriesHealthScoreDto;
import it.gdhi.dto.CountryHealthScoreDto;
import it.gdhi.dto.GlobalHealthScoreDto;
import it.gdhi.model.DevelopmentIndicator;
import it.gdhi.service.CountryService;
import it.gdhi.service.DevelopmentIndicatorService;
import it.gdhi.service.HealthIndicatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CountryControllerTest {

    @InjectMocks
    private CountryController countryController;

    @Mock
    private CountryService countryService;

    @Mock
    private DevelopmentIndicatorService developmentIndicatorService;

    @Mock
    private HealthIndicatorService healthIndicatorService;

    @Test
    public void shouldListCountries() {
        countryController.getCountries();
        verify(countryService).fetchCountries();
    }

    @Test
    public void shouldInvokeHealthIndicatorServiceCountryScore() {
        String countryId = "ARG";
        CountryHealthScoreDto countryHealthScoreMock = mock(CountryHealthScoreDto.class);
        when(healthIndicatorService.fetchCountryHealthScore(countryId)).thenReturn(countryHealthScoreMock);
        CountryHealthScoreDto healthIndicatorForGivenCountryCode = countryController.getHealthIndicatorForGivenCountryCode(countryId);
        assertThat(healthIndicatorForGivenCountryCode, is(countryHealthScoreMock));
        verify(healthIndicatorService).fetchCountryHealthScore(countryId);
    }

    @Test
    public void shouldInvokeFetchHealthScoresOnGettingGlobalInfo() {
        AllCountriesHealthScoreDto mockGlobalHealthScore = mock(AllCountriesHealthScoreDto.class);

        CountryHealthScoreDto countryHealthScoreDto = mock(CountryHealthScoreDto.class);
        when(countryHealthScoreDto.getCountryId()).thenReturn("ARG");
        when(mockGlobalHealthScore.getCountryHealthScores()).thenReturn(singletonList(countryHealthScoreDto));
        when(healthIndicatorService.fetchHealthScores()).thenReturn(mockGlobalHealthScore);
        AllCountriesHealthScoreDto globalHealthIndicators = countryController.getAllCountriesHealthIndicatorScores();
        int size = globalHealthIndicators.getCountryHealthScores().size();
        assertThat(size, is(1));
        assertThat(globalHealthIndicators.getCountryHealthScores().get(0).getCountryId(), is(countryHealthScoreDto.getCountryId()));
        verify(healthIndicatorService).fetchHealthScores();
    }

    @Test
    public void shouldInvokeGetGlobalHealthIndicator() {
        GlobalHealthScoreDto expected = mock(GlobalHealthScoreDto.class);

        when(healthIndicatorService.getGlobalHealthIndicator()).thenReturn(expected);
        GlobalHealthScoreDto actual = countryController.getGlobalHealthIndicator();
        assertThat(expected, is(actual));
        verify(healthIndicatorService).getGlobalHealthIndicator();
    }

    @Test
    public void shouldFetchTheDevelopmentIndicators() {
        String countryId = "ARG";
        DevelopmentIndicator developmentIndicator = new DevelopmentIndicator();

        when(developmentIndicatorService.fetchCountryDevelopmentScores(countryId)).thenReturn(developmentIndicator);

        DevelopmentIndicator actualDevelopmentIndicator = countryController.getDevelopmentIndicatorForGivenCountryCode(countryId);

        verify(developmentIndicatorService).fetchCountryDevelopmentScores(countryId);

        assertEquals(developmentIndicator.getCountryId(), actualDevelopmentIndicator.getCountryId());
    }
}