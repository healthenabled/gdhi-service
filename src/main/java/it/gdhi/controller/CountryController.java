package it.gdhi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import it.gdhi.dto.*;
import it.gdhi.model.Country;
import it.gdhi.model.DevelopmentIndicator;
import it.gdhi.service.CountryService;
import it.gdhi.service.DevelopmentIndicatorService;
import it.gdhi.service.HealthIndicatorService;
import it.gdhi.view.DevelopmentIndicatorView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
public class CountryController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private HealthIndicatorService healthIndicatorService;

    @Autowired
    private DevelopmentIndicatorService developmentIndicatorService;

    @RequestMapping("/countries")
    public List<Country> getCountries() {
        return countryService.fetchCountries();
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

    //TODO this is a duplicate of global_health_indicators. Make the app use global_health_indicators instead of this
    @RequestMapping("/countries_health_indicator_scores")
    public AllCountriesHealthScoreDto getAllCountriesHealthIndicatorScores() {
        return healthIndicatorService.fetchHealthScores();
    }

    @RequestMapping("/global_health_indicators")
    public GlobalHealthScoreDto getGlobalHealthIndicator() {
        return healthIndicatorService.getGlobalHealthIndicator();
    }

    @RequestMapping("/countries/{id}/country_summary")
    public CountrySummaryDto fetchCountrySummary(@PathVariable("id") String countryId) {
        return countryService.fetchCountrySummary(countryId);
    }

    @RequestMapping(value = "/countries", method = RequestMethod.POST)
    public void saveHealthIndicatorsFor(@RequestBody GdhiQuestionnaire gdhiQuestionnaire) {
        countryService.save(gdhiQuestionnaire);
    }

    @RequestMapping(value = "/countries/{id}", method = RequestMethod.GET)
    public GdhiQuestionnaire getCountryDetails(@PathVariable("id") String countryId) {
        return countryService.getDetails(countryId);
    }

    @RequestMapping(value = "/export_global_data", method = RequestMethod.GET)
    public void exportGlobalData(HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        log.info("Entered export global data end point");
        healthIndicatorService.createGlobalHealthIndicatorInExcel(request, response);
    }

    @RequestMapping(value = "/export_country_data/{id}", method = RequestMethod.GET)
    public void exportCountryDetails(HttpServletRequest request,
                                HttpServletResponse response, @PathVariable("id") String countryId) throws IOException {
        healthIndicatorService.createHealthIndicatorInExcelFor(countryId, request, response);
    }
}
