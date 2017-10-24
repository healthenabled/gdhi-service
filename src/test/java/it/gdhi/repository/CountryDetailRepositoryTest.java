package it.gdhi.repository;

import it.gdhi.model.CountryDetail;
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
public class CountryDetailRepositoryTest {

    @Autowired
    private CountryDetailRepository countryDetailRepository;

    @Test
    public void testInsert() {
        CountryDetail testCountry = new CountryDetail("testCountry");
        countryDetailRepository.save(testCountry);
        int size = countryDetailRepository.findAll().size();
        assertThat(size, is(1));
        countryDetailRepository.delete(testCountry);
        size = countryDetailRepository.findAll().size();
        assertThat(size, is(0));
    }
}