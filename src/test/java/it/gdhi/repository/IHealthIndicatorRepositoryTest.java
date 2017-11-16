package it.gdhi.repository;

import it.gdhi.model.HealthIndicator;
import it.gdhi.model.id.HealthIndicatorId;
import org.hamcrest.Matchers;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ActiveProfiles("test")
public class IHealthIndicatorRepositoryTest {

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

    @Test
    public void shouldFetchHealthIndicatorWithAssociatedProperties() throws Exception {
        String countryId = "IND";
        Integer categoryId = 7;
        Integer indicatorId = 13;
        Integer score = 3;

        HealthIndicatorId healthIndicatorId = new HealthIndicatorId(countryId,categoryId,indicatorId);
        HealthIndicator healthIndicatorSetupData = new HealthIndicator(healthIndicatorId, score);
        entityManager.persist(healthIndicatorSetupData);
        entityManager.flush();
        entityManager.clear();

        List<HealthIndicator> healthIndicators = iHealthIndicatorRepository.findHealthIndicatorsFor("IND");

        assertEquals(1, healthIndicators.size());
        HealthIndicator healthIndicator = healthIndicators.get(0);
        assertThat(healthIndicator.getCategory().getId(), is(categoryId));
        assertThat(healthIndicator.getCategory().getName(), is("Services and Applications"));
        assertThat(healthIndicator.getIndicator().getIndicatorId(), is(indicatorId));
        assertThat(healthIndicator.getIndicator().getName(), is("National digital health architecture and/or health information exchange"));
        assertThat(healthIndicator.getIndicator().getDefinition(), is("Is there a national digital health (eHealth) architectural framework established?"));
        assertThat(healthIndicator.getIndicatorScore().getDefinition(), is("The HIE is operable and provides core functions, such as authentication, translation, storage and warehousing function, " +
                "guide to what data is available and how to access it, and data interpretation."));
        assertThat(healthIndicator.getIndicatorScore().getScore(), is(score));
    }

    @Test
    public void shouldFetchUniqueCountryIdsFromHealthIndicatorTable() {
        String countryId1 = "IND";
        Integer categoryId1 = 1;
        Integer indicatorId1 = 1;
        Integer indicatorScore1 = 1;

        String countryId2 = "ARG";
        Integer categoryId2 = 1;
        Integer indicatorId2 = 2;
        Integer indicatorScore2 = 3;

        String countryId3 = "ARG";
        Integer categoryId3 = 1;
        Integer indicatorId3 = 1;
        Integer indicatorScore3 = 4;

        HealthIndicatorId healthIndicatorId1 = new HealthIndicatorId(countryId1,categoryId1,indicatorId1);
        HealthIndicator healthIndicatorSetupData1 = new HealthIndicator(healthIndicatorId1, indicatorScore1);

        HealthIndicatorId healthIndicatorId2 = new HealthIndicatorId(countryId2,categoryId2,indicatorId2);
        HealthIndicator healthIndicatorSetupData2 = new HealthIndicator(healthIndicatorId2, indicatorScore2);

        HealthIndicatorId healthIndicatorId3 = new HealthIndicatorId(countryId3,categoryId3,indicatorId3);
        HealthIndicator healthIndicatorSetupData3 = new HealthIndicator(healthIndicatorId3, indicatorScore3);

        entityManager.persist(healthIndicatorSetupData1);
        entityManager.persist(healthIndicatorSetupData2);
        entityManager.persist(healthIndicatorSetupData3);
        entityManager.flush();
        entityManager.clear();

        List<String> countries = iHealthIndicatorRepository.findCountriesWithHealthScores();

        assertThat(countries.size(), is(2));
        assertThat(countries, is(Matchers.containsInAnyOrder("ARG", "IND")));
    }

}