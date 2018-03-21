package it.gdhi.service;

import it.gdhi.dto.CountrySummaryDto;
import it.gdhi.dto.GdhiQuestionnaire;
import it.gdhi.dto.HealthIndicatorDto;
import it.gdhi.model.Country;
import it.gdhi.model.CountryHealthIndicator;
import it.gdhi.model.CountryResourceLink;
import it.gdhi.model.CountrySummary;
import it.gdhi.model.id.CountryHealthIndicatorId;
import it.gdhi.model.id.CountryResourceLinkId;
import it.gdhi.repository.ICountryRepository;
import it.gdhi.repository.ICountryResourceLinkRepository;
import it.gdhi.repository.ICountrySummaryRepository;
import it.gdhi.repository.ICountryHealthIndicatorRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.inOrder;

@RunWith(MockitoJUnitRunner.class)
public class CountryServiceTest {

    @InjectMocks
    CountryService countryService;
    @Mock
    ICountryRepository countryDetailRepository;
    @Mock
    ICountrySummaryRepository iCountrySummaryRepository;
    @Mock
    ICountryResourceLinkRepository iCountryResourceLinkRepository;
    @Mock
    ICountryHealthIndicatorRepository iCountryHealthIndicatorRepository;
    @Mock
    MailerService mailerService;

    @Test
    public void shouldInsertTestData() {
        countryService.fetchCountries();
        verify(countryDetailRepository).findAll();
    }

    @Test
    public void shouldReturnCountrySummaryDetails() {
        String countryId = "ARG";
        String summary = "Summary";
        String contactName = "Contact Name";
        String contactDesignation = "Contact Designation";
        String contactOrganization = "Contact Organization";
        String link1 = "Link 1";
        String link2 = "Link 2";
        CountryResourceLink countryResourceLink1 = new CountryResourceLink(new CountryResourceLinkId("ARG", link1));
        CountryResourceLink countryResourceLink2 = new CountryResourceLink(new CountryResourceLinkId("ARG", link2));
        List<CountryResourceLink> countryResourceLinks = asList(countryResourceLink1, countryResourceLink2);
        CountrySummary countrySummary = CountrySummary.builder()
                .countryId(countryId)
                .summary(summary)
                .country(new Country(countryId, "Argentina"))
                .contactName(contactName)
                .contactDesignation(contactDesignation)
                .contactOrganization(contactOrganization)
                .contactEmail("email")
                .dataFeederName("feeder name")
                .dataFeederRole("feeder role")
                .dataFeederEmail("email")
                .dataCollectorName("coll name")
                .dataFeederRole("coll role")
                .dataCollectorEmail("coll email")
                .collectedDate(new Date())
                .countryResourceLinks(countryResourceLinks)
                .build();
        when(iCountrySummaryRepository.findOne(countryId)).thenReturn(countrySummary);

        CountrySummaryDto countrySummaryDto = countryService.fetchCountrySummary(countryId);

        assertThat(countrySummaryDto.getCountryId(), is(countryId));
        assertThat(countrySummaryDto.getContactName(), is(contactName));
        assertThat(countrySummaryDto.getContactDesignation(), is(contactDesignation));
        assertThat(countrySummaryDto.getContactOrganization(), is(contactOrganization));
        assertThat(countrySummaryDto.getContactEmail(), is(countrySummary.getContactEmail()));
        assertThat(countrySummaryDto.getDataFeederName(), is(countrySummary.getDataFeederName()));
        assertThat(countrySummaryDto.getDataFeederRole(), is(countrySummary.getDataFeederRole()));
        assertThat(countrySummaryDto.getDataFeederEmail(), is(countrySummary.getDataFeederEmail()));
        assertThat(countrySummaryDto.getDataCollectorName(), is(countrySummary.getDataCollectorName()));
        assertThat(countrySummaryDto.getDataCollectorRole(), is(countrySummary.getDataCollectorRole()));
        assertThat(countrySummaryDto.getDataCollectorEmail(), is(countrySummary.getDataCollectorEmail()));
        assertThat(countrySummaryDto.getCollectedDate(), is(countrySummary.getCollectedDate()));
        assertThat(countrySummaryDto.getSummary(), is(summary));
        assertThat(countrySummaryDto.getResources(), Matchers.containsInAnyOrder(link1, link2));
    }

    @Test
    public void shouldReturnEmptyCountrySummaryObjectWhenNoCountrySummaryPresent() {
        String countryId = "ARG";
        when(iCountrySummaryRepository.findOne(countryId)).thenReturn(null);
        CountrySummaryDto countrySummaryDto = countryService.fetchCountrySummary(countryId);
        assertNull(countrySummaryDto.getCountryId());
    }

    @Test
    public void shouldSaveDetailsForACountry() throws Exception {
        List<String> resourceLinks = asList("Res 1");
        CountrySummaryDto countrySummaryDetailDto = CountrySummaryDto.builder().summary("Summary 1")
                .resources(resourceLinks).build();
        List<HealthIndicatorDto> healthIndicatorDtos = asList(new HealthIndicatorDto(1, 1, 2, "Text"));
        GdhiQuestionnaire gdhiQuestionnaire = GdhiQuestionnaire.builder().countryId("ARG")
                .countrySummary(countrySummaryDetailDto)
                .healthIndicators(healthIndicatorDtos).build();
        countryService.save(gdhiQuestionnaire);
        ArgumentCaptor<CountrySummary> summaryCaptor = ArgumentCaptor.forClass(CountrySummary.class);
        ArgumentCaptor<CountryHealthIndicator> healthIndicatorsCaptorList = ArgumentCaptor.forClass(CountryHealthIndicator.class);
        InOrder inOrder = inOrder(iCountryResourceLinkRepository, iCountrySummaryRepository, iCountryHealthIndicatorRepository);
        inOrder.verify(iCountryResourceLinkRepository).deleteResources("ARG");
        inOrder.verify(iCountrySummaryRepository).save(summaryCaptor.capture());
        inOrder.verify(iCountryHealthIndicatorRepository).save(healthIndicatorsCaptorList.capture());
        CountrySummary summaryCaptorValue = summaryCaptor.getValue();
        assertThat(summaryCaptorValue.getCountryId(), is("ARG"));
        assertThat(summaryCaptorValue.getSummary(), is("Summary 1"));
        assertThat(summaryCaptorValue.getCountryResourceLinks().get(0).getLink(), is("Res 1"));
        assertThat(healthIndicatorsCaptorList.getValue().getCountryHealthIndicatorId().getCategoryId(), is(1));
    }

    @Test
    public void shouldSendEmailOnSuccessfulSaveOfCountryDetailsAndIndicators() throws Exception {
        String countryId = "ARG";
        Country country = new Country(countryId, "Argentina");
        List<String> resourceLinks = asList("Res 1");
        String feeder = "feeder";
        String feederRole = "feeder role";
        String contactEmail = "contact@test.com";
        CountrySummaryDto countrySummaryDetailDto = CountrySummaryDto.builder().summary("Summary 1")
                .dataFeederName(feeder)
                .dataFeederRole(feederRole)
                .contactEmail(contactEmail)
                .resources(resourceLinks).build();

        List<HealthIndicatorDto> healthIndicatorDtos = asList(new HealthIndicatorDto(1, 1, 2, "Text"));
        GdhiQuestionnaire gdhiQuestionnaire = GdhiQuestionnaire.builder().countryId(countryId)
                .countrySummary(countrySummaryDetailDto)
                .healthIndicators(healthIndicatorDtos).build();

        when(iCountrySummaryRepository.save(any(CountrySummary.class))).thenReturn(CountrySummary.builder().build());
        when(iCountryHealthIndicatorRepository.save(any(CountryHealthIndicator.class))).thenReturn(CountryHealthIndicator.builder().build());
        when(countryDetailRepository.find(countryId)).thenReturn(country);

        countryService.save(gdhiQuestionnaire);

        verify(mailerService).send(country, feeder, feederRole, contactEmail);
    }

    @Test
    public void shouldGetGlobalHealthScoreDto() throws Exception {
        String countryId = "IND";
        CountrySummary countrySummary = CountrySummary.builder()
                .countryId(countryId)
                .country(new Country("IND", "India"))
                .summary("summary")
                .contactName("contactName")
                .contactDesignation("contact designation")
                .contactOrganization("contact org")
                .contactEmail("contact email")
                .dataFeederName("feeder name")
                .dataFeederRole("feeder role")
                .dataFeederEmail("feeder email")
                .dataCollectorName("collector name")
                .dataCollectorRole("collector role")
                .dataCollectorEmail("collector email")
                .collectedDate(new Date())
                .countryResourceLinks(asList(new CountryResourceLink(new CountryResourceLinkId(countryId, "link"))))
                .build();

        when(iCountrySummaryRepository.findOne(countryId)).thenReturn(countrySummary);
        CountryHealthIndicator indicator1 = CountryHealthIndicator.builder()
                .countryHealthIndicatorId(new CountryHealthIndicatorId(countryId, 1, 2))
                .score(5)
                .build();
        CountryHealthIndicator indicator2 = CountryHealthIndicator.builder()
                .countryHealthIndicatorId(new CountryHealthIndicatorId(countryId, 2, 3))
                .score(4)
                .build();
        List<CountryHealthIndicator> countryHealthIndicators = asList(indicator1, indicator2);
        when(iCountryHealthIndicatorRepository.findHealthIndicatorsFor(countryId)).thenReturn(countryHealthIndicators);

        GdhiQuestionnaire details = countryService.getDetails(countryId);

        assertSummary(countrySummary, details.getCountrySummary());
        assertIndicators(countryHealthIndicators, details.getHealthIndicators());
    }

    @Test
    public void shouldHandleCountriesNotAvailable() throws Exception {
        String countryId = "IND";

        when(iCountrySummaryRepository.findOne(countryId)).thenReturn(null);

        when(iCountryHealthIndicatorRepository.findHealthIndicatorsFor(countryId)).thenReturn(asList());

        GdhiQuestionnaire details = countryService.getDetails(countryId);

        assertNull(details.getCountrySummary());
    }

    private void assertIndicators(List<CountryHealthIndicator> expectedCountryHealthIndicators, List<HealthIndicatorDto> actualHealthIndicators) {
        assertEquals(expectedCountryHealthIndicators.size(), actualHealthIndicators.size());
        expectedCountryHealthIndicators.forEach(expected -> {
            HealthIndicatorDto actualIndicator = actualHealthIndicators.stream()
                    .filter(actual -> actual.getCategoryId().equals(expected.getCountryHealthIndicatorId().getCategoryId())
                            && actual.getIndicatorId().equals(expected.getCountryHealthIndicatorId().getIndicatorId()))
                    .findFirst()
                    .get();
            assertEquals(expected.getScore(), actualIndicator.getScore());
            assertEquals(expected.getSupportingText(), actualIndicator.getSupportingText());
        });
    }

    private void assertSummary(CountrySummary expectedCountrySummary, CountrySummaryDto actualCountrySummary) {
        assertEquals(expectedCountrySummary.getContactName(), actualCountrySummary.getContactName());
        assertEquals(expectedCountrySummary.getCountry().getName(), actualCountrySummary.getCountryName());
        assertEquals(expectedCountrySummary.getSummary(), actualCountrySummary.getSummary());
        assertEquals(expectedCountrySummary.getContactDesignation(), actualCountrySummary.getContactDesignation());
        assertEquals(expectedCountrySummary.getContactOrganization(), actualCountrySummary.getContactOrganization());
        assertEquals(expectedCountrySummary.getContactEmail(), actualCountrySummary.getContactEmail());
        assertEquals(expectedCountrySummary.getDataFeederName(), actualCountrySummary.getDataFeederName());
        assertEquals(expectedCountrySummary.getDataFeederRole(), actualCountrySummary.getDataFeederRole());
        assertEquals(expectedCountrySummary.getDataFeederEmail(), actualCountrySummary.getDataFeederEmail());
        assertEquals(expectedCountrySummary.getDataCollectorName(), actualCountrySummary.getDataCollectorName());
        assertEquals(expectedCountrySummary.getDataCollectorEmail(), actualCountrySummary.getDataCollectorEmail());
        assertEquals(expectedCountrySummary.getCollectedDate(), actualCountrySummary.getCollectedDate());
        assertEquals(expectedCountrySummary.getCountryResourceLinks().stream().map(CountryResourceLink::getLink).collect(Collectors.toList()),
                actualCountrySummary.getResources());
    }
}