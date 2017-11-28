package it.gdhi.service;

import it.gdhi.dto.CountrySummaryDto;
import it.gdhi.dto.GdhiQuestionnaire;
import it.gdhi.dto.HealthIndicatorDto;
import it.gdhi.model.Country;
import it.gdhi.model.CountrySummary;
import it.gdhi.model.HealthIndicator;
import it.gdhi.model.id.HealthIndicatorId;
import it.gdhi.repository.ICountryRepository;
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
    private IHealthIndicatorRepository iHealthIndicatorRepository;

    @Autowired
    private  MailerService mailerService;



    @Transactional
    public List<Country> fetchCountries() {
        return iCountryRepository.findAll();
    }

    @Transactional
    public CountrySummaryDto fetchCountrySummary(String countryId) {
        CountrySummary countrySummary = iCountrySummaryRepository.findOne(countryId);
        return countrySummary != null ? new CountrySummaryDto(countrySummary) : new CountrySummaryDto();
    }

    @Transactional
    public void save(GdhiQuestionnaire gdhiQuestionnaire) {
         saveCountryContactInfo(gdhiQuestionnaire.getCountryId(), gdhiQuestionnaire.getCountrySummary());
         saveHealthIndicators(gdhiQuestionnaire.getCountryId(), gdhiQuestionnaire.getHealthIndicators());
         String feederName = gdhiQuestionnaire.getDataFeederName();
         String feederRole = gdhiQuestionnaire.getDataFeederRole();
         String contactEmail = gdhiQuestionnaire.getContactEmail();
         Country country = iCountryRepository.find(gdhiQuestionnaire.getCountryId());
         mailerService.send(country, feederName, feederRole, contactEmail);
    }

    public GdhiQuestionnaire getDetails(String countryId) {
        CountrySummary countrySummary = iCountrySummaryRepository.findOne(countryId);
        List<HealthIndicator> healthIndicators = iHealthIndicatorRepository.findHealthIndicatorsFor(countryId);
        CountrySummaryDto countrySummaryDto = new CountrySummaryDto(countrySummary);
        List<HealthIndicatorDto> healthIndicatorDtos = healthIndicators.stream()
                .map(HealthIndicatorDto::new)
                .collect(toList());
        return new GdhiQuestionnaire(countryId, countrySummaryDto, healthIndicatorDtos);
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

    private void saveCountryContactInfo(String countryId, CountrySummaryDto countrySummaryDetailDto) {
        CountrySummary countrySummary = new CountrySummary(countryId, countrySummaryDetailDto);
        iCountrySummaryRepository.save(countrySummary);
    }
}