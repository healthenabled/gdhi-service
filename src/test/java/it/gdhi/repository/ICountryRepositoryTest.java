package it.gdhi.repository;

import it.gdhi.model.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ActiveProfiles("test")
public class ICountryRepositoryTest {

    @Autowired
    private ICountryRepository countryRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    public void shouldGetAllCountries() {
        int size = countryRepository.findAll().size();
        assertThat(size, is(194));
    }

    @Test
    public void shouldGetCountryGivenId() throws Exception {
        Country country = new Country("ABC", "Republic of ABC", "AB");
        entityManager.persist(country);
        entityManager.flush();
        entityManager.clear();

        Country actualCountry = countryRepository.find(country.getId());

        assertEquals(country.getId(), actualCountry.getId());
        assertEquals(country.getName(), actualCountry.getName());
    }
}