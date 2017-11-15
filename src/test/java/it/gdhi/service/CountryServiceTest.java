package it.gdhi.service;

import it.gdhi.dto.CountrySummaryDto;
import it.gdhi.model.CountryResourceLink;
import it.gdhi.model.CountryResourceLinkId;
import it.gdhi.model.CountrySummary;
import it.gdhi.repository.ICountryRepository;
import it.gdhi.repository.ICountryResourceLinkRepository;
import it.gdhi.repository.ICountrySummaryRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

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
        CountrySummary countrySummary = mock(CountrySummary.class);
        when(countrySummary.getCountryId()).thenReturn(countryId);
        when(countrySummary.getSummary()).thenReturn(summary);
        when(countrySummary.getContactName()).thenReturn(contactName);
        when(countrySummary.getContactDesignation()).thenReturn(contactDesignation);
        when(countrySummary.getContactOrganization()).thenReturn(contactOrganization);

        when(iCountrySummaryRepository.findOne(countryId)).thenReturn(countrySummary);
        String link1 = "Link 1";
        String link2 = "Link 2";
        CountryResourceLink countryResourceLink1 = new CountryResourceLink(new CountryResourceLinkId("ARG", link1));
        CountryResourceLink countryResourceLink2 = new CountryResourceLink(new CountryResourceLinkId("ARG", link2));
        when(iCountryResourceLinkRepository.findAllBy(countryId))
                .thenReturn(asList(countryResourceLink1, countryResourceLink2));
        CountrySummaryDto countrySummaryDto = countryService.fetchCountrySummary(countryId);
        assertThat(countrySummaryDto.getCountryId(), is(countryId));
        assertThat(countrySummaryDto.getContactName(), is(contactName));
        assertThat(countrySummaryDto.getContactDesignation(), is(contactDesignation));
        assertThat(countrySummaryDto.getContactOrganization(), is(contactOrganization));
        assertThat(countrySummaryDto.getSummary(), is(summary));
        assertThat(countrySummaryDto.getResources(), Matchers.containsInAnyOrder(link1, link2));
    }
}