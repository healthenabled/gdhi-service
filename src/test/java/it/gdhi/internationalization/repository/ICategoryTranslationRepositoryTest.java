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
public class ICategoryTranslationRepositoryTest {

    @Autowired
    private ICategoryTranslationRepository repository;

    @Test
    public void shouldReturnCategoryNameInArabic() {
        String category = repository.findTranslationForLanguage("ar",
                                                            "Services and Applications");

        assertEquals(category, "الخدمات والتطبيقات");
    }
}