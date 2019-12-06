package it.gdhi.internationalization.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ActiveProfiles("test")
public class IScoreDefinitionTranslationRepositoryTest {

    @Autowired
    private IScoreDefinitionTranslationRepository repository;

    @Test
    public void shouldReturnScoreDefinitionInFrenchForGivenHealthIndicator() {
        String expectedScoreDefinition = "La structure de gouvernance est completement fonctionnelle, dirigée par le " +
                "gouvernement, consulte les autres ministères et surveille la mise en œuvre de la santé numérique en " +
                "fonction d'un plan de travail.";

        String scoreDefinitionFR = repository.findTranslationForLanguage("fr", 1, 4);

        assertEquals(expectedScoreDefinition, scoreDefinitionFR);
    }
}