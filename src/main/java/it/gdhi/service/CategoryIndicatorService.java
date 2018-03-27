package it.gdhi.service;

import it.gdhi.dto.CategoryIndicatorDto;
import it.gdhi.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
public class CategoryIndicatorService {

    @Autowired
    private ICategoryRepository iCategoryRepository;

    public List<CategoryIndicatorDto> getHealthIndicatorOptions() {
        return iCategoryRepository.findAll().stream().map(CategoryIndicatorDto::new)
                                  .collect(toList());
    }
}