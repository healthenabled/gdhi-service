package it.gdhi.repository;

import it.gdhi.model.HealthIndicator;
import it.gdhi.model.id.HealthIndicatorId;
import org.junit.Before;
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

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ActiveProfiles("test")
public class IHealthIndicatorFilterRepositoryTest {

    String countryId1 = "IND";
    Integer categoryId1 = 1;
    Integer indicatorId1 = 1;
    Integer indicatorScore1 = 1;

    String countryId2 = "ARG";
    Integer indicatorId2 = 2;
    Integer indicatorScore2 = 3;

    Integer indicatorId3 = 3;
    Integer indicatorScore3 = 4;

    @Autowired
    private IHealthIndicatorRepository iHealthIndicatorRepository;

    @Autowired
    private EntityManager entityManager;

    @Before
    public void setUp() throws Exception {

        HealthIndicatorId healthIndicatorId1 = new HealthIndicatorId(countryId1,categoryId1,indicatorId1);
        HealthIndicator healthIndicatorSetupData1 = new HealthIndicator(healthIndicatorId1, indicatorScore1);

        HealthIndicatorId healthIndicatorId4 = new HealthIndicatorId(countryId1,2,4);
        HealthIndicator healthIndicatorSetupData4 = new HealthIndicator(healthIndicatorId4, indicatorScore2);

        HealthIndicatorId healthIndicatorId2 = new HealthIndicatorId(countryId2,categoryId1,indicatorId2);
        HealthIndicator healthIndicatorSetupData2 = new HealthIndicator(healthIndicatorId2, indicatorScore1);

        HealthIndicatorId healthIndicatorId3 = new HealthIndicatorId(countryId2,categoryId1,indicatorId3);
        HealthIndicator healthIndicatorSetupData3 = new HealthIndicator(healthIndicatorId3, indicatorScore3);

        entityManager.persist(healthIndicatorSetupData1);
        entityManager.persist(healthIndicatorSetupData2);
        entityManager.persist(healthIndicatorSetupData3);
        entityManager.persist(healthIndicatorSetupData4);
        entityManager.flush();
        entityManager.clear();

    }

    @Test
    public void shouldFilterHealthRepositoryBasedOnCategoryAndPhase() throws Exception {
        List<HealthIndicator> healthIndicators = iHealthIndicatorRepository.find(categoryId1, indicatorScore1);
        assertEquals(2, healthIndicators.size());
    }

    @Test
    public void shouldGetAllCountriesWithGivenCategory() throws Exception {
        List<HealthIndicator> healthIndicators = iHealthIndicatorRepository.find(categoryId1, null);
        assertEquals(3, healthIndicators.size());
    }

    @Test
    public void shouldGetAllCountriesWithGivenPhase() throws Exception {
        List<HealthIndicator> healthIndicators = iHealthIndicatorRepository.find(null, indicatorScore1);
        assertEquals(2, healthIndicators.size());
    }

    @Test
    public void shouldGetAllCountriesWhenNoCategoryAndPhaseIsGiven() throws Exception {
        List<HealthIndicator> healthIndicators = iHealthIndicatorRepository.find(null, null);
        assertEquals(4, healthIndicators.size());
    }
}