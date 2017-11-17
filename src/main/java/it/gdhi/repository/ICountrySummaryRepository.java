package it.gdhi.repository;

import it.gdhi.model.CountrySummary;
import org.springframework.data.repository.Repository;

public interface ICountrySummaryRepository extends Repository<CountrySummary, String> {

    CountrySummary findOne(String countryId);

    CountrySummary save(CountrySummary countrySummary);
}
