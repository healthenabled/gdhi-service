package it.gdhi.controller;

import it.gdhi.model.Country;
import it.gdhi.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {

    @Autowired
    CountryService countryDetailService;

    @RequestMapping("countrylist")
    public Country listCountries() {
        return countryDetailService.insert();
    }
}
