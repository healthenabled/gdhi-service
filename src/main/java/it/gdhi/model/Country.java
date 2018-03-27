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
    private UUID unique_id;
    @Column(name = "alpha_2_code")
    private String alpha2Code;

}
