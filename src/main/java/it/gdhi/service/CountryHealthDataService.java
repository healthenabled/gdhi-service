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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static it.gdhi.utils.Constants.*;

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
    public void save(GdhiQuestionnaire gdhiQuestionnaire, boolean isSubmit) {
        String currentStatus = iCountrySummaryRepository.getCountrySummaryStatus(gdhiQuestionnaire.getCountryId());
        String newStatus = getNextStatus(currentStatus);
        saveCountryContactInfo(gdhiQuestionnaire.getCountryId(),
                newStatus, gdhiQuestionnaire.getCountrySummary());
        saveHealthIndicators(gdhiQuestionnaire.getCountryId(),
                newStatus, gdhiQuestionnaire.getHealthIndicators());
        if (isSubmit) {
            sendMail(gdhiQuestionnaire.getDataFeederName(), gdhiQuestionnaire.getDataFeederRole(),
                    gdhiQuestionnaire.getContactEmail(), gdhiQuestionnaire.getCountryId());
        }
    }


    public void sendMail(String feederName, String feederRole, String contactEmail, String countryId) {
        Country country = iCountryRepository.find(countryId);
        mailerService.send(country, feederName, feederRole, contactEmail);

    }

    private void saveCountryContactInfo(String countryId, String status ,
                                        CountrySummaryDto countrySummaryDetailDto) {
        CountrySummary countrySummary = new CountrySummary(new CountrySummaryId(countryId, status),
                countrySummaryDetailDto);
        iCountryResourceLinkRepository.deleteResources(countryId);
        iCountrySummaryRepository.save(countrySummary);
    }

    private String getNextStatus(String currentStatus) {
        if (currentStatus != null) {
            if (currentStatus == "NEW" || currentStatus == "DRAFT") {
                return "DRAFT";
            }
            return currentStatus;
        }
        return "DRAFT";
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

    public CountryUrlGenerationStatusDto saveCountrySummaryAsNew
            (String countryId) throws Exception{

        List<String> list = iCountrySummaryRepository.getAllStatus(countryId);

        Optional<String> statusOptional = null;
        String currentStatus =  null ;

        if(list != null && !list.isEmpty()) {
            statusOptional = list.size() > 1 ?
                    list.stream()
                            .filter(el  -> !el.equalsIgnoreCase(PUBLISHED_STATUS)).findFirst() :
                    Optional.ofNullable(list.get(0));
        }

        if(statusOptional!=null && statusOptional.isPresent()){
            currentStatus =  statusOptional.get();
        }

        if(currentStatus==COUNTRY_DATA_NOT_PRESENT){
            CountrySummary countrySummary = new CountrySummary(new CountrySummaryId(countryId, NEW_STATUS),
                    new CountrySummaryDto());
            iCountrySummaryRepository.save(countrySummary);
            CountryUrlGenerationStatusDto dto = new CountryUrlGenerationStatusDto(
                    countryId,URL_GENERATED_SUCCESSFULLY_MESSAGE);
            return dto;
        }
        else if (currentStatus.equalsIgnoreCase(NEW_STATUS) || currentStatus.equalsIgnoreCase(DRAFT_STATUS)){
            CountryUrlGenerationStatusDto dto = new CountryUrlGenerationStatusDto(
                    countryId,AWAITING_SUBMISSION_MESSAGE);
            return dto;
        }
        else if (currentStatus.equalsIgnoreCase(REVIEW_PENDING_STATUS)){
            CountryUrlGenerationStatusDto dto = new CountryUrlGenerationStatusDto(countryId,PENDING_REVIEW_MESSAGE);
            return dto;
        }
        else if (currentStatus.equalsIgnoreCase(PUBLISHED_STATUS)){
            CountryUrlGenerationStatusDto dto = new CountryUrlGenerationStatusDto(countryId,ALREADY_PUBLISHED_MESSAGE);
            return dto;
        }
        else{
            throw new Exception();
        }
    }

}