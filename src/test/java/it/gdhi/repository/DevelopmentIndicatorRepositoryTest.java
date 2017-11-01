package it.gdhi.repository;

import it.gdhi.model.DevelopmentIndicator;
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
public class DevelopmentIndicatorRepositoryTest {

    @Autowired
    private IDevelopmentIndicatorRepository iDevelopmentIndicatorRepository;

    @Test
    public void shouldFetchPopulationGivenCountryCode() {
        DevelopmentIndicator developmentIndicator = iDevelopmentIndicatorRepository.findByCountryId("NZL").orElse(null);
        assertThat(developmentIndicator.getTotalPopulation(), is(4692700L));
    }

}