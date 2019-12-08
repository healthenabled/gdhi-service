package it.gdhi.utils;


import org.junit.Test;

import static it.gdhi.utils.LanguageCode.en;
import static it.gdhi.utils.LanguageCode.pt;
import static org.junit.Assert.assertEquals;
public class LanguageCodeTest {

    @Test
    public void shouldReturnAppropriateCodeForGivenLanguageCode() {
        LanguageCode code = LanguageCode.getValueFor("pt");

        assertEquals(code, pt);
    }

    @Test
    public void shouldReturnDefaultCodeENGivenLanguageCodeNotFound() {
        LanguageCode code = LanguageCode.getValueFor("abc");

        assertEquals(code, en);
    }
}