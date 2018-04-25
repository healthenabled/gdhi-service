package it.gdhi.controller;

import it.gdhi.dto.CategoryIndicatorDto;
import it.gdhi.dto.PhaseDto;
import it.gdhi.service.CategoryIndicatorService;
import it.gdhi.service.PhaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MetaDataController {

    @Autowired
    private CategoryIndicatorService categoryIndicatorService;

    @Autowired
    private PhaseService phaseService;

    @RequestMapping("/health_indicator_options")
    public List<CategoryIndicatorDto> getHealthIndicatorOptions() {
        return categoryIndicatorService.getHealthIndicatorOptions();
    }

    @RequestMapping(value = "/phases", method = RequestMethod.GET)
    public List<PhaseDto> getPhases() {
        return phaseService.getPhaseOptions();
    }
}
