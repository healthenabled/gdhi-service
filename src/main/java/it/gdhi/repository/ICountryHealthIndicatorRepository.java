package it.gdhi.repository;

import it.gdhi.model.CountryHealthIndicator;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ICountryHealthIndicatorRepository extends Repository<CountryHealthIndicator, Long> {

    List<CountryHealthIndicator> findAll();

    @Query("SELECT h FROM CountryHealthIndicator h WHERE h.countryHealthIndicatorId.countryId = ?1")
    List<CountryHealthIndicator> findHealthIndicatorsFor(String countryId);

    @Query("SELECT distinct (countryHealthIndicatorId.countryId) FROM CountryHealthIndicator")
    List<String> findCountriesWithHealthScores();

    CountryHealthIndicator save(CountryHealthIndicator countryHealthIndicatorSetupData);

    @Query("SELECT h FROM CountryHealthIndicator h WHERE (?1 is null or h.category.id = ?1)")
    List<CountryHealthIndicator> find(Integer categoryId);

}

