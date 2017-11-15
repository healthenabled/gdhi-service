package it.gdhi.service;

import it.gdhi.dto.CountrySummaryDto;
import it.gdhi.model.Country;
import it.gdhi.model.CountryResourceLink;
import it.gdhi.model.CountrySummary;
import it.gdhi.repository.ICountryRepository;
import it.gdhi.repository.ICountryResourceLinkRepository;
import it.gdhi.repository.ICountrySummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class CountryService {

    @Autowired
    private ICountryRepository iCountryRepository;

    @Autowired
    private ICountrySummaryRepository iCountrySummaryRepository;

    @Autowired
    private ICountryResourceLinkRepository iCountryResourceLinkRepository;

    @Transactional
    public List<Country> fetchCountries() {
        return iCountryRepository.findAll();
    }

    @Transactional
    public CountrySummaryDto fetchCountrySummary(String countryId) {
        CountrySummary countrySummary = iCountrySummaryRepository.findOne(countryId);
        List<CountryResourceLink> countryResourceLinks = iCountryResourceLinkRepository.findAllBy(countryId);
        return new CountrySummaryDto(countrySummary, countryResourceLinks);
    }


}