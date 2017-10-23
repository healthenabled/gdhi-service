package it.gdhi.service;

import it.gdhi.repository.CountryDetailRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CountryDetailServiceTest {

    @InjectMocks
    CountryDetailService countryDetailService;
    @Mock
    CountryDetailRepository countryDetailRepository;

    @Test
    public void shouldInsertTestData() {
        countryDetailService.insert();
        verify(countryDetailRepository).save(any());
    }
}