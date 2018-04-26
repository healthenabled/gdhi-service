package it.gdhi.service;

import it.gdhi.dto.*;
import it.gdhi.model.Country;
import it.gdhi.model.CountryHealthIndicator;
import it.gdhi.model.CountryHealthIndicators;
import it.gdhi.model.CountrySummary;
import it.gdhi.model.Score;
import it.gdhi.model.CountryPhase;
import it.gdhi.model.id.CountryHealthIndicatorId;
import it.gdhi.model.id.CountrySummaryId;
import it.gdhi.repository.ICountryHealthIndicatorRepository;
import it.gdhi.repository.ICountryRepository;
import it.gdhi.repository.ICountryResourceLinkRepository;
import it.gdhi.repository.ICountrySummaryRepository;
import it.gdhi.repository.ICountryPhaseRepository;
import it.gdhi.utils.FormStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.*;

import static it.gdhi.utils.FormStatus.*;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.groupingBy;
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

    @Autowired
    private ICountryPhaseRepository iCountryPhaseRepository;

    @Autowired
    private BenchMarkService benchmarkService;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void save(GdhiQuestionnaire gdhiQuestionnaire, String nextStatus) {
        String currentStatus = iCountrySummaryRepository.getCountrySummaryStatus(gdhiQuestionnaire.getCountryId());
        if (!nextStatus.equals(currentStatus)) {
            removeEntriesWithStatus(gdhiQuestionnaire.getCountryId(), currentStatus);
        }
        saveCountryContactInfo(gdhiQuestionnaire.getCountryId(),
                nextStatus, gdhiQuestionnaire.getCountrySummary());
        saveHealthIndicators(gdhiQuestionnaire.getCountryId(),
                nextStatus, gdhiQuestionnaire.getHealthIndicators());
    }

    private void calculateAndSaveCountryPhase(String countryId, String status) {
        CountryHealthIndicators countryHealthIndicators = new CountryHealthIndicators(iCountryHealthIndicatorRepository
                .findHealthIndicatorsByCountryIdAndStatus(countryId, status));
        Double overallScore = countryHealthIndicators.getOverallScore();
        Integer countryPhase = new Score(overallScore).convertToPhase();
        iCountryPhaseRepository.save(new CountryPhase(countryId, countryPhase));
    }

    private void removeEntriesWithStatus(String countryId, String currentStatus) {
        if (!currentStatus.equals(NEW.name())) {
            iCountryHealthIndicatorRepository.removeHealthIndicatorsBy(countryId, currentStatus);
        }
        iCountryResourceLinkRepository.deleteResources(countryId, currentStatus);
        iCountrySummaryRepository.removeCountrySummary(countryId, currentStatus);
    }

    private void sendMail(String feederName, String feederRole, String contactEmail, String countryId) {
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

    private void saveHealthIndicators(String countryId, String status,
                                      List<HealthIndicatorDto> healthIndicatorDto) {
        List<CountryHealthIndicator> countryHealthIndicators = transformToHealthIndicator(countryId, status,
                healthIndicatorDto);
        if (countryHealthIndicators != null) {
            countryHealthIndicators.forEach(health -> {
                CountryHealthIndicator countryHealthIndicator = iCountryHealthIndicatorRepository.save(health);
                entityManager.flush();
                entityManager.refresh(countryHealthIndicator);
            });
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

    @Transactional
    public void publish(GdhiQuestionnaire gdhiQuestionnaire) {
        save(gdhiQuestionnaire, PUBLISHED.name());
        calculateAndSaveCountryPhase(gdhiQuestionnaire.getCountryId(), PUBLISHED.name());
    }

    @Transactional
    public void submit(GdhiQuestionnaire gdhiQuestionnaire) {
        save(gdhiQuestionnaire, REVIEW_PENDING.name());
        sendMail(gdhiQuestionnaire.getDataFeederName(), gdhiQuestionnaire.getDataFeederRole(),
                gdhiQuestionnaire.getContactEmail(), gdhiQuestionnaire.getCountryId());
    }

    @Transactional
    public void saveCorrection(GdhiQuestionnaire gdhiQuestionnaire) {
        save(gdhiQuestionnaire, REVIEW_PENDING.name());
    }

    @Transactional
    public void deleteCountryData(UUID countryUUID) {
        String countryId = iCountryRepository.findByUUID(countryUUID).getId();
        iCountryHealthIndicatorRepository.removeHealthIndicatorsBy(countryId, REVIEW_PENDING.name());
        iCountryResourceLinkRepository.deleteResources(countryId, REVIEW_PENDING.name());
        iCountrySummaryRepository.removeCountrySummary(countryId, REVIEW_PENDING.name());
    }

    public Map<String, List<AdminViewFormDetailsDto>> getAdminViewFormDetails() {
        List<CountrySummary> countrySummaries = iCountrySummaryRepository.getAll();

        List<AdminViewFormDetailsDto> adminViewFormDetailsDtoList = countrySummaries
                .stream()
                .map(dto -> new AdminViewFormDetailsDto(
                        dto.getCountry().getName(),
                        dto.getCountry().getUniqueId(),
                        dto.getCountrySummaryId().getStatus(),
                        dto.getContactName(),
                        dto.getContactEmail()
                ))
                .collect(toList());


        return adminViewFormDetailsDtoList.stream()
                .collect(groupingBy(AdminViewFormDetailsDto::getStatus));
    }

    public Map<Integer, BenchmarkDto> getBenchmarkDetailsFor(String countryId, String benchmarkType) {
        return benchmarkService.getBenchmarkFor(countryId, benchmarkType);
    }

    public boolean validateRequiredFields(GdhiQuestionnaire gdhiQuestionnaire) {
        return verifyFields(gdhiQuestionnaire.getCountrySummary())
                && verifyDateRange(gdhiQuestionnaire.getCountrySummary().getCollectedDate())
                && verifyResources(gdhiQuestionnaire.getCountrySummary().getResources())
                && verifyIndicators(gdhiQuestionnaire.getHealthIndicators());
    }

    private boolean verifyIndicators(List<HealthIndicatorDto> healthIndicators) {
        return healthIndicators.stream().noneMatch(healthIndicatorDto
                -> StringUtils.isEmpty(healthIndicatorDto.getSupportingText())
        || healthIndicatorDto.getScore() < -1);
    }

    private boolean verifyResources(List<String> resources) {
        return (resources!=null && !resources.isEmpty());
    }

    private boolean verifyFields(CountrySummaryDto countrySummary) {
        return countrySummary.getCollectedDate() != null
                && !StringUtils.isEmpty(countrySummary.getCountryId())
                && !StringUtils.isEmpty(countrySummary.getContactDesignation())
                && !StringUtils.isEmpty(countrySummary.getContactEmail())
                && !StringUtils.isEmpty(countrySummary.getContactName())
                && !StringUtils.isEmpty(countrySummary.getContactOrganization())
                && !StringUtils.isEmpty(countrySummary.getCountryName())
                && !StringUtils.isEmpty(countrySummary.getDataApproverEmail())
                && !StringUtils.isEmpty(countrySummary.getDataApproverName())
                && !StringUtils.isEmpty(countrySummary.getDataApproverRole())
                && !StringUtils.isEmpty(countrySummary.getDataFeederEmail())
                && !StringUtils.isEmpty(countrySummary.getDataFeederName())
                && !StringUtils.isEmpty(countrySummary.getDataFeederRole())
                && !StringUtils.isEmpty(countrySummary.getSummary());
    }

    private boolean verifyDateRange(Date collectedDate) {
        Calendar myCalendar = new GregorianCalendar(2010, 0, 1);
        Date backDate = myCalendar.getTime();
        Date today = new GregorianCalendar().getTime();
        return (collectedDate.equals(today)) || (collectedDate.before(today) && collectedDate.after(backDate));
    }
}