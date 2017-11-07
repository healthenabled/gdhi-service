package it.gdhi.service;

import it.gdhi.repository.ICountryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CountryDetailServiceTest {

    @InjectMocks
    CountryService countryDetailService;
    @Mock
    ICountryRepository countryDetailRepository;

    @Test
    public void shouldInsertTestData() {
        countryDetailService.fetchCountries();
        verify(countryDetailRepository).findAll();
    }
}