package it.gdhi.service.internationalization;

import it.gdhi.model.Category;
import it.gdhi.repository.ICategoryTranslationRepository;
import it.gdhi.utils.LanguageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static it.gdhi.utils.LanguageCode.en;
import static java.util.stream.Collectors.toList;

@Component
public class CategoryNameTranslator {

    private final ICategoryTranslationRepository translationRepository;

    @Autowired
    public CategoryNameTranslator(ICategoryTranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    public List<Category> translate(List<Category> categories, LanguageCode languageCode) {
        if (languageCode == en || languageCode == null) return categories;

        List<Category> collect = categories.stream()
                .map(category -> category.makeWithName(getCategoryTranslationForLanguage(languageCode, category)))
                .collect(toList());
        return collect;
    }

    private String getCategoryTranslationForLanguage(LanguageCode languageCode, Category c) {
        String newName = translationRepository.findTranslationForLanguage(languageCode.toString(), c.getId());
        return (newName == null || newName.isEmpty()) ? c.getName() : newName ;
    }

}
