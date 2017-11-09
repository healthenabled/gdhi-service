package it.gdhi.service;

import it.gdhi.dto.CategoryIndicatorDto;
import it.gdhi.model.Category;
import it.gdhi.model.CategoryIndicator;
import it.gdhi.model.CategoryIndicatorId;
import it.gdhi.model.Indicator;
import it.gdhi.repository.ICategoryIndicatorMappingRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryIndicatorServiceTest {

    @InjectMocks
    private CategoryIndicatorService categoryIndicatorService;
    @Mock
    private ICategoryIndicatorMappingRepository iCategoryIndicatorMappingRepository;

    @Test
    public void shouldGetTransformedCategoryDtoInSortedByCategoryIdList() {

        List<CategoryIndicator> categoryIndicators;
        CategoryIndicatorId categoryIndicatorId1 = new CategoryIndicatorId(1, 1);
        Category category1 = new Category(1, "Cat 1");
        Indicator indicator1 = new Indicator(1, "Ind 1", "Ind Def 1");
        CategoryIndicator categoryIndicator1 = new CategoryIndicator(categoryIndicatorId1, category1, indicator1);

        CategoryIndicatorId categoryIndicatorId2 = new CategoryIndicatorId(4, 1);
        Category category2 = new Category(4, "Cat 4");
        Indicator indicator2 = new Indicator(1, "Ind 2", "Ind Def 2");
        CategoryIndicator categoryIndicator2 = new CategoryIndicator(categoryIndicatorId2, category2, indicator2);
        categoryIndicators = asList(categoryIndicator2, categoryIndicator1);
        when(iCategoryIndicatorMappingRepository.findAll()).thenReturn(categoryIndicators);

        List<CategoryIndicatorDto> categoryIndicatorMapping = categoryIndicatorService.getCategoryIndicatorMapping();
        assertThat(categoryIndicatorMapping.size(), is(2));
        assertThat(categoryIndicatorMapping.get(0).getCategoryId(), is(1));
        assertThat(categoryIndicatorMapping.get(0).getCategoryName(), is("Cat 1"));
        assertThat(categoryIndicatorMapping.get(0).getIndicators().get(0).getIndicatorId(), is(1));
        assertThat(categoryIndicatorMapping.get(0).getIndicators().get(0).getIndicatorName(), is("Ind 1"));
        assertThat(categoryIndicatorMapping.get(0).getIndicators().get(0).getIndicatorDefinition(), is("Ind Def 1"));
        assertThat(categoryIndicatorMapping.get(1).getCategoryId(), is(4));
        assertThat(categoryIndicatorMapping.get(1).getCategoryName(), is("Cat 4"));
        assertThat(categoryIndicatorMapping.get(1).getIndicators().get(0).getIndicatorId(), is(1));
        assertThat(categoryIndicatorMapping.get(1).getIndicators().get(0).getIndicatorName(), is("Ind 2"));
        assertThat(categoryIndicatorMapping.get(1).getIndicators().get(0).getIndicatorDefinition(), is("Ind Def 2"));

    }


    @Test
    public void shouldGetTransformedCategoryDtoInSortedByIndicatorIdListForEachCategory() {

        List<CategoryIndicator> categoryIndicators;
        CategoryIndicatorId categoryIndicatorId1 = new CategoryIndicatorId(1, 1);
        Category category1 = new Category(1, "Cat 1");
        Indicator indicator2 = new Indicator(5, "Ind 5", "Ind Def 5");
        Indicator indicator1 = new Indicator(1, "Ind 1", "Ind Def 1");
        CategoryIndicator categoryIndicator1 = new CategoryIndicator(categoryIndicatorId1, category1, indicator1);
        CategoryIndicator categoryIndicator2 = new CategoryIndicator(categoryIndicatorId1, category1, indicator2);

        CategoryIndicatorId categoryIndicatorId2 = new CategoryIndicatorId(4, 1);
        Category category2 = new Category(4, "Cat 4");
        Indicator indicator4 = new Indicator(8, "Ind 8", "Ind Def 8");
        Indicator indicator3 = new Indicator(2, "Ind 2", "Ind Def 2");
        CategoryIndicator categoryIndicator3 = new CategoryIndicator(categoryIndicatorId2, category2, indicator3);
        CategoryIndicator categoryIndicator4 = new CategoryIndicator(categoryIndicatorId2, category2, indicator4);
        categoryIndicators = asList(categoryIndicator4, categoryIndicator3, categoryIndicator2, categoryIndicator1);
        when(iCategoryIndicatorMappingRepository.findAll()).thenReturn(categoryIndicators);

        List<CategoryIndicatorDto> categoryIndicatorMapping = categoryIndicatorService.getCategoryIndicatorMapping();
        assertThat(categoryIndicatorMapping.size(), is(2));
        assertThat(categoryIndicatorMapping.get(0).getCategoryId(), is(1));
        assertThat(categoryIndicatorMapping.get(0).getCategoryName(), is("Cat 1"));
        assertThat(categoryIndicatorMapping.get(0).getIndicators().get(0).getIndicatorId(), is(1));
        assertThat(categoryIndicatorMapping.get(0).getIndicators().get(0).getIndicatorName(), is("Ind 1"));
        assertThat(categoryIndicatorMapping.get(0).getIndicators().get(0).getIndicatorDefinition(), is("Ind Def 1"));

        assertThat(categoryIndicatorMapping.get(0).getIndicators().get(1).getIndicatorId(), is(5));
        assertThat(categoryIndicatorMapping.get(0).getIndicators().get(1).getIndicatorName(), is("Ind 5"));
        assertThat(categoryIndicatorMapping.get(0).getIndicators().get(1).getIndicatorDefinition(), is("Ind Def 5"));

        assertThat(categoryIndicatorMapping.get(1).getCategoryId(), is(4));
        assertThat(categoryIndicatorMapping.get(1).getCategoryName(), is("Cat 4"));
        assertThat(categoryIndicatorMapping.get(1).getIndicators().get(0).getIndicatorId(), is(2));
        assertThat(categoryIndicatorMapping.get(1).getIndicators().get(0).getIndicatorName(), is("Ind 2"));
        assertThat(categoryIndicatorMapping.get(1).getIndicators().get(0).getIndicatorDefinition(), is("Ind Def 2"));

        assertThat(categoryIndicatorMapping.get(1).getIndicators().get(1).getIndicatorId(), is(8));
        assertThat(categoryIndicatorMapping.get(1).getIndicators().get(1).getIndicatorName(), is("Ind 8"));
        assertThat(categoryIndicatorMapping.get(1).getIndicators().get(1).getIndicatorDefinition(), is("Ind Def 8"));

    }

    @Test
    public void shouldHandleNullIndicatorsForACategory() {

        List<CategoryIndicator> categoryIndicators;
        CategoryIndicatorId categoryIndicatorId1 = new CategoryIndicatorId(1, 1);
        Category category1 = new Category(1, "Cat 1");
        CategoryIndicator categoryIndicator1 = new CategoryIndicator(categoryIndicatorId1, category1, null);
        categoryIndicators = asList(categoryIndicator1);
        when(iCategoryIndicatorMappingRepository.findAll()).thenReturn(categoryIndicators);

        List<CategoryIndicatorDto> categoryIndicatorMapping = categoryIndicatorService.getCategoryIndicatorMapping();
        assertThat(categoryIndicatorMapping.size(), is(1));
        assertThat(categoryIndicatorMapping.get(0).getCategoryId(), is(1));
        assertThat(categoryIndicatorMapping.get(0).getCategoryName(), is("Cat 1"));
        assertTrue(categoryIndicatorMapping.get(0).getIndicators().isEmpty());
    }
}