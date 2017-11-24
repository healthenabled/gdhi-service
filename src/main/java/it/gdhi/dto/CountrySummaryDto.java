package it.gdhi.dto;

import it.gdhi.model.CountrySummary;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@NoArgsConstructor
public class CountrySummaryDto {

    private String countryId;
    private String summary;
    private String contactName;
    private String contactDesignation;
    private String contactOrganization;
    private String contactEmail;
    private String dataFeederName;
    private String dataFeederRole;
    private String dataFeederEmail;
    private String dataCollectorName;
    private String dataCollectorRole;
    private String dataCollectorEmail;
    private Date collectedDate;

    private List<String> resources;


    public CountrySummaryDto(CountrySummary countrySummary) {
        this.countryId = countrySummary.getCountryId();
        this.summary = countrySummary.getSummary();
        this.contactName = countrySummary.getContactName();
        this.contactDesignation = countrySummary.getContactDesignation();
        this.contactOrganization = countrySummary.getContactOrganization();
        this.contactEmail = countrySummary.getContactEmail();
        this.dataFeederName = countrySummary.getDataFeederName();
        this.dataFeederRole = countrySummary.getDataFeederRole();
        this.dataFeederEmail = countrySummary.getDataFeederEmail();
        this.dataCollectorName = countrySummary.getDataCollectorName();
        this.dataCollectorRole = countrySummary.getDataCollectorRole();
        this.dataCollectorEmail = countrySummary.getDataCollectorEmail();
        this.collectedDate = countrySummary.getCollectedDate();
        this.resources = countrySummary.getCountryResourceLinks()
                .stream()
                .map(countryResourceLink -> countryResourceLink.getLink())
                .collect(toList());
    }
}
