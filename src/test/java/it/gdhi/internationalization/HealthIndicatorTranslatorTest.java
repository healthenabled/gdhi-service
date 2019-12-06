package it.gdhi.internationalization;

import it.gdhi.dto.CategoryIndicatorDto;
import it.gdhi.internationalization.model.HealthIndicatorTranslationId;
import it.gdhi.internationalization.model.IndicatorTranslation;
import it.gdhi.internationalization.repository.ICategoryTranslationRepository;
import it.gdhi.internationalization.repository.IIndicatorTranslationRepository;
import it.gdhi.internationalization.repository.IScoreDefinitionTranslationRepository;
import it.gdhi.internationalization.service.HealthIndicatorTranslator;
import it.gdhi.model.Category;
import it.gdhi.model.Indicator;
import it.gdhi.model.IndicatorScore;
import it.gdhi.utils.LanguageCode;
import org.junit.Before;
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
    @Mock
    private IScoreDefinitionTranslationRepository scoreTranslationRepository;

    private IndicatorScore scoreEN;
    private Indicator indicatorEN;
    private IndicatorTranslation indicatorTranslationFR;
    private Indicator indicatorFR;
    private String scoreDefinitionFR;

    @Before
    public void setUp() throws Exception {
        String scoreDefinitionEN = "Governance structure is fully-functional, government-led, consults with other " +
                                    "ministries, and monitors implementation of digital health based on a work plan.";
        scoreEN = new IndicatorScore(22L, 1, 4,
                scoreDefinitionEN);
        String indicatorNameEN = "Digital health prioritized at the national level through dedicated bodies / " +
                                "mechanisms for governance";
        String indicatorDefinitionEN = "Does the country have a separate department / agency / national working group for digital health?";
        indicatorEN = new Indicator(1, indicatorNameEN, "1", 4, 1,
                                    of(scoreEN), indicatorDefinitionEN);

        HealthIndicatorTranslationId indicatorId = new HealthIndicatorTranslationId(1, "fr");
        String indicatorNameFR = "Priorité accordée à la santé numérique au niveau national par l''intermédiaire " +
                                "d''organes et de mécanismes de gouvernance dédiés/ mechanisms for governance";
        String indicatorDefinitionFR = "Le pays dispose-t-il d''un ministère, d''un organisme ou d''un groupe de " +
                                        "travail national distinct pour la santé numérique ?";
        indicatorTranslationFR = new IndicatorTranslation(indicatorId, indicatorNameFR,
                                                            indicatorDefinitionFR, indicatorEN);
        scoreDefinitionFR = "La structure de gouvernance est completement fonctionnelle, dirigée par le " +
                                    "gouvernement, consulte les autres ministères et surveille la mise en œuvre de " +
                                    "la santé numérique en fonction d'un plan de travail.";
        IndicatorScore indicatorScoreFR = new IndicatorScore(22L, 1, 4, scoreDefinitionFR);
        indicatorFR = new Indicator(1, indicatorNameFR, "1", 4, 1,
                                            of(indicatorScoreFR), indicatorDefinitionFR);
    }

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

        when(categoryTranslationRepository.findTranslationForLanguage("fr", workforceEN))
                                            .thenReturn("Lois, politiques et conformité");

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
    public void shouldNotTranslateCategoryIndicatorGivenLanguageCodeIsEN() {
        Category categoryEN = new Category(2, "Strategy and Investment", of(new Indicator(1,
        "Digital health prioritized at the national level through dedicated bodies / mechanisms for governance",
        "Does the country have a separate department / agency / national working group for digital health?",
                4)));
        CategoryIndicatorDto categoryIndicatorEN = new CategoryIndicatorDto(categoryEN);

        translator.translate(categoryIndicatorEN, en);

        verify(categoryTranslationRepository, never()).findTranslationForLanguage(anyString(), anyString());
        verify(indicatorTranslationRepository, never()).findTranslationForLanguage(anyString(), anyInt());
        verify(scoreTranslationRepository, never()).findTranslationForLanguage(anyString(), anyInt(), anyInt());
    }

    @Test
    public void shouldNotTranslateCategoryIndicatorGivenLanguageCodeIsNull() {
        Category categoryEN = new Category(2, "Strategy and Investment", of(new Indicator(1,
        "Digital health prioritized at the national level through dedicated bodies / mechanisms for governance",
        "Does the country have a separate department / agency / national working group for digital health?",
                4)));
        CategoryIndicatorDto categoryIndicatorEN = new CategoryIndicatorDto(categoryEN);

        translator.translate(categoryIndicatorEN, null);

        verify(categoryTranslationRepository, never()).findTranslationForLanguage(anyString(), anyString());
        verify(indicatorTranslationRepository, never()).findTranslationForLanguage(anyString(), anyInt());
        verify(scoreTranslationRepository, never()).findTranslationForLanguage(anyString(), anyInt(), anyInt());
    }

    @Test
    public void shouldTranslateCategoryIndicatorToFrench() {
        Category categoryEN = new Category(2, "Strategy and Investment", of(indicatorEN));
        CategoryIndicatorDto categoryIndicatorEN = new CategoryIndicatorDto(categoryEN);
        Category categoryFR = new Category(2, "Stratégie et investissement", of(indicatorFR));
        CategoryIndicatorDto categoryIndicatorFR = new CategoryIndicatorDto(categoryFR);

        when(categoryTranslationRepository
                .findTranslationForLanguage("fr", "Strategy and Investment"))
                .thenReturn("Stratégie et investissement");
        when(indicatorTranslationRepository.findTranslationForLanguage("fr", 1))
                .thenReturn(indicatorTranslationFR);
        when(scoreTranslationRepository.findTranslationForLanguage("fr", 1, 4))
                .thenReturn(scoreDefinitionFR);

        CategoryIndicatorDto translatedIndicator = translator.translate(categoryIndicatorEN, LanguageCode.fr);

        assertEquals(categoryIndicatorFR, translatedIndicator);
    }
}