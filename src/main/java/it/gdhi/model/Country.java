package it.gdhi.model;

import it.gdhi.dto.CountryDTO;
import it.gdhi.utils.LanguageCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.UUID;

@Entity
@Table(schema = "master", name = "countries")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Slf4j
public class Country {

    @Id
    private String id;
    private String name;
    @Column(name = "unique_id")
    private UUID uniqueId;
    @Column(name = "alpha_2_code")
    private String alpha2Code;

    @Column(name = "name_spanish")
    public String spanish;
    @Column(name = "name_french")
    String french;
    @Column(name = "name_portuguese")
    public String portuguese;
    @Column(name = "name_arabic")
    public String arabic;

    public Country(String id, String name, UUID uniqueId, String alpha2Code) {
        this.id = id;
        this.name = name;
        this.uniqueId = uniqueId;
        this.alpha2Code = alpha2Code;
    }

    public CountryDTO convertToLanguage(LanguageCode languageCode) {
        String languageName = languageCode.getName();
        String countryName = name;
        try {
            Field field = this.getClass().getDeclaredField(languageName);
            field.setAccessible(true);
            countryName = (String) field.get(this);
        }
        catch (NoSuchFieldException e) {
            log.error("Error mapping country name in the language chosen by user. Requested language: {} ", languageCode.getName());
        }
        finally {
            return new CountryDTO(this.id, countryName, this.uniqueId, this.alpha2Code);
        }
    }
}
