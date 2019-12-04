package it.gdhi.utils;

/* Do not change, dependency on front-end */
public enum LanguageCode {
    en("english"), es("spanish"), fr("french"), pt("portuguese"), ar("arabic");

    private String name;
    public static final String USER_LANGUAGE = "user_language";

    LanguageCode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
