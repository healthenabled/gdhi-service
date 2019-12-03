package it.gdhi.controller;

import it.gdhi.dto.CountryHealthScoreDto;
import it.gdhi.dto.CountrySummaryDto;
import it.gdhi.dto.CountryUrlGenerationStatusDto;
import it.gdhi.dto.GdhiQuestionnaire;
import it.gdhi.model.DevelopmentIndicator;
import it.gdhi.service.CountryHealthDataService;
import it.gdhi.service.CountryHealthIndicatorService;
import it.gdhi.service.CountryService;
import it.gdhi.service.DevelopmentIndicatorService;
import it.gdhi.utils.LanguageCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.UUID;

import static it.gdhi.utils.FormStatus.DRAFT;
import static it.gdhi.utils.LanguageCode.en;
import static it.gdhi.utils.LanguageCode.fr;
import static java.util.Collections.emptyMap;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CountryControllerTest {

    @InjectMocks
    private CountryController countryController;

    @Mock
    private CountryService countryService;

    @Mock
    private CountryHealthDataService countryHealthDataService;

    @Mock
    private DevelopmentIndicatorService developmentIndicatorService;

    @Mock
    private CountryHealthIndicatorService countryHealthIndicatorService;

    @Test
    public void shouldListCountries() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("USER_LANGUAGE", "EN");

        countryController.getCountries(request);

        verify(countryService).fetchCountries(en);
    }

    @Test
    public void shouldListCountriesInGivenLanguage() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("USER_LANGUAGE", "FR");

        countryController.getCountries(request);

        verify(countryService).fetchCountries(fr);
    }

    @Test
    public void shouldInvokeHealthIndicatorServiceCountryScore() {
        String countryId = "ARG";
        CountryHealthScoreDto countryHealthScoreMock = mock(CountryHealthScoreDto.class);
        when(countryHealthIndicatorService.fetchCountryHealthScore(countryId)).thenReturn(countryHealthScoreMock);
        CountryHealthScoreDto healthIndicatorForGivenCountryCode = countryController.getHealthIndicatorForGivenCountryCode(countryId);
        assertThat(healthIndicatorForGivenCountryCode, is(countryHealthScoreMock));
        verify(countryHealthIndicatorService).fetchCountryHealthScore(countryId);
    }

    @Test
    public void shouldSubmitHealthIndicators() {
        GdhiQuestionnaire mock = mock(GdhiQuestionnaire.class);
        doNothing().when(countryHealthDataService).submit(mock);
        when(countryHealthDataService.validateRequiredFields(mock)).thenReturn(true);
        countryController.submitHealthIndicatorsFor(mock);
        verify(countryHealthDataService).submit(mock);
    }

    @Test
    public void shouldSaveCorrectedHealthIndicators() {
        GdhiQuestionnaire mock = mock(GdhiQuestionnaire.class);
        doNothing().when(countryHealthDataService).submit(mock);
        countryController.saveCorrectionsFor(mock);
        verify(countryHealthDataService).saveCorrection(mock);
    }

    @Test
    public void shouldSaveHealthIndicators() {
        GdhiQuestionnaire mock = mock(GdhiQuestionnaire.class);
        doNothing().when(countryHealthDataService).save(mock, DRAFT.name());
        countryController.saveHealthIndicatorsFor(mock);
        verify(countryHealthDataService).save(mock, DRAFT.name());
    }
    @Test
    public void shouldPublishHealthIndicators() {
        GdhiQuestionnaire mock = mock(GdhiQuestionnaire.class);
        doNothing().when(countryHealthDataService).publish(mock);
        when(countryHealthDataService.validateRequiredFields(mock)).thenReturn(true);
        countryController.publishHealthIndicatorsFor(mock);
        verify(countryHealthDataService).publish(mock);
    }

    @Test
    public void shouldFetchCountrySummary() {
        CountrySummaryDto countrySummary = mock(CountrySummaryDto.class);
        String countryId = "IND";
        when(countryService.fetchCountrySummary(countryId)).thenReturn(countrySummary);
        CountrySummaryDto actualCountrySummary = countryController.fetchCountrySummary(countryId);
        assertThat(actualCountrySummary, is(countrySummary));
    }

    @Test
    public void shouldFetchTheDevelopmentIndicators() {
        String countryId = "ARG";
        DevelopmentIndicator developmentIndicator = new DevelopmentIndicator();

        when(developmentIndicatorService.fetchCountryDevelopmentScores(countryId)).thenReturn(developmentIndicator);

        DevelopmentIndicator actualDevelopmentIndicator = countryController.
                getDevelopmentIndicatorForGivenCountryCode(countryId);

        verify(developmentIndicatorService).fetchCountryDevelopmentScores(countryId);

        assertEquals(developmentIndicator.getCountryId(), actualDevelopmentIndicator.getCountryId());
    }

    @Test
    public void shouldExportGlobalData() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        countryController.exportGlobalData(request, response);
        verify(countryHealthIndicatorService).createGlobalHealthIndicatorInExcel(request, response);
    }

    @Test
    public void shouldExportDataForAGivenCountry() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        countryController.exportCountryDetails(request, response, "IND");
        verify(countryHealthIndicatorService).createHealthIndicatorInExcelFor("IND", request, response);
    }

    @Test
    public void shouldSaveCountrySummaryAsNewStatus() throws Exception {
        String countryId = "IND";
        UUID countryUUID = UUID.randomUUID();
        CountryUrlGenerationStatusDto expected = new CountryUrlGenerationStatusDto(countryId, true, null);
        when(countryHealthDataService.saveNewCountrySummary(countryUUID)).thenReturn(expected);

        CountryUrlGenerationStatusDto actualResponse = countryController.saveNewCountrySummary(countryUUID);

        assertSame(actualResponse, expected);
    }

    @Test
    public void shouldDeleteCountryData() throws Exception {
        UUID countryUUID = UUID.randomUUID();
        doNothing().when(countryHealthDataService).deleteCountryData(countryUUID);

        countryController.deleteCountryData(countryUUID);

        verify(countryHealthDataService).deleteCountryData(countryUUID);
    }

    @Test
    public void shouldGetAllCountryStatusSummaries() {
        when(countryHealthDataService.getAllCountryStatusSummaries()).thenReturn(emptyMap());
        countryController.getAllCountryStatusSummaries();
        verify(countryHealthDataService).getAllCountryStatusSummaries();
    }

    @Test
    public void shouldGetQuestionnaireForPublishedCountry() {
        UUID countryUUID = UUID.randomUUID();
        GdhiQuestionnaire gdhiQuestionnaire = mock(GdhiQuestionnaire.class);
        when(countryService.getDetails(countryUUID,true)).thenReturn(gdhiQuestionnaire);
        countryController.getQuestionnaireForPublishedCountry(countryUUID);
        verify(countryService).getDetails(countryUUID,true);
    }

    @Test
    public void shouldGetGlobalBenchmarkDetailsFor() {
        String countryID = "IND";
        Integer benchmarkType = -1;
        when(countryHealthDataService.getBenchmarkDetailsFor(countryID, benchmarkType)).thenReturn(new HashMap<>());
        countryController.getBenchmarkDetailsFor(countryID, benchmarkType);
        verify(countryHealthDataService).getBenchmarkDetailsFor(countryID, benchmarkType);
    }

    @Test
    public void shouldCalculatePhaseForAllCountries() {
        doNothing().when(countryHealthDataService).calculatePhaseForAllCountries();
        countryController.calculateCountryPhase();
        verify(countryHealthDataService).calculatePhaseForAllCountries();
    }

    @Test
    public void should(){
        LanguageCode en = LanguageCode.valueOf("EN");
        System.out.println(en);
    }
}