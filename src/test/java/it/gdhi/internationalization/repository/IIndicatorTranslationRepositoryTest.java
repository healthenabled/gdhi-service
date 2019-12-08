package it.gdhi.internationalization.repository;

import it.gdhi.internationalization.model.IndicatorTranslation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static it.gdhi.utils.LanguageCode.fr;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ActiveProfiles("test")
public class IIndicatorTranslationRepositoryTest {

    @Autowired
    private IIndicatorTranslationRepository repository;

    @Test
    public void shouldReturnIndicatorInFrench() {
        String name = "Priorité accordée à la santé numérique au niveau national par l'intermédiaire d'organes et de " +
                "mécanismes de gouvernance dédiés";
        String definition = "Le pays dispose-t-il d'un ministère, d'un organisme ou d'un groupe de travail national " +
                "distinct pour la santé numérique ?";

        IndicatorTranslation translatedIndicator = repository.findTranslationForLanguage(fr, 1);

        assertEquals(translatedIndicator.getName(), name);
        assertEquals(translatedIndicator.getDefinition(), definition);
    }
}