package it.gdhi.repository;

import it.gdhi.model.HealthIndicator;
import it.gdhi.model.HealthIndicatorId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ActiveProfiles("test")
public class HelathIndicatorRepositoryTest {

    @Autowired
    private IHealthIndicatorRepository iHealthIndicatorRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void shouldFetchCountryCategoryIndicatorDetailsOnHealthIndicatorScoreFetchForCountry() {

        String countryId = "IND";
        Integer categoryId = 1;
        Integer indicatorId = 2;
        Integer indicatorScore = 3;

        HealthIndicatorId healthIndicatorId = new HealthIndicatorId(countryId,categoryId,indicatorId);
        HealthIndicator healthIndicatorSetupData = new HealthIndicator(healthIndicatorId, indicatorScore);

        entityManager.persist(healthIndicatorSetupData);
        entityManager.flush();
        entityManager.clear();

        List<HealthIndicator> healthIndicator = iHealthIndicatorRepository.findHealthIndicatorsFor("IND");

        assertThat(healthIndicator.size(), is(1));
        assertThat(healthIndicator.get(0).getCategory().getName(), is("Leadership and Governance"));
        assertThat(healthIndicator.get(0).getCountry().getName(), is("India"));
        assertThat(healthIndicator.get(0).getIndicator().getName(), is("Digital Health prioritized at the national level through planning"));
    }

}