package it.gdhi.repository;

import it.gdhi.model.Country;
import it.gdhi.model.CountryResourceLink;
import it.gdhi.model.id.CountryResourceLinkId;
import it.gdhi.model.CountrySummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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

    @Test
    public void shouldFetchPopulationGivenCountryCode() {
        CountrySummary expected = new CountrySummary("NZL", new Country("NZL", "NZL", "NZ"),
                "NZL summary",
                "Contact Name",
                "Contact Designation", null, null, null,
                null, null, null, null, null,
                null, null);
        entityManager.persist(expected);
        entityManager.flush();
        entityManager.clear();
        CountrySummary actual = iCountrySummaryRepository.findOne("NZL");
        assertThat(actual.getCountryId(), is("NZL"));
        assertThat(actual.getSummary(), is("NZL summary"));
        assertThat(actual.getContactName(), is("Contact Name"));
        assertThat(actual.getContactDesignation(), is("Contact Designation"));
        assertThat(actual.getCountry().getName(), is("New Zealand"));
    }

    @Test
    public void shouldFetchCountryCodeCaseInsensitive() {
        CountrySummary expected = new CountrySummary("NZL", new Country("NZL", "NZL", "NZ"),
                "NZL summary",
                "Contact Name",
                "Contact Designation", null, null, null,
                null, null, null, null, null,
                null, null);
        entityManager.persist(expected);
        entityManager.flush();
        entityManager.clear();
        CountrySummary actual = iCountrySummaryRepository.findOne("nzl");
        assertThat(actual.getCountryId(), is("NZL"));
        assertThat(actual.getSummary(), is("NZL summary"));
        assertThat(actual.getContactName(), is("Contact Name"));
        assertThat(actual.getContactDesignation(), is("Contact Designation"));
        assertThat(actual.getCountry().getName(), is("New Zealand"));
    }

    @Test
    public void shouldSaveCountrySummaryAlongWithResourceLinks() {
        CountrySummary countrySummary1 = new CountrySummary("NZL", new Country("NZL", "NZL", "NZ"),
                "NZL summary 1",
                "Contact Name",
                "Contact Designation", null, null, null,
                null, null, null, null, null,
                null, asList(new CountryResourceLink(new CountryResourceLinkId("NZL", "www.google.com"))));

        iCountrySummaryRepository.save(countrySummary1);

        CountrySummary nzl1 = iCountrySummaryRepository.findOne("NZL");
        assertThat(nzl1.getCountryId(), is("NZL"));
        assertThat(nzl1.getSummary(), is("NZL summary 1"));
        assertThat(nzl1.getContactName(), is("Contact Name"));
        assertThat(nzl1.getCountryResourceLinks().get(0).getCountryResourceLinkId().getCountryId(), is("NZL"));
        assertThat(nzl1.getCountryResourceLinks().get(0).getCountryResourceLinkId().getLink(), is("www.google.com"));

        CountrySummary countrySummary2 = new CountrySummary("NZL", new Country("NZL", "NZL", "NZ"),
                "NZL summary 2",
                "Contact Name",
                "Contact Designation", null, null, null,
                null, null, null, null, null,
                null, asList(new CountryResourceLink(new CountryResourceLinkId("NZL", "www.google.com"))));

        CountrySummary nzl2 = iCountrySummaryRepository.save(countrySummary2);
        assertThat(nzl2.getCountryId(), is("NZL"));
        assertThat(nzl2.getSummary(), is("NZL summary 2"));

    }

}