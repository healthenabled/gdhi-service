package it.gdhi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import it.gdhi.model.CategoryIndicator;
import it.gdhi.repository.ICategoryIndicatorMappingRepository;
import it.gdhi.view.CategoryIndicatorView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MetaDataController {

    @Autowired
    ICategoryIndicatorMappingRepository iCategoryIndicatorMappingRepository;

    @RequestMapping("/categorical_indicators")
    @JsonView(CategoryIndicatorView.class)
    public List<CategoryIndicator> getCategoryIndicatorMapping() {
        return iCategoryIndicatorMappingRepository.findAll();
    }

}
