package it.gdhi.repository;

import it.gdhi.model.CountrySummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ICountrySummaryRepository extends Repository<CountrySummary, String> {

    @Query("SELECT c FROM CountrySummary c WHERE c.countrySummaryId.countryId = UPPER(?1)")
    CountrySummary findOne(String countryId);

    @Query("SELECT c FROM CountrySummary c WHERE c.countrySummaryId.countryId = UPPER(?1) and" +
            " c.countrySummaryId.status= UPPER(?2)")
    CountrySummary findByCountryAndStatus(String countryId, String status);

    @Query("SELECT c FROM CountrySummary c WHERE c.countrySummaryId.countryId = UPPER(?1)")
    List<CountrySummary> findAll(String countryId);

    CountrySummary save(CountrySummary countrySummary);

    @Query("SELECT c.countrySummaryId.status from CountrySummary c where" +
            " c.countrySummaryId.status <> 'PUBLISHED' and " +
            "c.countrySummaryId.countryId = UPPER(?1)")
    String getCountrySummaryStatus(String countryId);
}
