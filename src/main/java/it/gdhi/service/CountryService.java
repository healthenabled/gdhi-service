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
import it.gdhi.utils.FormStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.emptyList;
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
        CountrySummary countrySummary = iCountrySummaryRepository.findByCountryAndStatus(countryId, "PUBLISHED");
        return Optional.ofNullable(countrySummary).map(CountrySummaryDto::new).orElse(new CountrySummaryDto());
    }

    public GdhiQuestionnaire getDetails(UUID countryUUID,boolean isPublishOnly) {

        String countryId = iCountryRepository.findByUUID(countryUUID).getId();

        GdhiQuestionnaire gdhiQuestionnaire = null;

        List<CountrySummary> countrySummaries = emptyList();

        if(!isPublishOnly)countrySummaries = iCountrySummaryRepository.findAll(countryId);
        else countrySummaries = Arrays.asList(iCountrySummaryRepository.findByCountryAndStatus(countryId,
                FormStatus.PUBLISHED.name()));

        if(countrySummaries != null) {
            CountrySummary countrySummary = countrySummaries.size() > 1 ?
                    countrySummaries.stream()
                            .filter(countrySummaryTmp -> !countrySummaryTmp.getCountrySummaryId().getStatus()
                                    .equalsIgnoreCase("PUBLISHED")).findFirst().get() :
                    Optional.ofNullable(countrySummaries.get(0)).get();

            List<CountryHealthIndicator> countryHealthIndicators =
                    iCountryHealthIndicatorRepository.findHealthIndicatorsByStatus(countryId,
                            countrySummary.getCountrySummaryId().getStatus());
            CountrySummaryDto countrySummaryDto = Optional.ofNullable(countrySummary)
                    .map(CountrySummaryDto::new)
                    .orElse(null);
            List<HealthIndicatorDto> healthIndicatorDtos = countryHealthIndicators.stream()
                    .map(HealthIndicatorDto::new)
                    .collect(toList());
            gdhiQuestionnaire = new GdhiQuestionnaire(countryId, countrySummary.getCountrySummaryId().getStatus(),
                    countrySummaryDto, healthIndicatorDtos);
        }

        return gdhiQuestionnaire;
    }
}