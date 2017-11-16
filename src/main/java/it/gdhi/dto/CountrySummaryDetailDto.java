package it.gdhi.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class CountrySummaryDetailDto {
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
}
