package it.gdhi.repository;

import it.gdhi.model.CountrySummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
        CountrySummary expected = new CountrySummary("NZL", "NZL summary", "Contact Name",
                "Contact Designation", null, null, null,
                null, null, null, null, null,
                null);
        entityManager.persist(expected);
        entityManager.flush();
        entityManager.clear();
        CountrySummary actual = iCountrySummaryRepository.findOne("NZL");
        assertThat(actual.getCountryId(), is("NZL"));
        assertThat(actual.getSummary(), is("NZL summary"));
        assertThat(actual.getContactName(), is("Contact Name"));
        assertThat(actual.getContactDesignation(), is("Contact Designation"));
    }

}