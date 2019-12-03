package it.gdhi.service.internationalization;

import com.google.common.collect.ImmutableList;
import it.gdhi.model.Country;
import it.gdhi.repository.ICountryTranslationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static it.gdhi.utils.LanguageCode.en;
import static it.gdhi.utils.LanguageCode.fr;
import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CountryNameTranslatorTest {

    @InjectMocks
    private CountryNameTranslator translator;
    @Mock
    private ICountryTranslationRepository translationRepository;

    @Test
    public void shouldReturnCountryNamesInEnglishGivenUserLanguageIsNull() {
        Country chileEN = new Country("CHL", "Chile", randomUUID(), "CH");
        Country malaysiaEN = new Country("MYL", "Malaysia", randomUUID(), "MY");
        List<Country> expectedCountries = ImmutableList.of(chileEN, malaysiaEN);

        List<Country> actualCountries = translator.translate(expectedCountries, null);

        assertEquals(expectedCountries, actualCountries);
    }

    @Test
    public void shouldNotInvokeTranslationRepositoryGivenUserLanguageIsNull() {
        Country chileEN = new Country("CHL", "Chile", randomUUID(), "CH");
        Country malaysiaEN = new Country("MYL", "Malaysia", randomUUID(), "MY");
        List<Country> expectedCountries = ImmutableList.of(chileEN, malaysiaEN);

        translator.translate(expectedCountries, null);

        verify(translationRepository, never()).findTranslationForLanguage(anyString(), anyString());
    }

    @Test
    public void shouldReturnCountryNamesInEnglishGivenUserLanguageIsEnglish() {
        Country chileEN = new Country("CHL", "Chile", randomUUID(), "CH");
        Country malaysiaEN = new Country("MYL", "Malaysia", randomUUID(), "MY");
        List<Country> expectedCountries = ImmutableList.of(chileEN, malaysiaEN);

        when(translationRepository.findTranslationForLanguage("en", chileEN.getId())).thenReturn("Chile");
        when(translationRepository.findTranslationForLanguage("en", malaysiaEN.getId())).thenReturn("Malaysia");

        List<Country> actualCountries = translator.translate(expectedCountries, en);

        assertEquals(expectedCountries, actualCountries);
    }

    @Test
    public void shouldReturnCountryNamesInFrenchGivenUserLanguageIsFrench() {
        Country chileEN = new Country("CHL", "Chile", randomUUID(), "CH");
        Country malaysiaEN = new Country("MYL", "Malaysia", randomUUID(), "MY");
        List<Country> countriesInEnglish = ImmutableList.of(chileEN, malaysiaEN);
        Country chileFR = new Country("CHL", "Chili", chileEN.getUniqueId(), "CH");
        Country malaysiaFR = new Country("MYL", "Malaisie", malaysiaEN.getUniqueId(), "MY");
        List<Country> translatedCountries = ImmutableList.of(chileFR, malaysiaFR);

        when(translationRepository.findTranslationForLanguage("fr", chileEN.getId())).thenReturn("Chili");
        when(translationRepository.findTranslationForLanguage("fr", malaysiaEN.getId())).thenReturn("Malaisie");

        List<Country> actualCountries = translator.translate(countriesInEnglish, fr);

        assertEquals(translatedCountries, actualCountries);
    }

    @Test
    public void shouldReturnCountryNameInEnglishGivenCountryNameInUserLanguageIsNull() {
        Country chileEN = new Country("CHL", "Chile", randomUUID(), "CH");
        Country malaysiaEN = new Country("MYL", "Malaysia", randomUUID(), "MY");
        List<Country> countriesInEnglish = ImmutableList.of(chileEN, malaysiaEN);
        Country chileFR = new Country("CHL", "Chili", chileEN.getUniqueId(), "CH");
        List<Country> translatedCountries = ImmutableList.of(chileFR, malaysiaEN);

        when(translationRepository.findTranslationForLanguage("fr", chileEN.getId())).thenReturn("Chili");
        when(translationRepository.findTranslationForLanguage("fr", malaysiaEN.getId())).thenReturn(null);

        List<Country> actualCountries = translator.translate(countriesInEnglish, fr);

        assertEquals(translatedCountries, actualCountries);
    }

    @Test
    public void shouldReturnCountryNameInEnglishGivenCountryNameInUserLanguageIsEmptyString() {
        Country chileEN = new Country("CHL", "Chile", randomUUID(), "CH");
        Country malaysiaEN = new Country("MYL", "Malaysia", randomUUID(), "MY");
        List<Country> countriesInEnglish = ImmutableList.of(chileEN, malaysiaEN);
        Country chileFR = new Country("CHL", "Chili", chileEN.getUniqueId(), "CH");
        List<Country> translatedCountries = ImmutableList.of(chileFR, malaysiaEN);

        when(translationRepository.findTranslationForLanguage("fr", chileEN.getId())).thenReturn("Chili");
        when(translationRepository.findTranslationForLanguage("fr", malaysiaEN.getId())).thenReturn("");

        List<Country> actualCountries = translator.translate(countriesInEnglish, fr);

        assertEquals(translatedCountries, actualCountries);
    }

}