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
        List<HealthIndicatorDto> healthIndicatorDtos = asList(new HealthIndicatorDto(1, 1, "PUBLISHED", 2, "Text"));
        GdhiQuestionnaire gdhiQuestionnaire = GdhiQuestionnaire.builder().countryId("ARG")
                .countrySummary(countrySummaryDetailDto)
                .healthIndicators(healthIndicatorDtos).build();

        countryHealthDataService.save(gdhiQuestionnaire);

        ArgumentCaptor<CountrySummary> summaryCaptor = ArgumentCaptor.forClass(CountrySummary.class);
        ArgumentCaptor<CountryHealthIndicator> healthIndicatorsCaptorList = ArgumentCaptor.forClass(CountryHealthIndicator.class);
        InOrder inOrder = inOrder(iCountryResourceLinkRepository, iCountrySummaryRepository, iCountryHealthIndicatorRepository);
        inOrder.verify(iCountryResourceLinkRepository).deleteResources("ARG");
        inOrder.verify(iCountrySummaryRepository).save(summaryCaptor.capture());
        inOrder.verify(iCountryHealthIndicatorRepository).save(healthIndicatorsCaptorList.capture());
        CountrySummary summaryCaptorValue = summaryCaptor.getValue();
        assertThat(summaryCaptorValue.getCountrySummaryId().getCountryId(), is("ARG"));
        assertThat(summaryCaptorValue.getSummary(), is("Summary 1"));
        assertThat(summaryCaptorValue.getCountryResourceLinks().get(0).getLink(), is("Res 1"));
        assertThat(healthIndicatorsCaptorList.getValue().getCountryHealthIndicatorId().getCategoryId(), is(1));
    }

    @Test
    public void shouldSendEmailOnSuccessfulSaveOfCountryDetailsAndIndicators() throws Exception {
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

        countryHealthDataService.save(gdhiQuestionnaire);

        verify(mailerService).send(country, feeder, feederRole, contactEmail);
    }

    @Test
    public void shouldSaveAsNewStatus() throws Exception {



        String countryId = "BGD";

        String status1 = null;
        String expectedMessage1 = "URL Generated Successfully";
        when(iCountrySummaryRepository.getCountrySummaryStatus(anyString())).thenReturn(status1);
        String actualMessage1 = countryHealthDataService.saveCountrySummaryAsNewStatusWhileGeneratingURL(countryId);

        assertEquals(expectedMessage1 , actualMessage1);

        String status2 = "NEW";
        String expectedMessage2 = "URL Already Generated";
        when(iCountrySummaryRepository.getCountrySummaryStatus(anyString())).thenReturn(status2);
        String actualMessage2 = countryHealthDataService.saveCountrySummaryAsNewStatusWhileGeneratingURL(countryId);
        assertEquals(expectedMessage2 , actualMessage2);


        String status3 = "DRAFT";
        String expectedMessage3 = "Form is currently in DRAFT status";
        when(iCountrySummaryRepository.getCountrySummaryStatus(anyString())).thenReturn(status3);
        String actualMessage3 = countryHealthDataService.saveCountrySummaryAsNewStatusWhileGeneratingURL(countryId);
        assertEquals(expectedMessage3 , actualMessage3);

        String status4 = "REVIEW PENDING";
        String expectedMessage4 = "Form is currently in REVIEW PENDING status";
        when(iCountrySummaryRepository.getCountrySummaryStatus(anyString())).thenReturn(status4);
        String actualMessage4 = countryHealthDataService.saveCountrySummaryAsNewStatusWhileGeneratingURL(countryId);
        assertEquals(expectedMessage4 , actualMessage4);


    }


}