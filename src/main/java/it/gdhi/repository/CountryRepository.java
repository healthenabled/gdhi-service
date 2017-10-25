package it.gdhi.repository;

import it.gdhi.model.Country;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends Repository<Country, Long> {

    void delete(Country country);

    List<Country> findAll();

    Optional<Country> findOne(Long id);

    Country save(Country country);
}