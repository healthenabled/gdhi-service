package it.gdhi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
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
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date collectedDate;
    private List<String> resourceLinks;
}
