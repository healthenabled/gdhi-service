package it.gdhi.repository;

import it.gdhi.model.CountrySummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ICountrySummaryRepository extends Repository<CountrySummary, String> {

    @Query("SELECT c FROM CountrySummary c WHERE c.countryId = UPPER(?1)")
    CountrySummary findOne(String countryId);

    @Query("SELECT c FROM CountrySummary c WHERE c.countryId = UPPER(?1) and c.status= UPPER(?2)")
    CountrySummary findByCountryAndStatus(String countryId, String status);

    @Query("SELECT c FROM CountrySummary c WHERE c.countryId = UPPER(?1)")
    List<CountrySummary> findAll(String countryId);

    CountrySummary save(CountrySummary countrySummary);
}
