package it.gdhi.controller;

import it.gdhi.model.CategoryIndicator;
import it.gdhi.repository.ICategoryIndicatorMappingRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MetadataControllerTest {

    @InjectMocks
    private MetaDataController metaDataController;

    @Mock
    private ICategoryIndicatorMappingRepository iCategoryIndicatorMappingRepository;

    @Test
    public void shouldInvokeDevelopmentIndicatorRepoToFetchCountryContextInfo() {
        CategoryIndicator categoryIndicator = new CategoryIndicator();
        when(iCategoryIndicatorMappingRepository.findAll()).thenReturn(Arrays.asList(categoryIndicator));
        List<CategoryIndicator> categoryIndicatorMapping = metaDataController.getCategoryIndicatorMapping();
        assertThat(categoryIndicatorMapping.size(), is(1));   
        assertThat(categoryIndicatorMapping.get(0), is(categoryIndicator));
    }

}