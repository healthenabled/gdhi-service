package it.gdhi.internationalization;

import it.gdhi.dto.CategoryIndicatorDto;
import it.gdhi.internationalization.model.HealthIndicatorTranslationId;
import it.gdhi.internationalization.model.IndicatorTranslation;
import it.gdhi.internationalization.repository.ICategoryTranslationRepository;
import it.gdhi.internationalization.repository.IIndicatorTranslationRepository;
import it.gdhi.internationalization.service.HealthIndicatorTranslator;
import it.gdhi.model.Category;
import it.gdhi.model.Indicator;
import it.gdhi.utils.LanguageCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.google.common.collect.ImmutableList.of;
import static it.gdhi.utils.LanguageCode.en;
import static it.gdhi.utils.LanguageCode.fr;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HealthIndicatorTranslatorTest {
    @InjectMocks
    private HealthIndicatorTranslator translator;
    @Mock
    private ICategoryTranslationRepository categoryTranslationRepository;
    @Mock
    private IIndicatorTranslationRepository indicatorTranslationRepository;

    @Test
    public void shouldReturnCategoryNamesInEnglishGivenUserLanguageIsNull() {
        String workforceEN = "Workforce";

        String actualCategory = translator.getTranslatedCategoryName(workforceEN, null);

        assertEquals(workforceEN, actualCategory);
    }

    @Test
    public void shouldReturnCategoryNamesInEnglishGivenUserLanguageIsEnglish() {
        String workforceEN = "Workforce";

        String actualCategory = translator.getTranslatedCategoryName(workforceEN, en);

        assertEquals(workforceEN, actualCategory);
    }

    @Test
    public void shouldNotInvokeTranslationRepositoryGivenUserLanguageIsNull() {
        String workforceEN = "Workforce";

        translator.getTranslatedCategoryName(workforceEN, en);

        verify(categoryTranslationRepository, never()).findTranslationForLanguage(anyString(), anyString());
    }

    @Test
    public void shouldReturnCategoryNamesInFrenchGivenUserLanguageIsFrench() {
        String workforceEN = "Workforce";
        String workforceFR = "Lois, politiques et conformité";

        when(categoryTranslationRepository.findTranslationForLanguage("fr", workforceEN)).thenReturn("Lois, politiques et conformité");

        String actualCategory = translator.getTranslatedCategoryName(workforceEN, fr);

        assertEquals(workforceFR, actualCategory);
    }

    @Test
    public void shouldReturnCategoryNameInEnglishGivenCategoryNameInUserLanguageIsNull() {
        String workforceEN = "Workforce";

        when(categoryTranslationRepository.findTranslationForLanguage("fr", workforceEN)).thenReturn(null);

        String actualCategory = translator.getTranslatedCategoryName(workforceEN, fr);

        assertEquals(workforceEN, actualCategory);
    }

    @Test
    public void shouldReturnCategoryNameInEnglishGivenCategoryNameInUserLanguageIsEmptyString() {
        String workforceEN = "Workforce";

        when(categoryTranslationRepository.findTranslationForLanguage("fr", workforceEN)).thenReturn("");

        String actualCategory = translator.getTranslatedCategoryName(workforceEN, fr);

        assertEquals(workforceEN, actualCategory);
    }

    @Test
    public void shouldTranslateCategoryIndicatorToFrench() {
        Category categoryEN = new Category(2, "Strategy and Investment", of(new Indicator(1, "Digital health prioritized at the national level through dedicated bodies / mechanisms for governance", "Does the country have a separate department / agency / national working group for digital health?", 4)));
        CategoryIndicatorDto categoryIndicatorEN = new CategoryIndicatorDto(categoryEN);

        HealthIndicatorTranslationId indicatorId = new HealthIndicatorTranslationId(1, "fr");
        IndicatorTranslation indicatorFR = new IndicatorTranslation(indicatorId, "Priorité accordée à la santé numérique au niveau national par l''intermédiaire d''organes et de mécanismes de gouvernance dédiés/ mechanisms for governance", "Le pays dispose-t-il d''un ministère, d''un organisme ou d''un groupe de travail national distinct pour la santé numérique ?", null);
        Category categoryFR = new Category(2, "Stratégie et investissement", of(new Indicator(1, "Priorité accordée à la santé numérique au niveau national par l''intermédiaire d''organes et de mécanismes de gouvernance dédiés/ mechanisms for governance", "Le pays dispose-t-il d''un ministère, d''un organisme ou d''un groupe de travail national distinct pour la santé numérique ?", 4)));
        CategoryIndicatorDto categoryIndicatorFR = new CategoryIndicatorDto(categoryFR);

        when(categoryTranslationRepository.findTranslationForLanguage("fr", "Strategy and Investment")).thenReturn("Stratégie et investissement");
        when(indicatorTranslationRepository.findTranslationForLanguage("fr", 1)).thenReturn(indicatorFR);

        CategoryIndicatorDto translatedIndicator = translator.translate(categoryIndicatorEN, LanguageCode.fr);

        assertEquals(categoryIndicatorFR, translatedIndicator);
    }

    @Test
    public void shouldNotTranslateCategoryIndicatorGivenLanguageCodeIsEN() {
        Category categoryEN = new Category(2, "Strategy and Investment", of(new Indicator(1, "Digital health prioritized at the national level through dedicated bodies / mechanisms for governance", "Does the country have a separate department / agency / national working group for digital health?", 4)));
        CategoryIndicatorDto categoryIndicatorEN = new CategoryIndicatorDto(categoryEN);

        translator.translate(categoryIndicatorEN, en);

        verify(categoryTranslationRepository, never()).findTranslationForLanguage(anyString(), anyString());
        verify(indicatorTranslationRepository, never()).findTranslationForLanguage(anyString(), anyInt());
    }

    @Test
    public void shouldNotTranslateCategoryIndicatorGivenLanguageCodeIsNull() {
        Category categoryEN = new Category(2, "Strategy and Investment", of(new Indicator(1, "Digital health prioritized at the national level through dedicated bodies / mechanisms for governance", "Does the country have a separate department / agency / national working group for digital health?", 4)));
        CategoryIndicatorDto categoryIndicatorEN = new CategoryIndicatorDto(categoryEN);

        translator.translate(categoryIndicatorEN, null);

        verify(categoryTranslationRepository, never()).findTranslationForLanguage(anyString(), anyString());
        verify(indicatorTranslationRepository, never()).findTranslationForLanguage(anyString(), anyInt());
    }
}