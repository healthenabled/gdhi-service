package it.gdhi.common.repository;

import it.gdhi.ApplicationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationTest.class)
@Transactional
public class CountryDetailRepositoryTest {

    @Autowired
    private CountryDetailRepository countryDetailRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testInsert() {
        int size = countryDetailRepository.findAll().size();
        assertThat(size, is(2));
        System.out.println("*************" + size + "*************");
    }

}