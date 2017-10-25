package it.gdhi.repository;

import it.gdhi.model.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ActiveProfiles("test")
public class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryRepository;

    @Test
    public void testInsert() {
        Country testCountry = new Country("testCountry", "in");
        countryRepository.save(testCountry);
        int size = countryRepository.findAll().size();
        assertThat(size, is(1));
        countryRepository.delete(testCountry);
        size = countryRepository.findAll().size();
        assertThat(size, is(0));
    }
}