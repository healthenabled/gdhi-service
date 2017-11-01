package it.gdhi.controller;

import it.gdhi.model.DevelopmentIndicator;
import it.gdhi.repository.IDevelopmentIndicatorRepository;
import it.gdhi.service.CountryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountryControllerTest {

    @InjectMocks
    private CountryController countryController;

    @Mock
    private CountryService countryService;

    @Mock
    private IDevelopmentIndicatorRepository iDevelopmentIndicatorRepository;

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
}