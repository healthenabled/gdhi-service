package it.gdhi.service;

import it.gdhi.dto.CountrySummaryDto;
import it.gdhi.dto.GdhiQuestionnaire;
import it.gdhi.dto.HealthIndicatorDto;
import it.gdhi.model.Country;
import it.gdhi.model.CountryHealthIndicator;
import it.gdhi.model.CountrySummary;
import it.gdhi.model.id.CountryHealthIndicatorId;
import it.gdhi.repository.ICountryHealthIndicatorRepository;
import it.gdhi.repository.ICountryRepository;
import it.gdhi.repository.ICountryResourceLinkRepository;
import it.gdhi.repository.ICountrySummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CountryHealthDataService {

    @Autowired
    private ICountryHealthIndicatorRepository iCountryHealthIndicatorRepository;

    @Autowired
    private ICountryRepository iCountryRepository;

    @Autowired
    private  MailerService mailerService;

    @Autowired
    private ICountryResourceLinkRepository iCountryResourceLinkRepository;

    @Autowired
    private ICountrySummaryRepository iCountrySummaryRepository;

    @Transactional
    public void save(GdhiQuestionnaire gdhiQuestionnaire) {
        saveCountryContactInfo(gdhiQuestionnaire.getCountryId(),
                gdhiQuestionnaire.getStatus(), gdhiQuestionnaire.getCountrySummary());
        saveHealthIndicators(gdhiQuestionnaire.getCountryId(),
                gdhiQuestionnaire.getStatus(), gdhiQuestionnaire.getHealthIndicators());
        String feederName = gdhiQuestionnaire.getDataFeederName();
        String feederRole = gdhiQuestionnaire.getDataFeederRole();
        String contactEmail = gdhiQuestionnaire.getContactEmail();
        Country country = iCountryRepository.find(gdhiQuestionnaire.getCountryId());
        mailerService.send(country, feederName, feederRole, contactEmail);
    }

    private void saveCountryContactInfo(String countryId, String status ,
                                        CountrySummaryDto countrySummaryDetailDto) {
        CountrySummary countrySummary = new CountrySummary(countryId, status,
                countrySummaryDetailDto);
        iCountryResourceLinkRepository.deleteResources(countryId);
        iCountrySummaryRepository.save(countrySummary);
    }

    private void saveHealthIndicators(String countryId, String status,
                                      List<HealthIndicatorDto> healthIndicatorDto) {
        List<CountryHealthIndicator> countryHealthIndicators = transformToHealthIndicator(countryId, status,
                healthIndicatorDto);
        if(countryHealthIndicators != null) {
            countryHealthIndicators.forEach(health ->
                    iCountryHealthIndicatorRepository.save(health));
        }
    }

    private List<CountryHealthIndicator> transformToHealthIndicator(String countryId,
                                                                    String status,
                                                                    List<HealthIndicatorDto> healthIndicatorDto) {
        return healthIndicatorDto.stream().map(dto -> {
            CountryHealthIndicatorId countryHealthIndicatorId = new CountryHealthIndicatorId(countryId,
                    dto.getCategoryId(), dto.getIndicatorId(), status);
            return new CountryHealthIndicator(countryHealthIndicatorId, dto.getScore(), dto.getSupportingText());
        }).collect(toList());
    }

}