package it.gdhi.repository;

import it.gdhi.model.CountrySummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface ICountrySummaryRepository extends Repository<CountrySummary, String> {

    @Query("SELECT c FROM CountrySummary c WHERE c.countryId = UPPER(?1)")
    CountrySummary findOne(String countryId);

    CountrySummary save(CountrySummary countrySummary);
}
