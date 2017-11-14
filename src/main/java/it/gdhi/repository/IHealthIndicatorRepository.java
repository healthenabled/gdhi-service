package it.gdhi.repository;

import it.gdhi.model.HealthIndicator;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface IHealthIndicatorRepository extends Repository<HealthIndicator, Long> {

    List<HealthIndicator> findAll();

    @Query("SELECT h FROM HealthIndicator h WHERE h.healthIndicatorId.countryId = ?1")
    //TODO tests for non existing id
    List<HealthIndicator> findHealthIndicatorsFor(String countryId);

    @Query("SELECT distinct (h.healthIndicatorId.countryId) FROM HealthIndicator h")
    List<String> findCountriesWithHealthScores();

}

