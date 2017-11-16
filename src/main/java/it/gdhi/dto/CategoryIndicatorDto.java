package it.gdhi.dto;

import it.gdhi.model.Category;
import it.gdhi.model.CategoryIndicator;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.Collections.singleton;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Getter
@NoArgsConstructor
public class CategoryIndicatorDto {

    private Integer categoryId;

    private String categoryName;

    private List<IndicatorDto> indicators;

    public CategoryIndicatorDto(Integer categoryId, List<CategoryIndicator> categoryIndicators) {
        this.categoryId = categoryId;
        this.categoryName = getCategoryName(categoryIndicators);
        this.indicators = transformToIndicators(categoryIndicators);
    }

    public CategoryIndicatorDto(Category category) {
        this.categoryId = category.getId();
        this.categoryName = category.getName();
        this.indicators = transformToIndicatorDto(category);
    }

    private List<IndicatorDto> transformToIndicatorDto(Category category) {
        return category.getIndicators().stream().map(indicator -> new IndicatorDto(indicator))
                .collect(toList());
    }

    private List<IndicatorDto> transformToIndicators(List<CategoryIndicator> value) {
        List<IndicatorDto> indicators = value.stream().map(each -> each.getIndicator() != null ?
        new IndicatorDto(each.getIndicatorId(), each.getIndicatorName(), each.getIndicatorDefinition()) : null)
                .collect(toList());
        indicators.removeAll(singleton(null));
        return indicators.stream().sorted(comparing(IndicatorDto::getIndicatorId)).collect(toList());
    }

    private String getCategoryName(List<CategoryIndicator> categoryIndicators) {
        return isCategoryIndicatorsPresent(categoryIndicators) ? categoryIndicators.get(0).getCategoryName()
                                                               : null;
    }

    private boolean isCategoryIndicatorsPresent(List<CategoryIndicator> categoryIndicators) {
        return categoryIndicators != null && categoryIndicators.size() != 0;
    }

}
