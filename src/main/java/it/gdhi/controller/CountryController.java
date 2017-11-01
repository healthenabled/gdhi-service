package it.gdhi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import it.gdhi.model.Country;
import it.gdhi.model.DevelopmentIndicator;
import it.gdhi.repository.IDevelopmentIndicatorRepository;
import it.gdhi.service.CountryService;
import it.gdhi.view.DevelopmentIndicatorView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {

    @Autowired
    CountryService countryDetailService;

    @Autowired
    IDevelopmentIndicatorRepository iDevelopmentIndicatorRepository;

    @RequestMapping("countrylist")
    public Country listCountries() {
        return countryDetailService.fetchCountry();
    }

    @RequestMapping("/countries/{id}/development_indicator.json")
    @JsonView(DevelopmentIndicatorView.class)
    public DevelopmentIndicator getDevelopmentIndicatorForGivenCountryCode(@PathVariable("id") String id) {
        return iDevelopmentIndicatorRepository.findByCountryId(id).orElse(null);
    }

}
