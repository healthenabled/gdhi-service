package it.gdhi.model;

import it.gdhi.dto.CountryDTO;
import it.gdhi.utils.LanguageCode;
import org.junit.Test;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;


public class CountryTest {

    @Test
    public void shouldReturnCountryNameInFrench() {
        Country chile = new Country("CHL", "Chile", randomUUID(), "CH", "SpanishName", "FrenchName", "PortugueseName", "ArabicName");
        CountryDTO expectedChileDTO = new CountryDTO("CHL", "FrenchName", chile.getUniqueId(), "CH");

        CountryDTO actualChileDTO = chile.convertToLanguage(LanguageCode.FR);

        assertEquals(expectedChileDTO.getName(), actualChileDTO.getName());
    }

}
