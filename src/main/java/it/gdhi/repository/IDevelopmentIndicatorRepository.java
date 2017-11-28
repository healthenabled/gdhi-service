package it.gdhi.repository;

import it.gdhi.model.DevelopmentIndicator;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface IDevelopmentIndicatorRepository extends Repository<DevelopmentIndicator, Long> {

    List<DevelopmentIndicator> findAll();

    DevelopmentIndicator save(DevelopmentIndicator developmentIndicator);

    @Query("SELECT d FROM DevelopmentIndicator d WHERE d.countryId = ?1")
    Optional<DevelopmentIndicator> findByCountryId(String countryCode);
}