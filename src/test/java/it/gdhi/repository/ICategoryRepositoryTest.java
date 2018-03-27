package it.gdhi.repository;

import it.gdhi.model.*;
import it.gdhi.model.id.CategoryIndicatorId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ActiveProfiles("test")
public class ICategoryRepositoryTest {
    @Autowired
    ICategoryRepository categoryRepository;
    @Autowired
    private EntityManager entityManager;


    @Test
    public void shouldFetchAllCategoriesWithAssociation() throws Exception {
        Indicator indicator21 = Indicator.builder().indicatorId(21).name("indicator 21").definition("this is indicator 21").build();
        Indicator indicator20 = Indicator.builder().indicatorId(20).name("indicator 20").definition("this is indicator 20").build();
        entityManager.persist(indicator21);
        entityManager.persist(indicator20);
        Category category9 = Category.builder().id(9).name("categort 9").build();
        Category category8 = Category.builder().id(8).name("categort 8").build();
        entityManager.persist(category9);
        entityManager.persist(category8);
        CategoryIndicator categoryIndicator1 = CategoryIndicator.builder()
                .categoryIndicatorId(CategoryIndicatorId.builder().categoryId(category9.getId()).indicatorId(indicator21.getIndicatorId()).build()).build();
        CategoryIndicator categoryIndicator2 = CategoryIndicator.builder()
                .categoryIndicatorId(CategoryIndicatorId.builder().categoryId(category9.getId()).indicatorId(indicator20.getIndicatorId()).build()).build();
        entityManager.persist(categoryIndicator1);
        entityManager.persist(categoryIndicator2);
        IndicatorScore indicatorScore = IndicatorScore.builder().id(180L).indicatorId(indicator20.getIndicatorId()).score(null).definition("NA").build();
        IndicatorScore indicatorScore1 = IndicatorScore.builder().id(190L).indicatorId(indicator20.getIndicatorId()).score(2).definition("score 2").build();
        IndicatorScore indicatorScore2 = IndicatorScore.builder().id(500L).indicatorId(indicator20.getIndicatorId()).score(1).definition("score 1").build();
        entityManager.persist(indicatorScore);
        entityManager.persist(indicatorScore1);
        entityManager.persist(indicatorScore2);
        entityManager.flush();
        entityManager.clear();

        List<Category> categories = categoryRepository.findAll();
        assertEquals(9, categories.size());
        Category category = categories.get(8);
        assertThat(category.getName(), is(category9.getName()));
        assertEquals(2, category.getIndicators().size());
        Indicator indicator = category.getIndicators().get(0);
        assertThat(indicator.getName(), is(indicator20.getName()));
        assertThat(indicator.getDefinition(), is(indicator20.getDefinition()));
        List<IndicatorScore> options = indicator.getOptions();
        assertEquals(3, options.size());
        IndicatorScore actualIndicatorScore = options.get(2);
        assertNull(actualIndicatorScore.getScore());
        actualIndicatorScore = options.get(0);
        assertEquals(1, actualIndicatorScore.getScore().intValue());
        actualIndicatorScore = options.get(1);
        assertEquals(2, actualIndicatorScore.getScore().intValue());
    }
}