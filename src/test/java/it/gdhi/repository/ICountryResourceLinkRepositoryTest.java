package it.gdhi.repository;

import it.gdhi.model.Country;
import it.gdhi.model.CountryResourceLink;
import it.gdhi.model.CountrySummary;
import it.gdhi.model.id.CountryResourceLinkId;
import it.gdhi.model.id.CountrySummaryId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ActiveProfiles("test")
public class ICountryResourceLinkRepositoryTest {

    @Autowired
    private ICountryResourceLinkRepository iCountryResourceLinkRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ICountryHealthIndicatorRepository iCountryHealthIndicatorRepository;

    @Autowired
    private ICountrySummaryRepository countrySummaryRepository;

    private void addCountrySummary(String countryId, String countryName, String alpha2code) {
        String status = "PUBLISHED";
        CountrySummary countrySummary = CountrySummary.builder()
                .countrySummaryId(new CountrySummaryId(countryId, status))
                .summary("summary")
                .country(new Country(countryId, countryName, UUID.randomUUID(), alpha2code))
                .contactName("contactName")
                .contactDesignation("contactDesignation")
                .contactOrganization("contactOrganization")
                .contactEmail("email")
                .dataFeederName("feeder name")
                .dataFeederRole("feeder role")
                .dataFeederEmail("email")
                .dataCollectorName("coll name")
                .dataCollectorRole("coll role")
                .dataFeederRole("coll role")
                .dataCollectorEmail("coll email")
                .countryResourceLinks(new ArrayList<>())
                .build();
        countrySummaryRepository.save(countrySummary);
    }

    @Test
    public void shouldFetchCountryResourceLinks() {
        addCountrySummary("NZL", "New Zealand", "NZ");
        addCountrySummary("AUS", "Australia", "AU");
        CountryResourceLink countryResourceLink1 = new CountryResourceLink(new CountryResourceLinkId("NZL", "Res 1","PUBLISHED"));
        CountryResourceLink countryResourceLink2 = new CountryResourceLink(new CountryResourceLinkId("AUS", "Res 2","PUBLISHED"));
        CountryResourceLink countryResourceLink3 = new CountryResourceLink(new CountryResourceLinkId("NZL", "Res 3","PUBLISHED"));
        entityManager.persist(countryResourceLink1);
        entityManager.persist(countryResourceLink2);
        entityManager.persist(countryResourceLink3);
        entityManager.flush();
        entityManager.clear();
        List<CountryResourceLink> actual = iCountryResourceLinkRepository.findAllBy("NZL");
        assertThat(actual.size(), is(2));
        assertThat(actual.stream().map(CountryResourceLink::getLink).collect(toList()), containsInAnyOrder("Res 1", "Res 3"));
    }

    @Test
    public void shouldDeleteCountryResourceLinksForAGivenCountry() {
        addCountrySummary("NZL", "New Zealand", "NZ");
        addCountrySummary("AUS", "Australia", "AU");
        CountryResourceLink countryResourceLink1 = new CountryResourceLink(new CountryResourceLinkId("NZL", "Res 1","PUBLISHED"));
        CountryResourceLink countryResourceLink2 = new CountryResourceLink(new CountryResourceLinkId("AUS", "Res 2","PUBLISHED"));
        CountryResourceLink countryResourceLink3 = new CountryResourceLink(new CountryResourceLinkId("NZL", "Res 3","PUBLISHED"));
        entityManager.persist(countryResourceLink1);
        entityManager.persist(countryResourceLink2);
        entityManager.persist(countryResourceLink3);
        entityManager.flush();
        entityManager.clear();
        iCountryResourceLinkRepository.deleteResources("NZL");
        List<CountryResourceLink> actual1 = iCountryResourceLinkRepository.findAllBy("NZL");
        List<CountryResourceLink> actual2 = iCountryResourceLinkRepository.findAllBy("AUS");
        assertThat(actual1.size(), is(0));
        assertThat(actual2.size(), is(1));
        assertThat(actual2.stream().map(CountryResourceLink::getLink).collect(toList()), containsInAnyOrder("Res 2"));
    }

}