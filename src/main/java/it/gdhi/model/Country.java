package it.gdhi.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema ="master", name="countries")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Country {

    @Id
    private String id;
    private String name;

}
