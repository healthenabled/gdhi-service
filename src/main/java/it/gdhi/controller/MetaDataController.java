package it.gdhi.controller;

import it.gdhi.dto.CategoryIndicatorDto;
import it.gdhi.service.CategoryIndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MetaDataController {

    @Autowired
    private CategoryIndicatorService categoryIndicatorService;

    @RequestMapping("/health_indicator_options")
    public List<CategoryIndicatorDto> getHealthIndicatorOptions() {
        return categoryIndicatorService.getHealthIndicatorOptions();
    }

}
