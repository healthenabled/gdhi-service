package it.gdhi.service;

import it.gdhi.dto.CountryDTO;
import it.gdhi.dto.CountrySummaryDto;
import it.gdhi.dto.GdhiQuestionnaire;
import it.gdhi.dto.HealthIndicatorDto;
import it.gdhi.model.Country;
import it.gdhi.model.CountryHealthIndicator;
import it.gdhi.model.CountrySummary;
import it.gdhi.repository.ICountryHealthIndicatorRepository;
import it.gdhi.repository.ICountryRepository;
import it.gdhi.repository.ICountrySummaryRepository;
import it.gdhi.utils.LanguageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static it.gdhi.utils.FormStatus.PUBLISHED;
import static it.gdhi.utils.LanguageCode.EN;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;


@Service
public class CountryService {

    @Autowired
    private ICountryRepository iCountryRepository;

    @Autowired
    private ICountrySummaryRepository iCountrySummaryRepository;

    @Autowired
    private ICountryHealthIndicatorRepository iCountryHealthIndicatorRepository;

    public List<CountryDTO> fetchCountries(LanguageCode languageCode) {
        List<Country> countries = iCountryRepository.findAll();
        return countries.stream()
                        .map(country -> country.convertToLanguage(languageCode))
                        .collect(toList());
    }

    public CountrySummaryDto fetchCountrySummary(String countryId) {
        CountrySummary countrySummary = iCountrySummaryRepository.findByCountryAndStatus(countryId, PUBLISHED.name());
        return Optional.ofNullable(countrySummary).map(CountrySummaryDto::new).orElse(new CountrySummaryDto());
    }

    public GdhiQuestionnaire getDetails(UUID countryUUID, boolean publishedOnly) {
        String countryId = iCountryRepository.findByUUID(countryUUID).getId();

        GdhiQuestionnaire gdhiQuestionnaire = null;
        List<CountrySummary> countrySummaries;

        if (!publishedOnly)
            countrySummaries = iCountrySummaryRepository.findAll(countryId);
        else
            countrySummaries = asList(iCountrySummaryRepository.findByCountryAndStatus(countryId, PUBLISHED.name()));

        if (countrySummaries != null) {
            CountrySummary countrySummary = countrySummaries.size() > 1 ?
                    getUnPublishedCountrySummary(countrySummaries) :
                    Optional.ofNullable(countrySummaries.get(0)).get();

            List<CountryHealthIndicator> sortedIndicators = getCountryHealthIndicators(countryId, countrySummary);
            gdhiQuestionnaire = constructGdhiQuestionnaire(countryId, countrySummary, sortedIndicators);
        }

        return gdhiQuestionnaire;
    }

    private List<CountryHealthIndicator> getCountryHealthIndicators(String countryId, CountrySummary countrySummary) {
        List<CountryHealthIndicator> countryHealthIndicators =
                iCountryHealthIndicatorRepository.findByCountryIdAndStatus(countryId,
                        countrySummary.getCountrySummaryId().getStatus());

        return countryHealthIndicators.stream().sorted(
                Comparator.comparing(o -> o.getIndicator().getRank()))
                .collect(Collectors.toList());
    }

    private CountrySummary getUnPublishedCountrySummary(List<CountrySummary> countrySummaries) {
        return countrySummaries.stream()
                .filter(countrySummaryTmp -> !countrySummaryTmp.getCountrySummaryId().getStatus()
                        .equalsIgnoreCase(PUBLISHED.name())).findFirst().get();
    }

    private GdhiQuestionnaire constructGdhiQuestionnaire(String countryId, CountrySummary countrySummary,
                                                         List<CountryHealthIndicator> sortedIndicators) {
        GdhiQuestionnaire gdhiQuestionnaire;CountrySummaryDto countrySummaryDto = Optional.ofNullable(countrySummary)
                .map(CountrySummaryDto::new)
                .orElse(null);
        List<HealthIndicatorDto> healthIndicatorDtos = sortedIndicators.stream()
                .map(HealthIndicatorDto::new)
                .collect(toList());
        gdhiQuestionnaire = new GdhiQuestionnaire(countryId, countrySummary.getCountrySummaryId().getStatus(),
                countrySummaryDto, healthIndicatorDtos);
        return gdhiQuestionnaire;
    }
}