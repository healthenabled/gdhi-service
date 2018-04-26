package it.gdhi.controller;

import it.gdhi.dto.CountriesHealthScoreDto;
import it.gdhi.dto.GlobalHealthScoreDto;
import it.gdhi.service.CountryHealthIndicatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HealthIndicatorController {

    @Autowired
    private CountryHealthIndicatorService countryHealthIndicatorService;

    @RequestMapping("/global_health_indicators")
    public GlobalHealthScoreDto getGlobalHealthIndicator(
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "phase", required = false) Integer score) {
        return countryHealthIndicatorService.getGlobalHealthIndicator(categoryId, score);
    }

    @RequestMapping("/countries_health_indicator_scores")
    public CountriesHealthScoreDto getCountriesHealthIndicatorScores(
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "phase", required = false) Integer score) {
        return countryHealthIndicatorService.fetchCountriesHealthScores(categoryId, score);
    }


}
