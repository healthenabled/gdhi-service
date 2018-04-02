package it.gdhi.service;

import it.gdhi.dto.CountrySummaryDto;
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

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static it.gdhi.utils.Constants.*;

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
    public void shouldSaveAsNewStatusWhenLiveDataNotPresentAlready() throws Exception {



        String countryId = "BGD";

        List status1 = asList(COUNTRY_DATA_NOT_PRESENT);
        String expectedMessage1 = URL_GENERATED_SUCCESSFULLY_MESSAGE;
        when(iCountrySummaryRepository.getAllStatus(anyString())).thenReturn(status1);
        String actualMessage1 = countryHealthDataService.saveCountrySummaryAsNew(countryId).getMsg();
        assertEquals(expectedMessage1 , actualMessage1);

        List status2 = asList(NEW_STATUS);
        String expectedMessage2 = AWAITING_SUBMISSION_MESSAGE;
        when(iCountrySummaryRepository.getAllStatus(anyString())).thenReturn(status2);
        String actualMessage2 = countryHealthDataService.saveCountrySummaryAsNew(countryId).getMsg();
        assertEquals(expectedMessage2 , actualMessage2);


        List status3 = asList(DRAFT_STATUS);
        String expectedMessage3 = AWAITING_SUBMISSION_MESSAGE;
        when(iCountrySummaryRepository.getAllStatus(anyString())).thenReturn(status3);
        String actualMessage3 = countryHealthDataService.saveCountrySummaryAsNew(countryId).getMsg();
        assertEquals(expectedMessage3 , actualMessage3);

        List status4 = asList(REVIEW_PENDING_STATUS);
        String expectedMessage4 = PENDING_REVIEW_MESSAGE;
        when(iCountrySummaryRepository.getAllStatus(anyString())).thenReturn(status4);
        String actualMessage4 = countryHealthDataService.saveCountrySummaryAsNew(countryId).getMsg();
        assertEquals(expectedMessage4 , actualMessage4);

        List status5 = asList(PUBLISHED_STATUS);
        String expectedMessage5 = ALREADY_PUBLISHED_MESSAGE;
        when(iCountrySummaryRepository.getAllStatus(anyString())).thenReturn(status5);
        String actualMessage5 = countryHealthDataService.saveCountrySummaryAsNew(countryId).getMsg();
        assertEquals(expectedMessage5 , actualMessage5);


    }


}