package it.gdhi.repository;

import it.gdhi.model.*;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ActiveProfiles("test")
public class ICategoryIndicatorMappingRepositoryTest {

    @Autowired
    private ICategoryIndicatorMappingRepository iCategoryIndicatorMappingRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void shouldFetchCountryCategoryIndicatorDetailsOnHealthIndicatorScoreFetchForCountry() {

        CategoryIndicator categoryIndicator1 = new CategoryIndicator(new CategoryIndicatorId(1, 1));
        CategoryIndicator categoryIndicator2 = new CategoryIndicator(new CategoryIndicatorId(2, 2));
        entityManager.persist(categoryIndicator1);
        entityManager.persist(categoryIndicator2);

        entityManager.flush();
        entityManager.clear();

        List<CategoryIndicator> categoryIndicators = iCategoryIndicatorMappingRepository.findAll();

        assertThat(categoryIndicators.size(), is(2));
        Optional<CategoryIndicator> firstCategory = categoryIndicators.stream().filter(c -> c.getCategoryId().equals(1))
                .findFirst();
        assertThat(firstCategory.get().getCategoryName(), is("Leadership and Governance"));
        assertThat(firstCategory.get().getIndicatorName(),
            is("Digital health prioritized at the national " +
                    "level through dedicated bodies / mechanisms for governance"));
        assertThat(firstCategory.get().getIndicatorDefinition(),
            is("Does the country have a separate department / agency / national " +
                    "working group for digital health?"));

        Optional<CategoryIndicator> secondCategory = categoryIndicators.stream().filter(c -> c.getCategoryId()
                .equals(2)).findFirst();
        assertThat(secondCategory.get().getCategoryName(), is("Strategy and Investment"));
        assertThat(secondCategory.get().getIndicatorName(), is("Digital Health prioritized at the national level " +
                "through planning"));
        assertThat(secondCategory.get().getIndicatorDefinition(), is("Is digital health included and budgeted for " +
                "in national health or relevant national strategies and/or plan(s)?"));
    }

}