package it.gdhi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.gdhi.model.CountrySummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @JsonFormat(pattern = "dd-MM-yyyy")
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
        this.resources = transformResourceLinks(countrySummary);
    }

    private List<String> transformResourceLinks(CountrySummary countrySummary) {
        return Optional.ofNullable(countrySummary.getCountryResourceLinks())
                .map(list -> list.stream()
                        .map(countryResourceLink -> countryResourceLink.getLink())
                        .collect(toList()))
                .orElse(null);

    }
}
