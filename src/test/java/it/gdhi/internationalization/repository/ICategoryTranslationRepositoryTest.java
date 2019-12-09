package it.gdhi.internationalization.repository;

import it.gdhi.internationalization.model.CategoryTranslation;
import it.gdhi.utils.LanguageCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableList.of;
import static it.gdhi.utils.LanguageCode.ar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ActiveProfiles("test")
public class ICategoryTranslationRepositoryTest {

    @Autowired
    private ICategoryTranslationRepository repository;

    @Test
    public void shouldReturnCategoryNameInArabic() {
        String category = repository.findTranslationForLanguage(ar, "Services and Applications");

        assertEquals(category, "الخدمات والتطبيقات");
    }

    @Test
    public void shouldReturnAllCategoryNamesInFrench() {
        List<String> expectedCategories = of("Leadership et gouvernance", "Stratégie et investissement", "Lois, politiques et conformité",
                "Ressources Humaines", "Normes et interopérabilité", "Infrastructure", "Services et applications");

        List<CategoryTranslation> categories = repository.findByLanguageId(LanguageCode.fr);
        List<String> categoryNames = categories.stream().map(c -> c.getName()).sorted(Comparator.naturalOrder()).collect(Collectors.toList());

        assertTrue(expectedCategories.containsAll(categoryNames));
    }
}