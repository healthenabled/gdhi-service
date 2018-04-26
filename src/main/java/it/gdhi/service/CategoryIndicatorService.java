package it.gdhi.service;

import it.gdhi.dto.CategoryIndicatorDto;
import it.gdhi.model.Category;
import it.gdhi.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;


@Service
public class CategoryIndicatorService {

    @Autowired
    private ICategoryRepository iCategoryRepository;

    public List<CategoryIndicatorDto> getHealthIndicatorOptions() {
        return iCategoryRepository.findAll().stream().map(CategoryIndicatorDto::new)
                                  .collect(toList());
    }

    public Integer getHealthIndicatorCount() {
        List<Category> categoryList = iCategoryRepository.findAll();
        AtomicInteger count = new AtomicInteger(0);

        categoryList.stream().forEach(category -> count.addAndGet(category.getIndicators().size()));
        return count.get();
    }
}