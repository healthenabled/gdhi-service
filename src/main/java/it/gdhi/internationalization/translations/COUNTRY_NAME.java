package it.gdhi.internationalization.translations;

import lombok.Getter;

@Getter
public enum COUNTRY_NAME {
    en("Country Name"),
    es("Nombre del país"),
    fr("Nom du pays"),
    pt("Nome do país"),
    ar("");

    private String translatedText;

    COUNTRY_NAME(String translatedText) {
        this.translatedText = translatedText;
    }
}
