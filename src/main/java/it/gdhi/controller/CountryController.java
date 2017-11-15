package it.gdhi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import it.gdhi.dto.AllCountriesHealthScoreDto;
import it.gdhi.dto.CountryHealthScoreDto;
import it.gdhi.dto.GlobalHealthScoreDto;
import it.gdhi.model.Country;
import it.gdhi.model.DevelopmentIndicator;
import it.gdhi.service.CountryService;
import it.gdhi.service.DevelopmentIndicatorService;
import it.gdhi.service.HealthIndicatorService;
import it.gdhi.view.DevelopmentIndicatorView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountryController {

    @Autowired
    private CountryService countryDetailService;

    @Autowired
    private HealthIndicatorService healthIndicatorService;

    @Autowired
    private DevelopmentIndicatorService developmentIndicatorService;

    @RequestMapping("/countries")
    public List<Country> getCountries() {
        return countryDetailService.fetchCountries();
    }

    @RequestMapping("/countries/{id}/development_indicators")
    @JsonView(DevelopmentIndicatorView.class)
    public DevelopmentIndicator getDevelopmentIndicatorForGivenCountryCode(@PathVariable("id") String countryId) {
        return developmentIndicatorService.fetchCountryDevelopmentScores(countryId);
    }

    @RequestMapping("/countries/{id}/health_indicators")
    public CountryHealthScoreDto getHealthIndicatorForGivenCountryCode(@PathVariable("id") String countryId) {
        return healthIndicatorService.fetchCountryHealthScore(countryId);
    }

    @RequestMapping("/countries_health_indicator_scores")
    public AllCountriesHealthScoreDto getAllCountriesHealthIndicatorScores() {
        return healthIndicatorService.fetchHealthScores();
    }

    @RequestMapping("/global_health_indicators")
    public GlobalHealthScoreDto getGlobalHealthIndicator() {
        return healthIndicatorService.getGlobalHealthIndicator();
    }

}
