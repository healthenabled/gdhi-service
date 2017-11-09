package it.gdhi.service;

import it.gdhi.dto.CategoryIndicatorDto;
import it.gdhi.model.CategoryIndicator;
import it.gdhi.repository.ICategoryIndicatorMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;


@Service
public class CategoryIndicatorService {

    @Autowired
    private ICategoryIndicatorMappingRepository iCategoryIndicatorMappingRepository;

    @Transactional
    public List<CategoryIndicatorDto> getCategoryIndicatorMapping() {
        List<CategoryIndicator> categoryIndicators = iCategoryIndicatorMappingRepository.findAll();
        List<CategoryIndicator> sorted = categoryIndicators.stream().sorted(comparing(CategoryIndicator::getCategoryId))
                .collect(toList());
        Map<Integer, List<CategoryIndicator>> categoryIndicatorMap = sorted.stream()
                .collect(groupingBy(CategoryIndicator::getCategoryId));
        return categoryIndicatorMap.entrySet().stream().map(categoryIndicator ->
                new CategoryIndicatorDto(categoryIndicator.getKey(),categoryIndicator.getValue())).collect(toList());
    }

}