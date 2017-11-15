package it.gdhi.dto;

import it.gdhi.model.CountryResourceLink;
import it.gdhi.model.CountrySummary;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private List<String> resources;


    public CountrySummaryDto(CountrySummary countrySummary, List<CountryResourceLink> countryResourceLinks) {
        this.countryId = countrySummary.getCountryId();
        this.summary = countrySummary.getSummary();
        this.contactName = countrySummary.getContactName();
        this.contactDesignation = countrySummary.getContactDesignation();
        this.contactOrganization = countrySummary.getContactOrganization();
        this.resources = countryResourceLinks.stream().map(countryResourceLink -> countryResourceLink.getLink())
                                                      .collect(toList());
    }
}
