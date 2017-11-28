package it.gdhi.model;

import it.gdhi.model.id.CountryResourceLinkId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema ="validated_config", name="country_resource_links")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CountryResourceLink {

    @EmbeddedId
    private CountryResourceLinkId countryResourceLinkId;

    public String getLink(){
        return countryResourceLinkId.getLink();
    }
}