package it.gdhi.controller;

import it.gdhi.dto.CountriesHealthScoreDto;
import it.gdhi.dto.CountryHealthScoreDto;
import it.gdhi.dto.GdhiQuestionnaire;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        CountriesHealthScoreDto mockGlobalHealthScore = mock(CountriesHealthScoreDto.class);

        CountryHealthScoreDto countryHealthScoreDto = mock(CountryHealthScoreDto.class);
        when(countryHealthScoreDto.getCountryId()).thenReturn("ARG");
        when(mockGlobalHealthScore.getCountryHealthScores()).thenReturn(singletonList(countryHealthScoreDto));
        when(healthIndicatorService.fetchHealthScores()).thenReturn(mockGlobalHealthScore);
        CountriesHealthScoreDto globalHealthIndicators = countryController.getAllCountriesHealthIndicatorScores();
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
    public void shouldSaveHealthIndicators() {
        GdhiQuestionnaire mock = mock(GdhiQuestionnaire.class);
        doNothing().when(countryService).save(mock);
        countryController.saveHealthIndicatorsFor(mock);
        verify(countryService).save(mock);
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

    @Test
    public void shouldGetCountryDetails() throws Exception {
        String countryId = "IND";

        countryController.getCountryDetails(countryId);

        verify(countryService).getDetails(countryId);
    }

    @Test
    public void shouldExportGlobalData() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        countryController.exportGlobalData(request, response);
        verify(healthIndicatorService).createGlobalHealthIndicatorInExcel(request, response);
    }

    @Test
    public void shouldExportDataForAGivenCountry() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        countryController.exportCountryDetails(request, response, "IND");
        verify(healthIndicatorService).createHealthIndicatorInExcelFor("IND", request, response);
    }
 }