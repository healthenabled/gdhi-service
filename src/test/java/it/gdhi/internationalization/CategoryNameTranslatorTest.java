package it.gdhi.internationalization;

import it.gdhi.internationalization.repository.ICategoryTranslationRepository;
import it.gdhi.internationalization.service.CategoryNameTranslator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static it.gdhi.utils.LanguageCode.en;
import static it.gdhi.utils.LanguageCode.fr;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryNameTranslatorTest {
    @InjectMocks
    private CategoryNameTranslator translator;
    @Mock
    private ICategoryTranslationRepository translationRepository;

    @Test
    public void shouldReturnCategoryNamesInEnglishGivenUserLanguageIsNull() {
        String workforceEN = "Workforce";

        String actualCategory = translator.translate(workforceEN, null);

        assertEquals(workforceEN, actualCategory);
    }

    @Test
    public void shouldReturnCategoryNamesInEnglishGivenUserLanguageIsEnglish() {
        String workforceEN = "Workforce";

        String actualCategory = translator.translate(workforceEN, en);

        assertEquals(workforceEN, actualCategory);
    }

    @Test
    public void shouldNotInvokeTranslationRepositoryGivenUserLanguageIsNull() {
        String workforceEN = "Workforce";

        translator.translate(workforceEN, en);

        verify(translationRepository, never()).findTranslationForLanguage(anyString(), anyString());
    }

    @Test
    public void shouldReturnCategoryNamesInFrenchGivenUserLanguageIsFrench() {
        String workforceEN = "Workforce";
        String workforceFR = "Lois, politiques et conformité";

        when(translationRepository.findTranslationForLanguage("fr", workforceEN)).thenReturn("Lois, politiques et conformité");

        String actualCategory = translator.translate(workforceEN, fr);

        assertEquals(workforceFR, actualCategory);
    }

    @Test
    public void shouldReturnCategoryNameInEnglishGivenCategoryNameInUserLanguageIsNull() {
        String workforceEN = "Workforce";

        when(translationRepository.findTranslationForLanguage("fr", workforceEN)).thenReturn(null);

        String actualCategory = translator.translate(workforceEN, fr);

        assertEquals(workforceEN, actualCategory);
    }

    @Test
    public void shouldReturnCategoryNameInEnglishGivenCategoryNameInUserLanguageIsEmptyString() {
        String workforceEN = "Workforce";

        when(translationRepository.findTranslationForLanguage("fr", workforceEN)).thenReturn("");

        String actualCategory = translator.translate(workforceEN, fr);

        assertEquals(workforceEN, actualCategory);
    }
}