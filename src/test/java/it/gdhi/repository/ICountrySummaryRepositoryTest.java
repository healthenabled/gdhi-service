package it.gdhi.repository;

import it.gdhi.model.Country;
import it.gdhi.model.CountryResourceLink;
import it.gdhi.model.id.CountryResourceLinkId;
import it.gdhi.model.CountrySummary;
import it.gdhi.model.id.CountrySummaryId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ActiveProfiles("test")
public class ICountrySummaryRepositoryTest {

    @Autowired
    private ICountrySummaryRepository iCountrySummaryRepository;

    @Autowired
    private EntityManager entityManager;

    private void addCountrySummary(String countryId, String countryName, String alpha2code, String summary, List<CountryResourceLink> countryResourceLinkList) {
        String status = "PUBLISHED";
        CountrySummary countrySummary = CountrySummary.builder()
                .countrySummaryId(new CountrySummaryId(countryId, status))
                .summary(summary)
                .country(new Country(countryId, countryName, UUID.randomUUID(), alpha2code))
                .contactName("Contact Name")
                .contactDesignation("Contact Designation")
                .contactOrganization("contactOrganization")
                .contactEmail("email")
                .dataFeederName("feeder name")
                .dataFeederRole("feeder role")
                .dataFeederEmail("email")
                .dataCollectorName("coll name")
                .dataCollectorRole("coll role")
                .dataFeederRole("coll role")
                .dataCollectorEmail("coll email")
                .countryResourceLinks(countryResourceLinkList)
                .build();
        iCountrySummaryRepository.save(countrySummary);
    }

    @Test
    public void shouldFetchPopulationGivenCountryCode() {
        addCountrySummary("NZL", "New Zealand", "NZ","NZL summary", new ArrayList<>());
        CountrySummary actual = iCountrySummaryRepository.findOne("NZL");
        assertThat(actual.getCountrySummaryId().getCountryId(), is("NZL"));
        assertThat(actual.getSummary(), is("NZL summary"));
        assertThat(actual.getContactName(), is("Contact Name"));
        assertThat(actual.getContactDesignation(), is("Contact Designation"));
        assertThat(actual.getCountry().getName(), is("New Zealand"));
    }

    @Test
    public void shouldFetchCountryCodeCaseInsensitive() {
        addCountrySummary("NZL", "New Zealand", "NZ","NZL summary", new ArrayList<>());
        CountrySummary actual = iCountrySummaryRepository.findOne("nzl");
        assertThat(actual.getCountrySummaryId().getCountryId(), is("NZL"));
        assertThat(actual.getSummary(), is("NZL summary"));
        assertThat(actual.getContactName(), is("Contact Name"));
        assertThat(actual.getContactDesignation(), is("Contact Designation"));
        assertThat(actual.getCountry().getName(), is("New Zealand"));
    }

    @Test
    public void shouldSaveCountrySummaryAlongWithResourceLinks() {
        addCountrySummary("NZL", "New Zealand", "NZ", "NZL summary 1",
                asList(new CountryResourceLink(new CountryResourceLinkId("NZL", "www.google.com","PUBLISHED"))));

        CountrySummary nzl1 = iCountrySummaryRepository.findOne("NZL");
        assertThat(nzl1.getCountrySummaryId().getCountryId(), is("NZL"));
        assertThat(nzl1.getSummary(), is("NZL summary 1"));
        assertThat(nzl1.getContactName(), is("Contact Name"));
        assertThat(nzl1.getCountryResourceLinks().get(0).getCountryResourceLinkId().getCountryId(), is("NZL"));
        assertThat(nzl1.getCountryResourceLinks().get(0).getCountryResourceLinkId().getLink(), is("www.google.com"));

        addCountrySummary("NZL", "New Zealand", "NZ","NZL summary 2",
                asList(new CountryResourceLink(new CountryResourceLinkId("NZL", "www.google.com","PUBLISHED"))));

        CountrySummary nzl2 = iCountrySummaryRepository.findOne("NZL");
        assertThat(nzl2.getCountrySummaryId().getCountryId(), is("NZL"));
        assertThat(nzl2.getSummary(), is("NZL summary 2"));
    }

}