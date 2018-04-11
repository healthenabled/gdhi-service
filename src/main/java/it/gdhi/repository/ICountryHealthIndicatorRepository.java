package it.gdhi.repository;

import it.gdhi.model.CountryHealthIndicator;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ICountryHealthIndicatorRepository extends Repository<CountryHealthIndicator, Long> {

    List<CountryHealthIndicator> findAll();

    @Query("SELECT h FROM CountryHealthIndicator h WHERE h.countryHealthIndicatorId.countryId = ?1 " +
            "and h.countryHealthIndicatorId.status = 'PUBLISHED'")
    List<CountryHealthIndicator> findHealthIndicatorsFor(String countryId);

    @Query("SELECT h FROM CountryHealthIndicator h WHERE " +
            "h.countryHealthIndicatorId.countryId = ?1 and h.countryHealthIndicatorId.status=?2")
    List<CountryHealthIndicator> findHealthIndicatorsByStatus(String countryId, String status);

    @Query("SELECT distinct (countryHealthIndicatorId.countryId) FROM CountryHealthIndicator")
    List<String> findCountriesWithHealthScores();

    CountryHealthIndicator save(CountryHealthIndicator countryHealthIndicatorSetupData);

    @Query("SELECT h FROM CountryHealthIndicator h WHERE (?1 is null or h.category.id = ?1) "+
            "and h.countryHealthIndicatorId.status=?2")
    List<CountryHealthIndicator> findByStatus(Integer categoryId, String currentStatus);

    @Modifying
    @Query("DELETE FROM CountryHealthIndicator h WHERE " +
            "h.countryHealthIndicatorId.countryId = ?1 and h.countryHealthIndicatorId.status=?2")
    void removeHealthIndicators(String countryId, String currentStatus);
}

