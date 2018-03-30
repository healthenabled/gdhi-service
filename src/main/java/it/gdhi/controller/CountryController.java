package it.gdhi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import it.gdhi.dto.*;
import it.gdhi.model.Country;
import it.gdhi.model.DevelopmentIndicator;
import it.gdhi.service.CountryHealthDataService;
import it.gdhi.service.CountryService;
import it.gdhi.service.DevelopmentIndicatorService;
import it.gdhi.service.CountryHealthIndicatorService;
import it.gdhi.view.DevelopmentIndicatorView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class CountryController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private CountryHealthDataService countryHealthDataService;

    @Autowired
    private CountryHealthIndicatorService countryHealthIndicatorService;

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
        return countryHealthIndicatorService.fetchCountryHealthScore(countryId);
    }

    @RequestMapping("/countries_health_indicator_scores")
    public CountriesHealthScoreDto getCountriesHealthIndicatorScores(
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "phase", required = false) Integer score) {
        return countryHealthIndicatorService.fetchCountriesHealthScores(categoryId, score);
    }

    @RequestMapping("/global_health_indicators")
    public GlobalHealthScoreDto getGlobalHealthIndicator(
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "phase", required = false) Integer score) {
        return countryHealthIndicatorService.getGlobalHealthIndicator(categoryId, score);
    }

    @RequestMapping("/countries/{id}/country_summary")
    public CountrySummaryDto fetchCountrySummary(@PathVariable("id") String countryId) {
        return countryService.fetchCountrySummary(countryId);
    }

    @RequestMapping(value = "/countries/save", method = RequestMethod.POST)
    public void saveHealthIndicatorsFor(@RequestBody GdhiQuestionnaire gdhiQuestionnaire) {
        countryHealthDataService.save(gdhiQuestionnaire);
    }

    @RequestMapping(value = "/countries/submit", method = RequestMethod.POST)
    public void submitHealthIndicatorsFor(@RequestBody GdhiQuestionnaire gdhiQuestionnaire) {
        countryHealthDataService.save(gdhiQuestionnaire);
    }

    @RequestMapping(value = "/countries/{id}", method = RequestMethod.GET)
    public GdhiQuestionnaire getCountryDetails(@PathVariable("id") String countryId) {
        return countryService.getDetails(countryId);
    }

    @RequestMapping(value = "/export_global_data", method = RequestMethod.GET)
    public void exportGlobalData(HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        log.info("Entered export global data end point");
        countryHealthIndicatorService.createGlobalHealthIndicatorInExcel(request, response);
    }

    @RequestMapping(value = "/export_country_data/{id}", method = RequestMethod.GET)
    public void exportCountryDetails(HttpServletRequest request,
                                HttpServletResponse response, @PathVariable("id") String countryId) throws IOException {
        countryHealthIndicatorService.createHealthIndicatorInExcelFor(countryId, request, response);
    }

    @RequestMapping(value = "/country_info/{uuid}", method = RequestMethod.GET)
        public Country getCountryDetails(@PathVariable("uuid") UUID countryUUID) {
        return countryService.fetchCountryFromUUID(countryUUID);
    }

    @RequestMapping(value = "/country/url_gen_status/{id}", method = RequestMethod.POST)
    public String saveUrlGenerationStatus(@PathVariable("id") String countryId) {
        return countryHealthDataService.saveCountrySummaryAsNewStatusWhileGeneratingURL(countryId);
    }
}
