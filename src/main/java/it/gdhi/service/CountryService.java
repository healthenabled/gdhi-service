package it.gdhi.service;

import it.gdhi.model.Country;
import it.gdhi.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class CountryService {

    @Autowired
    private CountryRepository repository;

    @Transactional
    public Country insert() {
        Country created = new Country("India", "in");
        return repository.save(created);
    }

}