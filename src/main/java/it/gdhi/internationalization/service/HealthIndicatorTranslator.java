package it.gdhi.internationalization.service;

import it.gdhi.dto.CategoryIndicatorDto;
import it.gdhi.dto.IndicatorDto;
import it.gdhi.dto.ScoreDto;
import it.gdhi.internationalization.model.IndicatorTranslation;
import it.gdhi.internationalization.repository.ICategoryTranslationRepository;
import it.gdhi.internationalization.repository.IIndicatorTranslationRepository;
import it.gdhi.utils.LanguageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static it.gdhi.utils.LanguageCode.en;

@Component
public class HealthIndicatorTranslator {

    private final ICategoryTranslationRepository categoryTranslationRepository;
    private final IIndicatorTranslationRepository indicatorTranslationRepo;

    @Autowired
    public HealthIndicatorTranslator(ICategoryTranslationRepository categoryTranslationRepository,
                                     IIndicatorTranslationRepository indicatorTranslationRepo) {
        this.categoryTranslationRepository = categoryTranslationRepository;
        this.indicatorTranslationRepo = indicatorTranslationRepo;
    }

    public CategoryIndicatorDto translate(CategoryIndicatorDto categoryIndicatorDto, LanguageCode languageCode) {
        if (languageCode == en || languageCode == null) return categoryIndicatorDto;

        String translatedCategoryName = getCategoryTranslationForLanguage(languageCode,
                                                                          categoryIndicatorDto.getCategoryName());
        categoryIndicatorDto
                .translateCategoryName(translatedCategoryName)
                .getIndicators()
                .forEach(indicator -> {
                    translateIndicator(languageCode, indicator);
                });
        return categoryIndicatorDto;
    }

    public String getTranslatedCategoryName(String categoryName, LanguageCode languageCode) {
        if (languageCode == en || languageCode == null) return categoryName;

        return getCategoryTranslationForLanguage(languageCode, categoryName);
    }

    private String getCategoryTranslationForLanguage(LanguageCode languageCode, String categoryName) {
        String translationCategoryName = categoryTranslationRepository.findTranslationForLanguage(
                                                                                            languageCode.toString(),
                                                                                            categoryName);
        return (translationCategoryName == null || translationCategoryName.isEmpty()) ?
                categoryName : translationCategoryName;
    }

    private void translateIndicator(LanguageCode languageCode, IndicatorDto indicator) {
        IndicatorTranslation translatedIndicator = indicatorTranslationRepo.findTranslationForLanguage(
                languageCode.toString(),
                indicator.getIndicatorId());
        indicator.translateName(translatedIndicator.getName());
        indicator.translateDefinition(translatedIndicator.getDefinition());
    }

    private void translateScore(LanguageCode languageCode, ScoreDto score) {
        //TODO
    }

}
