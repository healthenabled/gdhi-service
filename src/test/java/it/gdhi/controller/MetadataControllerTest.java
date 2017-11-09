package it.gdhi.controller;

import it.gdhi.dto.CategoryIndicatorDto;
import it.gdhi.service.CategoryIndicatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MetadataControllerTest {

    @InjectMocks
    private MetaDataController metaDataController;

    @Mock
    private CategoryIndicatorService categoryIndicatorService;

    @Test
    public void shouldInvokeDevelopmentIndicatorRepoToFetchCountryContextInfo() {
        CategoryIndicatorDto categoryIndicatorDto = new CategoryIndicatorDto();
        when(categoryIndicatorService.getCategoryIndicatorMapping())
                .thenReturn(singletonList(categoryIndicatorDto));
        List<CategoryIndicatorDto> categoryIndicators = metaDataController.getCategoryIndicatorMapping();
        assertThat(categoryIndicators.size(), is(1));
        assertThat(categoryIndicators.get(0), is(categoryIndicatorDto));
    }

}