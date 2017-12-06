package it.gdhi.repository;

import it.gdhi.model.CountryResourceLink;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ICountryResourceLinkRepository extends Repository<CountryResourceLink, String> {

    @Query("SELECT c from CountryResourceLink c where c.countryResourceLinkId.countryId = ?1")
    List<CountryResourceLink> findAllBy(String countryId);

    @Modifying
    @Query("DELETE FROM CountryResourceLink c WHERE c.countryResourceLinkId.countryId = UPPER(?1)")
    void deleteResources(String countryId);
}
