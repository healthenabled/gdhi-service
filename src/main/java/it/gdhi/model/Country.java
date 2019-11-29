package it.gdhi.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(schema = "master", name = "countries")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Country {

    @Id
    private String id;
    private String name;
    @Column(name = "unique_id")
    private UUID uniqueId;
    @Column(name = "alpha_2_code")
    private String alpha2Code;

    @Column(name = "name_spanish")
    private String nameSpanish;
    @Column(name = "name_french")
    private String nameFrench;
    @Column(name = "name_portuguese")
    private String namePortuguese;
    @Column(name = "name_arabic")
    private String nameArabic;

    public Country(String id, String name, UUID uniqueId, String alpha2Code) {
        this.id = id;
        this.name = name;
        this.uniqueId = uniqueId;
        this.alpha2Code = alpha2Code;
    }
}
