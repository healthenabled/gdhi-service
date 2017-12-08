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

    @Query("SELECT distinct (healthIndicatorId.countryId) FROM HealthIndicator")
    List<String> findCountriesWithHealthScores();

    HealthIndicator save(HealthIndicator healthIndicatorSetupData);

    @Query("SELECT h FROM HealthIndicator h WHERE (?1 is null or h.category.id = ?1) " +
            "and (?2 is null or h.score = ?2)")
    List<HealthIndicator> find(Integer categoryId, Integer score);

}

