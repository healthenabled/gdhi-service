package it.gdhi.service;

import it.gdhi.dto.CountryDTO;
import it.gdhi.dto.CountrySummaryDto;
import it.gdhi.dto.GdhiQuestionnaire;
import it.gdhi.dto.HealthIndicatorDto;
import it.gdhi.model.*;
import it.gdhi.model.id.CountryHealthIndicatorId;
import it.gdhi.model.id.CountryResourceLinkId;
import it.gdhi.model.id.CountrySummaryId;
import it.gdhi.repository.ICountryHealthIndicatorRepository;
import it.gdhi.repository.ICountryRepository;
import it.gdhi.repository.ICountrySummaryRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableList.of;
import static it.gdhi.utils.LanguageCode.EN;
import static it.gdhi.utils.LanguageCode.FR;
import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountryServiceTest {

    @InjectMocks
    CountryService countryService;
    @Mock
    ICountryRepository countryDetailRepository;
    @Mock
    ICountrySummaryRepository iCountrySummaryRepository;
    @Mock
    ICountryHealthIndicatorRepository iCountryHealthIndicatorRepository;

    @Test
    public void shouldInsertTestData() {
        countryService.fetchCountries(EN);
        verify(countryDetailRepository).findAll();
    }

    @Test
    public void shouldReturnCountriesGivenUserLanguageIsFrench() {
        Country chile = new Country("CHL", "Chile", randomUUID(), "CH", "ChileSpanishName", "ChileFrenchName", "ChilePortugueseName", "ChileArabicName");
        Country malaysia = new Country("MYL", "Malaysia", randomUUID(), "MY", "MalaysiaSpanishName", "MalaysiaFrenchName", "MalaysiaPortugueseName", "MalaysiaArabicName");
        CountryDTO expectedChileDTO = new CountryDTO("CHL", "ChileFrenchName", chile.getUniqueId(), "CH");
        CountryDTO expectedMalaysiaDTO = new CountryDTO("MYL", "MalaysiaFrenchName", malaysia.getUniqueId(), "MY");

        when(countryDetailRepository.findAll()).thenReturn(Arrays.asList(chile, malaysia));
        List<CountryDTO> actualCountries = countryService.fetchCountries(FR);

        assertEquals(of(expectedChileDTO, expectedMalaysiaDTO), actualCountries);
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
        String status = "PUBLISHED";
        CountryResourceLink countryResourceLink1 = new CountryResourceLink(new CountryResourceLinkId("ARG", link1,
                status), new Date(), null);
        CountryResourceLink countryResourceLink2 = new CountryResourceLink(new CountryResourceLinkId("ARG", link2,
                status), new Date(), null);
        List<CountryResourceLink> countryResourceLinks = asList(countryResourceLink1, countryResourceLink2);
        CountrySummary countrySummary = CountrySummary.builder()
                .countrySummaryId(new CountrySummaryId(countryId, status))
                .summary(summary)
                .country(new Country(countryId, "Argentina", randomUUID(), "AR"))
                .contactName(contactName)
                .contactDesignation(contactDesignation)
                .contactOrganization(contactOrganization)
                .contactEmail("email")
                .dataFeederName("feeder name")
                .dataFeederRole("feeder role")
                .dataFeederEmail("email")
                .dataApproverName("coll name")
                .dataApproverRole("coll role")
                .dataApproverEmail("coll email")
                .collectedDate(new Date())
                .countryResourceLinks(countryResourceLinks)
                .build();
        when(iCountrySummaryRepository.findByCountryAndStatus(countryId, status)).thenReturn(countrySummary);

        CountrySummaryDto countrySummaryDto = countryService.fetchCountrySummary(countryId);

        assertThat(countrySummaryDto.getCountryId(), is(countryId));
        assertThat(countrySummaryDto.getContactName(), is(contactName));
        assertThat(countrySummaryDto.getContactDesignation(), is(contactDesignation));
        assertThat(countrySummaryDto.getContactOrganization(), is(contactOrganization));
        assertThat(countrySummaryDto.getContactEmail(), is(countrySummary.getContactEmail()));
        assertThat(countrySummaryDto.getDataFeederName(), is(countrySummary.getDataFeederName()));
        assertThat(countrySummaryDto.getDataFeederRole(), is(countrySummary.getDataFeederRole()));
        assertThat(countrySummaryDto.getDataFeederEmail(), is(countrySummary.getDataFeederEmail()));
        assertThat(countrySummaryDto.getDataApproverName(), is(countrySummary.getDataApproverName()));
        assertThat(countrySummaryDto.getDataApproverRole(), is(countrySummary.getDataApproverRole()));
        assertThat(countrySummaryDto.getDataApproverEmail(), is(countrySummary.getDataApproverEmail()));
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
    public void shouldGetGlobalHealthScoreDto() throws Exception {
        String countryId = "IND";
        String statusValue = "PUBLISHED";
        UUID countryUUID = randomUUID();
        Country country = new Country(countryId, "India", countryUUID, "IN");
        CountrySummary countrySummary = CountrySummary.builder()
                .countrySummaryId(new CountrySummaryId(countryId, statusValue))
                .country(country)
                .summary("summary")
                .contactName("contactName")
                .contactDesignation("contact designation")
                .contactOrganization("contact org")
                .contactEmail("contact email")
                .dataFeederName("feeder name")
                .dataFeederRole("feeder role")
                .dataFeederEmail("feeder email")
                .dataApproverName("collector name")
                .dataApproverRole("collector role")
                .dataApproverRole("collector email")
                .collectedDate(new Date())
                .countryResourceLinks(asList(new CountryResourceLink(new CountryResourceLinkId(countryId, "link",
                        statusValue), new Date(), null)))
                .build();

        when(iCountrySummaryRepository.findAll(countryId)).thenReturn(asList(countrySummary));
        when(countryDetailRepository.findByUUID(countryUUID)).thenReturn(country);
        CountryHealthIndicator indicator1 = CountryHealthIndicator.builder()
                .countryHealthIndicatorId(new CountryHealthIndicatorId(countryId, 1, 2, statusValue))
                .indicator(new Indicator(2,"Some indicator","some code",1,null,new ArrayList<>(),"some def"))
                .score(5)
                .build();
        CountryHealthIndicator indicator2 = CountryHealthIndicator.builder()
                .countryHealthIndicatorId(new CountryHealthIndicatorId(countryId, 2, 3, statusValue))
                .indicator(new Indicator(3,"Some indicator","some code",2,null,new ArrayList<>(),"some def"))
                .score(4)
                .build();
        List<CountryHealthIndicator> countryHealthIndicators = asList(indicator1, indicator2);
        when(iCountryHealthIndicatorRepository.findByCountryIdAndStatus(countryId, statusValue)).thenReturn(countryHealthIndicators);

        GdhiQuestionnaire details = countryService.getDetails(countryUUID,false);

        assertSummary(countrySummary, details.getCountrySummary());
        assertIndicators(countryHealthIndicators, details.getHealthIndicators());
    }

    @Test
    public void shouldHandleCountriesNotAvailable() throws Exception {
        String countryId = "IND";
        UUID countryUUID = randomUUID();
        Country country = new Country(countryId, "India", countryUUID, "IN");

        when(iCountrySummaryRepository.findAll(countryId)).thenReturn(null);

        when(iCountryHealthIndicatorRepository.findByCountryIdAndStatus(countryId, null)).thenReturn(Collections.emptyList());

        when(countryDetailRepository.findByUUID(countryUUID)).thenReturn(country);
        GdhiQuestionnaire details = countryService.getDetails(countryUUID,false);

        assertNull(details);
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
        assertEquals(expectedCountrySummary.getDataApproverName(), actualCountrySummary.getDataApproverName());
        assertEquals(expectedCountrySummary.getDataApproverEmail(), actualCountrySummary.getDataApproverEmail());
        assertEquals(expectedCountrySummary.getCollectedDate(), actualCountrySummary.getCollectedDate());
        assertEquals(expectedCountrySummary.getCountryResourceLinks().stream().map(CountryResourceLink::getLink).collect(Collectors.toList()),
                actualCountrySummary.getResources());
    }
}