package it.gdhi.utils;

public enum LanguageCode {
    EN("english"), ES("spanish"), FR("french"), PT("portuguese"), AR("arabic");

    private String name;

    LanguageCode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
