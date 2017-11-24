package it.gdhi.model;

import it.gdhi.dto.CountrySummaryDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Table(schema ="validated_config", name="countries_summary")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CountrySummary {

    @Id
    @Column(name="country_id")
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    private List<CountryResourceLink> countryResourceLinks;

    public CountrySummary(String countryId, CountrySummaryDetailDto countrySummaryDetailDto) {
        this.countryId = countryId;
        this.summary = countrySummaryDetailDto.getSummary();
        this.contactName = countrySummaryDetailDto.getContactName();
        this.contactDesignation = countrySummaryDetailDto.getContactDesignation();
        this.contactOrganization = countrySummaryDetailDto.getContactOrganization();
        this.contactEmail = countrySummaryDetailDto.getContactEmail();
        this.dataFeederName = countrySummaryDetailDto.getDataFeederName();
        this.dataFeederRole = countrySummaryDetailDto.getDataFeederRole();
        this.dataFeederEmail = countrySummaryDetailDto.getDataFeederEmail();
        this.dataCollectorName = countrySummaryDetailDto.getDataCollectorName();
        this.dataCollectorRole = countrySummaryDetailDto.getDataCollectorRole();
        this.dataCollectorEmail = countrySummaryDetailDto.getDataCollectorEmail();
        this.collectedDate = countrySummaryDetailDto.getCollectedDate();
        this.countryResourceLinks = transformToResourceLinks(countryId, countrySummaryDetailDto);
    }

    private List<CountryResourceLink> transformToResourceLinks(String countryId,
                                                               CountrySummaryDetailDto countrySummaryDetailDto) {
        List<String> resourceLinks = countrySummaryDetailDto.getResourceLinks();
        return ObjectUtils.isEmpty(resourceLinks) ? null : resourceLinks.stream().map(link ->
                new CountryResourceLink(new CountryResourceLinkId(countryId, link))).collect(toList());
    }

}