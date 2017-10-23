package it.gdhi.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="country_details")
@AllArgsConstructor
@NoArgsConstructor
public class CountryDetail {

    @Id
    private String name;

}
