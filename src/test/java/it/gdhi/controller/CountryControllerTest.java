package it.gdhi.controller;

import it.gdhi.dto.CountryHealthScoreDto;
import it.gdhi.model.DevelopmentIndicator;
import it.gdhi.repository.IDevelopmentIndicatorRepository;
import it.gdhi.service.CountryService;
import it.gdhi.service.HealthIndicatorsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

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
    private HealthIndicatorsService healthIndicatorsService;

    @Test
    public void shouldListCountries() {
        countryController.listCountries();
        verify(countryService).fetchCountry();
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
        when(healthIndicatorsService.fetchCountryHealthScore(countryId)).thenReturn(countryHealthScoreMock);
        countryController.getHealthIndicatorForGivenCountryCode(countryId);
        verify(healthIndicatorsService).fetchCountryHealthScore(countryId);
    }
}