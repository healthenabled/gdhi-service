package it.gdhi.controller;

import it.gdhi.service.CountryDetailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CountryControllerTest {

    @InjectMocks
    private CountryController countryController;

    @Mock
    private CountryDetailService countryDetailService;

    @Test
    public void shouldListCountries() {
        countryController.listCountries();
        verify(countryDetailService).insert();
    }
}