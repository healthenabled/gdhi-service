package it.gdhi.service;

import it.gdhi.dto.CountrySummaryDto;
import it.gdhi.dto.CountryUrlGenerationStatusDto;
import it.gdhi.dto.GdhiQuestionnaire;
import it.gdhi.dto.HealthIndicatorDto;
import it.gdhi.model.Country;
import it.gdhi.model.CountryHealthIndicator;
import it.gdhi.model.CountrySummary;
import it.gdhi.repository.ICountryHealthIndicatorRepository;
import it.gdhi.repository.ICountryRepository;
import it.gdhi.repository.ICountryResourceLinkRepository;
import it.gdhi.repository.ICountrySummaryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static it.gdhi.utils.Constants.NEW_STATUS;
import static it.gdhi.utils.FormStatus.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CountryHealthDataServiceTest {

    @InjectMocks
    CountryHealthDataService countryHealthDataService;
    @Mock
    ICountrySummaryRepository iCountrySummaryRepository;
    @Mock
    ICountryResourceLinkRepository iCountryResourceLinkRepository;
    @Mock
    ICountryHealthIndicatorRepository iCountryHealthIndicatorRepository;
    @Mock
    MailerService mailerService;
    @Mock
    ICountryRepository countryDetailRepository;

    @Test
    public void shouldSaveDetailsForACountry() throws Exception {
        List<String> resourceLinks = asList("Res 1");
        CountrySummaryDto countrySummaryDetailDto = CountrySummaryDto.builder().summary("Summary 1")
                .resources(resourceLinks).build();
        String status = "PUBLISHED";
        List<HealthIndicatorDto> healthIndicatorDtos = asList(new HealthIndicatorDto(1, 1, status, 2, "Text"));
        String countryId = "ARG";
        GdhiQuestionnaire gdhiQuestionnaire = GdhiQuestionnaire.builder().countryId(countryId)
                .countrySummary(countrySummaryDetailDto)
                .healthIndicators(healthIndicatorDtos).build();

        when(iCountrySummaryRepository.getCountrySummaryStatus(countryId)).thenReturn("PUBLISHED");
        countryHealthDataService.save(gdhiQuestionnaire, false);

        ArgumentCaptor<CountrySummary> summaryCaptor = ArgumentCaptor.forClass(CountrySummary.class);
        ArgumentCaptor<CountryHealthIndicator> healthIndicatorsCaptorList = ArgumentCaptor.forClass(CountryHealthIndicator.class);
        InOrder inOrder = inOrder(iCountryResourceLinkRepository, iCountrySummaryRepository, iCountryHealthIndicatorRepository);
        inOrder.verify(iCountryResourceLinkRepository).deleteResources(countryId, status);
        inOrder.verify(iCountrySummaryRepository).save(summaryCaptor.capture());
        inOrder.verify(iCountryHealthIndicatorRepository).save(healthIndicatorsCaptorList.capture());
        CountrySummary summaryCaptorValue = summaryCaptor.getValue();
        assertThat(summaryCaptorValue.getCountrySummaryId().getCountryId(), is(countryId));
        assertThat(summaryCaptorValue.getSummary(), is("Summary 1"));
        assertThat(summaryCaptorValue.getCountryResourceLinks().get(0).getLink(), is("Res 1"));
        assertThat(healthIndicatorsCaptorList.getValue().getCountryHealthIndicatorId().getCategoryId(), is(1));
    }

    @Test
    public void shouldSendEmailOnSuccessfulSubmitOfCountryDetailsAndIndicators() throws Exception {
        String countryId = "ARG";
        Country country = new Country(countryId, "Argentina",UUID.randomUUID(), "AR");
        List<String> resourceLinks = asList("Res 1");
        String feeder = "feeder";
        String feederRole = "feeder role";
        String contactEmail = "contact@test.com";
        CountrySummaryDto countrySummaryDetailDto = CountrySummaryDto.builder().summary("Summary 1")
                .dataFeederName(feeder)
                .dataFeederRole(feederRole)
                .contactEmail(contactEmail)
                .resources(resourceLinks).build();

        List<HealthIndicatorDto> healthIndicatorDtos = asList(new HealthIndicatorDto(1, 1,"PUBLISHED", 2, "Text"));
        GdhiQuestionnaire gdhiQuestionnaire = GdhiQuestionnaire.builder().countryId(countryId)
                .countrySummary(countrySummaryDetailDto)
                .healthIndicators(healthIndicatorDtos).build();

        when(iCountrySummaryRepository.save(any(CountrySummary.class))).thenReturn(CountrySummary.builder().build());
        when(iCountryHealthIndicatorRepository.save(any(CountryHealthIndicator.class))).thenReturn(CountryHealthIndicator.builder().build());
        when(countryDetailRepository.find(countryId)).thenReturn(country);
        when(iCountrySummaryRepository.getCountrySummaryStatus(countryId)).thenReturn("DRAFT");
        countryHealthDataService.save(gdhiQuestionnaire, true);

        verify(mailerService).send(country, feeder, feederRole, contactEmail);
    }

    @Test
    public void shouldSaveAsNewStatusWhenCountryDoesNotHaveLiveData() throws Exception {
        String countryId = "BGD";
        ArgumentCaptor<CountrySummary> summaryCaptor = ArgumentCaptor.forClass(CountrySummary.class);

        when(iCountrySummaryRepository.getAllStatus(anyString())).thenReturn(emptyList());
        CountryUrlGenerationStatusDto countryUrlGenerationStatusDto = countryHealthDataService.saveCountrySummaryAsNew(countryId);
        assert(countryUrlGenerationStatusDto.isSuccess());
        assertNull(countryUrlGenerationStatusDto.getExistingStatus());

        verify(iCountrySummaryRepository).save(summaryCaptor.capture());
        CountrySummary summaryCaptorValue = summaryCaptor.getValue();
        assertEquals(countryId, summaryCaptorValue.getCountrySummaryId().getCountryId());
        assertEquals(NEW.toString(), summaryCaptorValue.getCountrySummaryId().getStatus());
    }

    @Test
    public void shouldSaveAsNewStatusWhenCountryHasPublishedData() throws Exception {
        String countryId = "BGD";
        ArgumentCaptor<CountrySummary> summaryCaptor = ArgumentCaptor.forClass(CountrySummary.class);

        when(iCountrySummaryRepository.getAllStatus(anyString())).thenReturn(asList(PUBLISHED.toString()));
        CountryUrlGenerationStatusDto countryUrlGenerationStatusDto = countryHealthDataService.saveCountrySummaryAsNew(countryId);
        assert(countryUrlGenerationStatusDto.isSuccess());
        assertEquals(PUBLISHED, countryUrlGenerationStatusDto.getExistingStatus());

        verify(iCountrySummaryRepository).save(summaryCaptor.capture());
        CountrySummary summaryCaptorValue = summaryCaptor.getValue();
        assertEquals(countryId, summaryCaptorValue.getCountrySummaryId().getCountryId());
        assertEquals(NEW.toString(), summaryCaptorValue.getCountrySummaryId().getStatus());
    }

    @Test
    public void shouldNotSaveNewCountrySummaryWhenItAlreadyHasNewData() throws Exception {
        String countryId = "BGD";

        when(iCountrySummaryRepository.getAllStatus(anyString())).thenReturn(asList(NEW_STATUS));

        CountryUrlGenerationStatusDto countryUrlGenerationStatusDto = countryHealthDataService.saveCountrySummaryAsNew(countryId);
        assertFalse(countryUrlGenerationStatusDto.isSuccess());
        assertEquals(NEW, countryUrlGenerationStatusDto.getExistingStatus());

        verify(iCountrySummaryRepository, never()).save(any());
    }

    @Test
    public void shouldNotSaveNewCountrySummaryWhenItAlreadyHasDraftData() throws Exception {
        String countryId = "BGD";

        when(iCountrySummaryRepository.getAllStatus(anyString())).thenReturn(asList(DRAFT.toString()));

        CountryUrlGenerationStatusDto countryUrlGenerationStatusDto = countryHealthDataService.saveCountrySummaryAsNew(countryId);
        assertFalse(countryUrlGenerationStatusDto.isSuccess());
        assertEquals(DRAFT, countryUrlGenerationStatusDto.getExistingStatus());

        verify(iCountrySummaryRepository, never()).save(any());
    }

    @Test
    public void shouldNotSaveNewCountrySummaryWhenItAlreadyHasReviewPendingData() throws Exception {
        String countryId = "BGD";

        when(iCountrySummaryRepository.getAllStatus(anyString())).thenReturn(asList(REVIEW_PENDING.toString()));

        CountryUrlGenerationStatusDto countryUrlGenerationStatusDto = countryHealthDataService.saveCountrySummaryAsNew(countryId);
        assertFalse(countryUrlGenerationStatusDto.isSuccess());
        assertEquals(REVIEW_PENDING, countryUrlGenerationStatusDto.getExistingStatus());

        verify(iCountrySummaryRepository, never()).save(any());
    }

    @Test
    public void shouldNotSaveNewCountrySummaryWhenItAlreadyHasBothPublishedAndDraftData() throws Exception {
        String countryId = "BGD";

        when(iCountrySummaryRepository.getAllStatus(anyString()))
                .thenReturn(asList(PUBLISHED.toString(), DRAFT.toString()));

        CountryUrlGenerationStatusDto countryUrlGenerationStatusDto = countryHealthDataService.saveCountrySummaryAsNew(countryId);
        assertFalse(countryUrlGenerationStatusDto.isSuccess());
        assertEquals(DRAFT, countryUrlGenerationStatusDto.getExistingStatus());

        verify(iCountrySummaryRepository, never()).save(any());
    }

    @Test
    public void shouldNotSaveNewCountrySummaryWhenItAlreadyHasBothPublishedAndNewData() throws Exception {
        String countryId = "BGD";

        when(iCountrySummaryRepository.getAllStatus(anyString()))
                .thenReturn(asList(PUBLISHED.toString(), NEW.toString()));

        CountryUrlGenerationStatusDto countryUrlGenerationStatusDto = countryHealthDataService.saveCountrySummaryAsNew(countryId);
        assertFalse(countryUrlGenerationStatusDto.isSuccess());
        assertEquals(NEW, countryUrlGenerationStatusDto.getExistingStatus());

        verify(iCountrySummaryRepository, never()).save(any());
    }
}