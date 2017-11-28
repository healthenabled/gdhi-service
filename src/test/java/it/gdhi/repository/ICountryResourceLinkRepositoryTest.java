package it.gdhi.repository;

import it.gdhi.model.CountryResourceLink;
import it.gdhi.model.id.CountryResourceLinkId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

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

    @Test
    public void shouldFetchCountryResourceLinks() {
        CountryResourceLink countryResourceLink1 = new CountryResourceLink(new CountryResourceLinkId("NZL", "Res 1"));
        CountryResourceLink countryResourceLink2 = new CountryResourceLink(new CountryResourceLinkId("AUS", "Res 2"));
        CountryResourceLink countryResourceLink3 = new CountryResourceLink(new CountryResourceLinkId("NZL", "Res 3"));
        entityManager.persist(countryResourceLink1);
        entityManager.persist(countryResourceLink2);
        entityManager.persist(countryResourceLink3);
        entityManager.flush();
        entityManager.clear();
        List<CountryResourceLink> actual = iCountryResourceLinkRepository.findAllBy("NZL");
        assertThat(actual.size(), is(2));
        assertThat(actual.stream().map(a -> a.getLink()).collect(toList()), containsInAnyOrder("Res 1", "Res 3"));
    }

}