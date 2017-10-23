package it.gdhi.service;

import it.gdhi.model.CountryDetail;
import it.gdhi.repository.CountryDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class CountryDetailService {

    @Autowired
    private CountryDetailRepository repository;

    @Transactional
    public void insert() {
        CountryDetail created = new CountryDetail("India");
        repository.save(created);
    }

}