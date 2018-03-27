package it.gdhi.repository;

import it.gdhi.model.CountryHealthIndicator;
import it.gdhi.model.id.CountryHealthIndicatorId;
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
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ActiveProfiles("test")
public class ICountryHealthIndicatorRepositoryTest {

    @Autowired
    private ICountryHealthIndicatorRepository iCountryHealthIndicatorRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void shouldFetchCountryCategoryIndicatorDetailsOnHealthIndicatorScoreFetchForCountry() {

        String countryId = "IND";
        Integer categoryId = 1;
        Integer indicatorId = 2;
        Integer indicatorScore = 3;

        CountryHealthIndicatorId countryHealthIndicatorId = new CountryHealthIndicatorId(countryId,categoryId,indicatorId);
        CountryHealthIndicator countryHealthIndicatorSetupData = new CountryHealthIndicator(countryHealthIndicatorId, indicatorScore);

        entityManager.persist(countryHealthIndicatorSetupData);
        entityManager.flush();
        entityManager.clear();

        List<CountryHealthIndicator> countryHealthIndicator = iCountryHealthIndicatorRepository.findHealthIndicatorsFor("IND");

        assertThat(countryHealthIndicator.size(), is(1));
        assertThat(countryHealthIndicator.get(0).getCategory().getName(), is("Leadership and Governance"));
        assertThat(countryHealthIndicator.get(0).getCountry().getName(), is("India"));
        assertThat(countryHealthIndicator.get(0).getIndicator().getName(), is("Digital Health prioritized at the national level through planning"));
    }

    @Test
    public void shouldSaveCountryHealthIndicatorScoreForCountry() {

        String countryId = "IND";
        Integer categoryId = 1;
        Integer indicatorId = 2;
        Integer indicatorScore = 3;

        CountryHealthIndicatorId countryHealthIndicatorId = new CountryHealthIndicatorId(countryId,categoryId,indicatorId);
        CountryHealthIndicator countryHealthIndicatorSetupData = new CountryHealthIndicator(countryHealthIndicatorId, indicatorScore);

        iCountryHealthIndicatorRepository.save(countryHealthIndicatorSetupData);
        entityManager.flush();
        entityManager.clear();

        List<CountryHealthIndicator> countryHealthIndicator = iCountryHealthIndicatorRepository.findHealthIndicatorsFor("IND");

        assertThat(countryHealthIndicator.size(), is(1));
        assertThat(countryHealthIndicator.get(0).getCategory().getName(), is("Leadership and Governance"));
        assertThat(countryHealthIndicator.get(0).getCountry().getName(), is("India"));
        assertThat(countryHealthIndicator.get(0).getIndicator().getName(), is("Digital Health prioritized at the national level through planning"));
    }

    @Test
    public void shouldFetchHealthIndicatorWithAssociatedProperties() throws Exception {
        String countryId = "IND";
        Integer categoryId = 7;
        Integer indicatorId = 13;
        Integer score = 3;

        CountryHealthIndicatorId countryHealthIndicatorId = new CountryHealthIndicatorId(countryId,categoryId,indicatorId);
        CountryHealthIndicator countryHealthIndicatorSetupData = new CountryHealthIndicator(countryHealthIndicatorId, score);
        entityManager.persist(countryHealthIndicatorSetupData);
        entityManager.flush();
        entityManager.clear();

        List<CountryHealthIndicator> countryHealthIndicators = iCountryHealthIndicatorRepository.findHealthIndicatorsFor("IND");

        assertEquals(1, countryHealthIndicators.size());
        CountryHealthIndicator countryHealthIndicator = countryHealthIndicators.get(0);
        assertThat(countryHealthIndicator.getCategory().getId(), is(categoryId));
        assertThat(countryHealthIndicator.getCategory().getName(), is("Services and Applications"));
        assertThat(countryHealthIndicator.getIndicator().getIndicatorId(), is(indicatorId));
        assertThat(countryHealthIndicator.getIndicator().getName(), is("National digital health architecture and/or health information exchange"));
        assertThat(countryHealthIndicator.getIndicator().getDefinition(), is("Is there a national digital health (eHealth) architectural framework established?"));
        assertThat(countryHealthIndicator.getIndicatorScore().getDefinition(), is("The HIE is operable and provides core functions, such as authentication, translation, storage and warehousing function, " +
                "guide to what data is available and how to access it, and data interpretation."));
        assertThat(countryHealthIndicator.getIndicatorScore().getScore(), is(score));
    }

    @Test
    public void shouldFetchHealthIndicatorWithNullIndicatorScore() throws Exception {
        String countryId = "IND";
        Integer categoryId = 7;
        Integer indicatorId = 13;
        Integer score = null;

        CountryHealthIndicatorId countryHealthIndicatorId = new CountryHealthIndicatorId(countryId,categoryId,indicatorId);
        CountryHealthIndicator countryHealthIndicatorSetupData = new CountryHealthIndicator(countryHealthIndicatorId, score);
        entityManager.persist(countryHealthIndicatorSetupData);
        entityManager.flush();
        entityManager.clear();

        List<CountryHealthIndicator> countryHealthIndicators = iCountryHealthIndicatorRepository.findHealthIndicatorsFor("IND");

        assertEquals(1, countryHealthIndicators.size());
        CountryHealthIndicator countryHealthIndicator = countryHealthIndicators.get(0);
        assertThat(countryHealthIndicator.getCategory().getId(), is(categoryId));
        assertThat(countryHealthIndicator.getCategory().getName(), is("Services and Applications"));
        assertThat(countryHealthIndicator.getIndicator().getIndicatorId(), is(indicatorId));
        assertThat(countryHealthIndicator.getIndicator().getName(), is("National digital health architecture and/or health information exchange"));
        assertThat(countryHealthIndicator.getIndicator().getDefinition(), is("Is there a national digital health (eHealth) architectural framework established?"));
        assertThat(countryHealthIndicator.getScoreDescription(), is("Missing or Not Available"));
        assertNull(countryHealthIndicator.getScore());
    }

    @Test
    public void shouldNotFetchDataWhenCountryDataNotPresent() throws Exception {
        List<CountryHealthIndicator> countryHealthIndicators = iCountryHealthIndicatorRepository.findHealthIndicatorsFor("IND");
        assertEquals(0, countryHealthIndicators.size());
    }

    @Test
    public void shouldNotFetchDataWhenCountryIdIsNotSpecified() throws Exception {
        List<CountryHealthIndicator> countryHealthIndicators = iCountryHealthIndicatorRepository
                .findHealthIndicatorsFor(null);
        assertEquals(0, countryHealthIndicators.size());
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

        CountryHealthIndicatorId countryHealthIndicatorId1 = new CountryHealthIndicatorId(countryId1,categoryId1,indicatorId1);
        CountryHealthIndicator countryHealthIndicatorSetupData1 = new CountryHealthIndicator(countryHealthIndicatorId1, indicatorScore1);

        CountryHealthIndicatorId countryHealthIndicatorId2 = new CountryHealthIndicatorId(countryId2,categoryId2,indicatorId2);
        CountryHealthIndicator countryHealthIndicatorSetupData2 = new CountryHealthIndicator(countryHealthIndicatorId2, indicatorScore2);

        CountryHealthIndicatorId countryHealthIndicatorId3 = new CountryHealthIndicatorId(countryId3,categoryId3,indicatorId3);
        CountryHealthIndicator countryHealthIndicatorSetupData3 = new CountryHealthIndicator(countryHealthIndicatorId3, indicatorScore3);

        entityManager.persist(countryHealthIndicatorSetupData1);
        entityManager.persist(countryHealthIndicatorSetupData2);
        entityManager.persist(countryHealthIndicatorSetupData3);
        entityManager.flush();
        entityManager.clear();

        List<String> countries = iCountryHealthIndicatorRepository.findCountriesWithHealthScores();

        assertThat(countries.size(), is(2));
        assertThat(countries, is(Matchers.containsInAnyOrder("ARG", "IND")));
    }
}