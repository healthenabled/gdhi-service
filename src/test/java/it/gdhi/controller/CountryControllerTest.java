package it.gdhi.controller;

import it.gdhi.dto.CountryHealthScoreDto;
import it.gdhi.dto.GlobalHealthScoreDto;
import it.gdhi.model.DevelopmentIndicator;
import it.gdhi.repository.IDevelopmentIndicatorRepository;
import it.gdhi.service.CountryService;
import it.gdhi.service.HealthIndicatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CountryControllerTest {

    @InjectMocks
    private CountryController countryController;

    @Mock
    private CountryService countryService;

    @Mock
    private IDevelopmentIndicatorRepository iDevelopmentIndicatorRepository;

    @Mock
    private HealthIndicatorService healthIndicatorService;

    @Test
    public void shouldListCountries() {
        countryController.getCountries();
        verify(countryService).fetchCountries();
    }

    @Test
    public void shouldInvokeDevelopmentIndicatorRepoToFetchCountryContextInfo() {
        DevelopmentIndicator value = new DevelopmentIndicator();
        Optional<DevelopmentIndicator> developmentIndicatorOptional = Optional.of(value);
        when(iDevelopmentIndicatorRepository.findByCountryId("ARG")).thenReturn(developmentIndicatorOptional);
        countryController.getDevelopmentIndicatorForGivenCountryCode("ARG");
        verify(iDevelopmentIndicatorRepository).findByCountryId("ARG");
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
        GlobalHealthScoreDto mock = mock(GlobalHealthScoreDto.class);
        CountryHealthScoreDto countryHealthScoreDto = mock(CountryHealthScoreDto.class);
        when(countryHealthScoreDto.getCountryId()).thenReturn("ARG");
        when(mock.getCountryHealthScores()).thenReturn(singletonList(countryHealthScoreDto));
        when(healthIndicatorService.fetchHealthScores()).thenReturn(mock);
        GlobalHealthScoreDto globalHealthIndicators = countryController.getGlobalHealthIndicators();
        int size = globalHealthIndicators.getCountryHealthScores().size();
        assertThat(size, is(1));
        assertThat(globalHealthIndicators.getCountryHealthScores().get(0).getCountryId(), is(countryHealthScoreDto.getCountryId()));
        verify(healthIndicatorService).fetchHealthScores();
    }

}