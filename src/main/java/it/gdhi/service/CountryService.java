package it.gdhi.service;

import it.gdhi.model.Country;
import it.gdhi.repository.ICountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class CountryService {

    @Autowired
    private ICountryRepository repository;

    @Transactional
    public Country fetchCountry() {
        return repository.findAll().stream().findFirst().orElse(null);
    }

}