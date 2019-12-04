package it.gdhi.internationalization.service;

import it.gdhi.internationalization.repository.ICategoryTranslationRepository;
import it.gdhi.utils.LanguageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static it.gdhi.utils.LanguageCode.en;

@Component
public class CategoryNameTranslator {

    private final ICategoryTranslationRepository translationRepository;

    @Autowired
    public CategoryNameTranslator(ICategoryTranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    public String translate(String categoryName, LanguageCode languageCode) {
        if (languageCode == en || languageCode == null) return categoryName;

        return getCategoryTranslationForLanguage(languageCode, categoryName);
    }

    private String getCategoryTranslationForLanguage(LanguageCode languageCode, String categoryName) {
        String translationCategoryName = translationRepository.findTranslationForLanguage(languageCode.toString(), categoryName);
        return (translationCategoryName == null || translationCategoryName.isEmpty()) ? categoryName : translationCategoryName ;
    }

}
