package it.gdhi.repository;

import it.gdhi.model.CountryHealthIndicator;
import it.gdhi.model.id.CountryHealthIndicatorId;
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

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ActiveProfiles("test")
public class ICountryHealthIndicatorFilterRepositoryTest {

    private String countryId1 = "IND";
    private Integer categoryId1 = 1;
    private Integer indicatorId1 = 1;
    private Integer indicatorScore1 = 1;

    String countryId2 = "ARG";
    Integer indicatorId2 = 2;
    Integer indicatorScore2 = 3;

    Integer indicatorId3 = 3;
    Integer indicatorScore3 = 4;

    @Autowired
    private ICountryHealthIndicatorRepository iCountryHealthIndicatorRepository;

    @Autowired
    private EntityManager entityManager;

    @Before
    public void setUp() throws Exception {

        CountryHealthIndicatorId countryHealthIndicatorId1 = new CountryHealthIndicatorId(countryId1,categoryId1,indicatorId1);
        CountryHealthIndicator countryHealthIndicatorSetupData1 = new CountryHealthIndicator(countryHealthIndicatorId1, indicatorScore1);

        CountryHealthIndicatorId countryHealthIndicatorId4 = new CountryHealthIndicatorId(countryId1,2,4);
        CountryHealthIndicator countryHealthIndicatorSetupData4 = new CountryHealthIndicator(countryHealthIndicatorId4, indicatorScore2);

        CountryHealthIndicatorId countryHealthIndicatorId2 = new CountryHealthIndicatorId(countryId2,categoryId1,indicatorId2);
        CountryHealthIndicator countryHealthIndicatorSetupData2 = new CountryHealthIndicator(countryHealthIndicatorId2, indicatorScore1);

        CountryHealthIndicatorId countryHealthIndicatorId3 = new CountryHealthIndicatorId(countryId2,categoryId1,indicatorId3);
        CountryHealthIndicator countryHealthIndicatorSetupData3 = new CountryHealthIndicator(countryHealthIndicatorId3, indicatorScore3);

        entityManager.persist(countryHealthIndicatorSetupData1);
        entityManager.persist(countryHealthIndicatorSetupData2);
        entityManager.persist(countryHealthIndicatorSetupData3);
        entityManager.persist(countryHealthIndicatorSetupData4);
        entityManager.flush();
        entityManager.clear();

    }

    @Test
    public void shouldFilterHealthRepositoryBasedOnCategoryAndPhase() throws Exception {
        List<CountryHealthIndicator> countryHealthIndicators = iCountryHealthIndicatorRepository.find(categoryId1);
        assertEquals(3, countryHealthIndicators.size());
    }

    @Test
    public void shouldGetAllCountriesWhenNoCategoryAndPhaseIsGiven() throws Exception {
        List<CountryHealthIndicator> countryHealthIndicators = iCountryHealthIndicatorRepository.find(null);
        assertEquals(4, countryHealthIndicators.size());
    }

    @Test
    public void shouldReturnEmptyListWhenGivenCategoryHasNoHealthIndicatorPresent() throws Exception {
        List<CountryHealthIndicator> countryHealthIndicators = iCountryHealthIndicatorRepository.find(7);
        assertEquals(0, countryHealthIndicators.size());
    }
}