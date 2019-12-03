package it.gdhi.service.internationalization;

import it.gdhi.model.Country;
import it.gdhi.repository.ICountryTranslationRepository;
import it.gdhi.utils.LanguageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static it.gdhi.utils.LanguageCode.en;
import static java.util.stream.Collectors.toList;

@Component
public class CountryNameTranslator {

    private final ICountryTranslationRepository translationRepository;

    @Autowired
    public CountryNameTranslator(ICountryTranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    public List<Country> translate(List<Country> countries, LanguageCode languageCode) {
        if (languageCode == en || languageCode == null) return countries;

        return countries.stream()
                        .map(country -> country.makeWithName(getCountryTranslationForLanguage(languageCode, country)))
                        .collect(toList());
    }

    private String getCountryTranslationForLanguage(LanguageCode languageCode, Country c) {
        String newName = translationRepository.findTranslationForLanguage(languageCode.toString(), c.getId());
        return (newName == null || newName.isEmpty()) ? c.getName() : newName ;
    }

}
