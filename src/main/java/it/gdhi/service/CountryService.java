package it.gdhi.service;

import it.gdhi.dto.CountrySummaryDto;
import it.gdhi.dto.GdhiQuestionnaire;
import it.gdhi.dto.HealthIndicatorDto;
import it.gdhi.model.Country;
import it.gdhi.model.CountryHealthIndicator;
import it.gdhi.model.CountrySummary;
import it.gdhi.repository.ICountryHealthIndicatorRepository;
import it.gdhi.repository.ICountryRepository;
import it.gdhi.repository.ICountrySummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


@Service
public class CountryService {

    @Autowired
    private ICountryRepository iCountryRepository;

    @Autowired
    private ICountrySummaryRepository iCountrySummaryRepository;

    @Autowired
    private ICountryHealthIndicatorRepository iCountryHealthIndicatorRepository;

    @Transactional
    public List<Country> fetchCountries() {
        return iCountryRepository.findAll();
    }

    @Transactional
    public CountrySummaryDto fetchCountrySummary(String countryId) {
        CountrySummary countrySummary = iCountrySummaryRepository.findOne(countryId);
        return Optional.ofNullable(countrySummary).map(CountrySummaryDto::new).orElse(new CountrySummaryDto());
    }

    public GdhiQuestionnaire getDetails(String countryId) {
        CountrySummary countrySummary = iCountrySummaryRepository.findOne(countryId);
        List<CountryHealthIndicator> countryHealthIndicators =
                iCountryHealthIndicatorRepository.findHealthIndicatorsFor(countryId);
        CountrySummaryDto countrySummaryDto = Optional.ofNullable(countrySummary)
                .map(CountrySummaryDto::new)
                .orElse(null);
        List<HealthIndicatorDto> healthIndicatorDtos = countryHealthIndicators.stream()
                .map(HealthIndicatorDto::new)
                .collect(toList());
        return new GdhiQuestionnaire(countryId, countrySummaryDto, healthIndicatorDtos);
    }
}