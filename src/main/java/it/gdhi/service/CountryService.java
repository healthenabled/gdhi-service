package it.gdhi.service;

import it.gdhi.dto.CountrySummaryDetailDto;
import it.gdhi.dto.CountrySummaryDto;
import it.gdhi.dto.GdhiQuestionnaire;
import it.gdhi.dto.HealthIndicatorDto;
import it.gdhi.model.Country;
import it.gdhi.model.CountryResourceLink;
import it.gdhi.model.CountrySummary;
import it.gdhi.model.HealthIndicator;
import it.gdhi.model.id.HealthIndicatorId;
import it.gdhi.repository.ICountryRepository;
import it.gdhi.repository.ICountryResourceLinkRepository;
import it.gdhi.repository.ICountrySummaryRepository;
import it.gdhi.repository.IHealthIndicatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
public class CountryService {

    @Autowired
    private ICountryRepository iCountryRepository;

    @Autowired
    private ICountrySummaryRepository iCountrySummaryRepository;

    @Autowired
    private ICountryResourceLinkRepository iCountryResourceLinkRepository;

    @Autowired
    private IHealthIndicatorRepository iHealthIndicatorRepository;

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

    @Transactional
    public void save(GdhiQuestionnaire gdhiQuestionnaire) {
         saveCountryContactInfo(gdhiQuestionnaire.getCountryId(), gdhiQuestionnaire.getCountrySummary());
         saveHealthIndicators(gdhiQuestionnaire.getCountryId(), gdhiQuestionnaire.getHealthIndicators());
    }

    private void saveHealthIndicators(String countryId, List<HealthIndicatorDto> healthIndicatorDto) {
        List<HealthIndicator> healthIndicators = transformToHealthIndicator(countryId, healthIndicatorDto);
        if(healthIndicators != null) {
            healthIndicators.stream().forEach(health -> {
                iHealthIndicatorRepository.save(health);
            });
        }
    }

    private List<HealthIndicator> transformToHealthIndicator(String countryId,
                                                             List<HealthIndicatorDto> healthIndicatorDto) {
        return healthIndicatorDto.stream().map(dto -> {
            HealthIndicatorId healthIndicatorId = new HealthIndicatorId(countryId, dto.getCategoryId(),
                                                  dto.getIndicatorId());
            return new HealthIndicator(healthIndicatorId, dto.getScore(), dto.getSupportingText());
        }).collect(toList());
    }

    private void saveCountryContactInfo(String countryId, CountrySummaryDetailDto countrySummaryDetailDto) {
        CountrySummary countrySummary = new CountrySummary(countryId, countrySummaryDetailDto);
        iCountrySummaryRepository.save(countrySummary);
    }

}