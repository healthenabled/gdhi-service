package it.gdhi.service.internationalization;

import it.gdhi.model.Category;
import it.gdhi.repository.ICategoryTranslationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.google.common.collect.ImmutableList.of;
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
        Category workforceEN = new Category(1, "Workforce");
        List<Category> expectedCategories = of(workforceEN);

        List<Category> actualCategories = translator.translate(expectedCategories, null);

        assertEquals(expectedCategories, actualCategories);
    }

    @Test
    public void shouldReturnCategoryNamesInEnglishGivenUserLanguageIsEnglish() {
        Category workforceEN = new Category(1, "Workforce");
        List<Category> expectedCategories = of(workforceEN);

        List<Category> actualCategories = translator.translate(expectedCategories, en);

        assertEquals(expectedCategories, actualCategories);
    }

    @Test
    public void shouldNotInvokeTranslationRepositoryGivenUserLanguageIsNull() {
        Category workforceEN = new Category(1, "Workforce");
        List<Category> expectedCategories = of(workforceEN);

        List<Category> actualCategories = translator.translate(expectedCategories, en);

        verify(translationRepository, never()).findTranslationForLanguage(anyString(), anyInt());
    }

    @Test
    public void shouldReturnCategoryNamesInFrenchGivenUserLanguageIsFrench() {
        Category workforceEN = new Category(1, "Workforce");
        Category infrastructureEN = new Category(2, "Infrastructure");
        List<Category> categoriesInEnglish = of(workforceEN, infrastructureEN);
        Category workforceFR = new Category(1, "Lois, politiques et conformité");
        Category infrastructureFR = new Category(2, "Infrastructure");
        List<Category> translatedCategories = of(workforceFR, infrastructureFR);

        when(translationRepository.findTranslationForLanguage("fr", workforceEN.getId())).thenReturn("Lois, politiques et conformité");
        when(translationRepository.findTranslationForLanguage("fr", infrastructureFR.getId())).thenReturn("Infrastructure");

        List<Category> actualCategories = translator.translate(categoriesInEnglish, fr);

        assertEquals(translatedCategories, actualCategories);
    }

    @Test
    public void shouldReturnCategoryNameInEnglishGivenCategoryNameInUserLanguageIsNull() {
        Category workforceEN = new Category(1, "Workforce");
        Category infrastructureEN = new Category(2, "Infrastructure");
        List<Category> categoriesInEnglish = of(workforceEN, infrastructureEN);
        Category workforceFR = new Category(1, "Lois, politiques et conformité");
        List<Category> translatedCategories = of(workforceFR, infrastructureEN);

        when(translationRepository.findTranslationForLanguage("fr", infrastructureEN.getId())).thenReturn(null);
        when(translationRepository.findTranslationForLanguage("fr", workforceEN.getId())).thenReturn("Lois, politiques et conformité");

        List<Category> actualCategories = translator.translate(categoriesInEnglish, fr);

        assertEquals(translatedCategories, actualCategories);
    }

    @Test
    public void shouldReturnCategoryNameInEnglishGivenCategoryNameInUserLanguageIsEmptyString() {
        Category workforceEN = new Category(1, "Workforce");
        Category infrastructureEN = new Category(2, "Infrastructure");
        List<Category> categoriesInEnglish = of(workforceEN, infrastructureEN);
        Category workforceFR = new Category(1, "Lois, politiques et conformité");
        List<Category> translatedCategories = of(workforceFR, infrastructureEN);

        when(translationRepository.findTranslationForLanguage("fr", infrastructureEN.getId())).thenReturn("");
        when(translationRepository.findTranslationForLanguage("fr", workforceEN.getId())).thenReturn("Lois, politiques et conformité");

        List<Category> actualCategories = translator.translate(categoriesInEnglish, fr);

        assertEquals(translatedCategories, actualCategories);
    }
}