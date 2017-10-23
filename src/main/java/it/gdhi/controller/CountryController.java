package it.gdhi.controller;

import it.gdhi.model.CountryDetail;
import it.gdhi.service.CountryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {

    @Autowired
    CountryDetailService countryDetailService;

    @RequestMapping("/countryList")
    public CountryDetail listCountries() {
        return countryDetailService.insert();
    }
}
