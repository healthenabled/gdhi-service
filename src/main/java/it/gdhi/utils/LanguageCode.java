package it.gdhi.utils;

/* Do not change, dependency on front-end */
public enum LanguageCode {
    en("english"), es("spanish"), fr("french"), pt("portuguese"), ar("arabic");

    private String name;

    LanguageCode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
