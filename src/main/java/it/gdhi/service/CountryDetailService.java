package it.gdhi.service;

import it.gdhi.model.CountryDetail;
import it.gdhi.repository.CountryDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class CountryDetailService {

    private CountryDetailRepository repository;

    @Autowired
    CountryDetailService(CountryDetailRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void create() {
        CountryDetail created = new CountryDetail("India");
        repository.save(created);
    }

}