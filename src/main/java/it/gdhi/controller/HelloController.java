package it.gdhi.controller;

import it.gdhi.service.CountryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    CountryDetailService countryDetailService;

    @RequestMapping("/hello")
    public String index() {
        countryDetailService.create();
        return "Greetings from Spring Boot!";
    }
}
