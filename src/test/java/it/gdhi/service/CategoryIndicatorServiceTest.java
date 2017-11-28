package it.gdhi.service;

import it.gdhi.dto.CategoryIndicatorDto;
import it.gdhi.model.Category;
import it.gdhi.model.Indicator;
import it.gdhi.model.IndicatorScore;
import it.gdhi.repository.ICategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryIndicatorServiceTest {

    @InjectMocks
    private CategoryIndicatorService categoryIndicatorService;
    @Mock
    private ICategoryRepository iCategoryRepository;

    @Test
    public void shouldGetTransformedCategoryDtoInSortedByCategoryIdList() {

        Indicator indicator1 = new Indicator(1, "Ind 1", "Ind Def 1");
        Category category1 = Category.builder().id(1).name("Cat 1").indicators(asList(indicator1)).build();

        Indicator indicator2 = new Indicator(1, "Ind 2", "Ind Def 2");
        Category category2 = Category.builder().id(4).name("Cat 4").indicators(asList(indicator2)).build();

        List<Category> categories= asList(category1, category2);
        when(iCategoryRepository.findAll()).thenReturn(categories);

        List<CategoryIndicatorDto> categoryIndicatorMapping = categoryIndicatorService.getHealthIndicatorOptions();
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
    public void shouldGetTransformedCategoryDto() {

        List<Category> categories;

        IndicatorScore option1 = IndicatorScore.builder().indicatorId(1).score(1).definition("Score 1").build();
        IndicatorScore option2 = IndicatorScore.builder().indicatorId(1).score(2).definition("Score 2").build();
        Indicator indicator1 = Indicator.builder().indicatorId(1).name("Ind 1").definition("Ind Def 1").options(asList(option1, option2)).build();
        Category category1 = Category.builder().id(1).name("Cat 1").indicators(asList(indicator1)).build();

        IndicatorScore option3 = IndicatorScore.builder().indicatorId(2).score(3).definition("Score 3").build();
        IndicatorScore option4 = IndicatorScore.builder().indicatorId(2).score(4).definition("Score 4").build();
        Indicator indicator2 = Indicator.builder().indicatorId(2).name("Ind 2").definition("Ind Def 2").options(asList(option3, option4)).build();
        Category category2 = Category.builder().id(4).name("Cat 4").indicators(asList(indicator2)).build();

        categories = asList(category1, category2);
        when(iCategoryRepository.findAll()).thenReturn(categories);

        List<CategoryIndicatorDto> categoryIndicatorMapping = categoryIndicatorService.getHealthIndicatorOptions();

        assertThat(categoryIndicatorMapping.size(), is(2));
        assertThat(categoryIndicatorMapping.get(0).getCategoryId(), is(1));
        assertThat(categoryIndicatorMapping.get(0).getCategoryName(), is("Cat 1"));
        assertThat(categoryIndicatorMapping.get(0).getIndicators().get(0).getIndicatorId(), is(1));
        assertThat(categoryIndicatorMapping.get(0).getIndicators().get(0).getIndicatorName(), is("Ind 1"));
        assertThat(categoryIndicatorMapping.get(0).getIndicators().get(0).getIndicatorDefinition(), is("Ind Def 1"));
        assertThat(categoryIndicatorMapping.get(0).getIndicators().get(0).getScores().stream().map(s -> s.getScoreDefinition())
                .collect(toList()), hasItems("Score 1", "Score 2"));
        assertThat(categoryIndicatorMapping.get(1).getCategoryId(), is(4));
        assertThat(categoryIndicatorMapping.get(1).getCategoryName(), is("Cat 4"));
        assertThat(categoryIndicatorMapping.get(1).getIndicators().get(0).getIndicatorId(), is(2));
        assertThat(categoryIndicatorMapping.get(1).getIndicators().get(0).getIndicatorName(), is("Ind 2"));
        assertThat(categoryIndicatorMapping.get(1).getIndicators().get(0).getIndicatorDefinition(), is("Ind Def 2"));
        assertThat(categoryIndicatorMapping.get(1).getIndicators().get(0).getScores().stream().map(s -> s.getScoreDefinition())
                .collect(toList()), hasItems("Score 3", "Score 4"));

    }

    @Test
    public void shouldGetTransformedCategoryDtoInSortedByIndicatorIdListForEachCategory() {

        Indicator indicator1 = new Indicator(1, "Ind 1", "Ind Def 1");
        Indicator indicator2 = new Indicator(5, "Ind 5", "Ind Def 5");
        Category category1 = Category.builder().id(1).name("Cat 1").indicators(asList(indicator1, indicator2)).build();

        Indicator indicator4 = new Indicator(8, "Ind 8", "Ind Def 8");
        Indicator indicator3 = new Indicator(2, "Ind 2", "Ind Def 2");
        Category category2 = Category.builder().id(4).name("Cat 4").indicators(asList(indicator3, indicator4)).build();

        List<Category> categories = asList(category1, category2);
        when(iCategoryRepository.findAll()).thenReturn(categories);


        when(iCategoryRepository.findAll()).thenReturn(categories);

        List<CategoryIndicatorDto> categoryIndicatorMapping = categoryIndicatorService.getHealthIndicatorOptions();
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

        Category category1 = Category.builder().id(1).name("Cat 1").indicators(null).build();
        when(iCategoryRepository.findAll()).thenReturn(asList(category1));

        List<CategoryIndicatorDto> categoryIndicatorMapping = categoryIndicatorService.getHealthIndicatorOptions();
        assertThat(categoryIndicatorMapping.size(), is(1));
        assertThat(categoryIndicatorMapping.get(0).getCategoryId(), is(1));
        assertThat(categoryIndicatorMapping.get(0).getCategoryName(), is("Cat 1"));
        assertTrue(categoryIndicatorMapping.get(0).getIndicators().isEmpty());
    }
}