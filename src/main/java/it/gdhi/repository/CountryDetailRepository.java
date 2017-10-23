package it.gdhi.repository;

import it.gdhi.model.CountryDetail;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CountryDetailRepository extends Repository<CountryDetail, Long> {

    void delete(CountryDetail deleted);

    List<CountryDetail> findAll();

    Optional<CountryDetail> findOne(Long id);

    CountryDetail save(CountryDetail persisted);
}