package it.gdhi.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="country_details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CountryDetail {

    @Id
    private String name;

}
