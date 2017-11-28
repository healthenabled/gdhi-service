package it.gdhi.repository;

import it.gdhi.model.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ICountryRepository extends Repository<Country, Long> {

    List<Country> findAll();

    @Query("SELECT  c FROM Country c WHERE c.id = ?1")
    Country find(String id);

    Country save(Country country);
}