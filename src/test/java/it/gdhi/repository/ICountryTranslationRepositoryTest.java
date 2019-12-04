package it.gdhi.repository;

import it.gdhi.internationalization.repository.ICountryTranslationRepository;
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
public class ICountryTranslationRepositoryTest {

    @Autowired
    private ICountryTranslationRepository repository;

    @Test
    public void shouldReturnCountryNameInSpanish() {
        String country = repository.findTranslationForLanguage("es", "AFG");

        assertEquals(country, "Afganistán");
    }

}