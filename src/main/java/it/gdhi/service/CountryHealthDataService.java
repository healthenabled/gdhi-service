package it.gdhi.service;

import it.gdhi.dto.CountrySummaryDto;
import it.gdhi.dto.CountryUrlGenerationStatusDto;
import it.gdhi.dto.GdhiQuestionnaire;
import it.gdhi.dto.HealthIndicatorDto;
import it.gdhi.model.Country;
import it.gdhi.model.CountryHealthIndicator;
import it.gdhi.model.CountrySummary;
import it.gdhi.model.id.CountryHealthIndicatorId;
import it.gdhi.model.id.CountrySummaryId;
import it.gdhi.repository.ICountryHealthIndicatorRepository;
import it.gdhi.repository.ICountryRepository;
import it.gdhi.repository.ICountryResourceLinkRepository;
import it.gdhi.repository.ICountrySummaryRepository;
import it.gdhi.utils.FormStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static it.gdhi.utils.FormStatus.*;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Service
public class CountryHealthDataService {

    @Autowired
    private ICountryHealthIndicatorRepository iCountryHealthIndicatorRepository;

    @Autowired
    private ICountryRepository iCountryRepository;

    @Autowired
    private MailerService mailerService;

    @Autowired
    private ICountryResourceLinkRepository iCountryResourceLinkRepository;

    @Autowired
    private ICountrySummaryRepository iCountrySummaryRepository;

    @Transactional
    public void save(GdhiQuestionnaire gdhiQuestionnaire, boolean isSubmit) {
        String currentStatus = iCountrySummaryRepository.getCountrySummaryStatus(gdhiQuestionnaire.getCountryId());
        String nextStatus = getNextStatus(currentStatus, isSubmit);
        if (!nextStatus.equals(currentStatus)) {
            removeEntriesWithStatus(gdhiQuestionnaire.getCountryId(), currentStatus);
        }
        saveCountryContactInfo(gdhiQuestionnaire.getCountryId(),
                nextStatus, gdhiQuestionnaire.getCountrySummary());
        saveHealthIndicators(gdhiQuestionnaire.getCountryId(),
                nextStatus, gdhiQuestionnaire.getHealthIndicators());
        if (isSubmit) {
            sendMail(gdhiQuestionnaire.getDataFeederName(), gdhiQuestionnaire.getDataFeederRole(),
                    gdhiQuestionnaire.getContactEmail(), gdhiQuestionnaire.getCountryId());
        }
    }

    private void removeEntriesWithStatus(String countryId, String currentStatus) {
        if (currentStatus.equals(FormStatus.DRAFT.name())) {
            iCountryHealthIndicatorRepository.removeHealthIndicators(countryId, currentStatus);
        }
        iCountryResourceLinkRepository.deleteResources(countryId, currentStatus);
        iCountrySummaryRepository.removeCountrySummary(countryId, currentStatus);
    }


    public void sendMail(String feederName, String feederRole, String contactEmail, String countryId) {
        Country country = iCountryRepository.find(countryId);
        mailerService.send(country, feederName, feederRole, contactEmail);

    }

    private void saveCountryContactInfo(String countryId, String status,
                                        CountrySummaryDto countrySummaryDetailDto) {
        CountrySummary countrySummary = new CountrySummary(new CountrySummaryId(countryId, status),
                countrySummaryDetailDto);
        iCountryResourceLinkRepository.deleteResources(countryId, status);
        iCountrySummaryRepository.save(countrySummary);
    }

    private String getNextStatus(String currentStatus, boolean isSubmit) {
        if (isSubmit)
            return REVIEW_PENDING.name();
        if (currentStatus.equals(NEW.name()) || currentStatus.equals(FormStatus.DRAFT.name())) {
            return FormStatus.DRAFT.name();
        }
        return currentStatus;
    }

    private void saveHealthIndicators(String countryId, String status,
                                      List<HealthIndicatorDto> healthIndicatorDto) {
        List<CountryHealthIndicator> countryHealthIndicators = transformToHealthIndicator(countryId, status,
                healthIndicatorDto);
        if (countryHealthIndicators != null) {
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

    public CountryUrlGenerationStatusDto saveNewCountrySummary(UUID countryUUID) throws Exception {
        String countryId = iCountryRepository.findByUUID(countryUUID).getId();

        CountryUrlGenerationStatusDto statusDto;

        String currentStatus = getStatusOfCountrySummary(countryId);

        if (isNull(currentStatus) || currentStatus.equalsIgnoreCase(PUBLISHED.toString())) {
            CountrySummary countrySummary = new CountrySummary(new CountrySummaryId(countryId, NEW.toString()),
                    new CountrySummaryDto());
            iCountrySummaryRepository.save(countrySummary);
            statusDto = new CountryUrlGenerationStatusDto(countryId, true, isNull(currentStatus) ? null :
                    FormStatus.valueOf(currentStatus));
        } else {
            statusDto = new CountryUrlGenerationStatusDto(countryId, false, FormStatus.valueOf(currentStatus));
        }
        return statusDto;
    }

    private String getStatusOfCountrySummary(String countryId) {
        String currentStatus = null;
        List<String> countrySummaryStatuses = iCountrySummaryRepository.getAllStatus(countryId);
        if (!countrySummaryStatuses.isEmpty()) {
            currentStatus = countrySummaryStatuses.size() > 1 ?
                    countrySummaryStatuses.stream()
                            .filter(el -> !el.equalsIgnoreCase(PUBLISHED.toString())).findFirst().get() :
                    countrySummaryStatuses.get(0);
        }
        return currentStatus;
    }

}