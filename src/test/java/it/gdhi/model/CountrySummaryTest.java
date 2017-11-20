package it.gdhi.model;

import it.gdhi.dto.CountrySummaryDetailDto;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CountrySummaryTest {
    @Test
    public void shouldTransformIntoCountryResourceLinks() throws Exception {
        CountrySummary countrySummary = new CountrySummary("IND", CountrySummaryDetailDto.builder().resourceLinks(asList("res1", "res2")).build());
        assertEquals(2, countrySummary.getCountryResourceLinks().size());
        assertThat(countrySummary.getCountryResourceLinks().stream().map(CountryResourceLink::getLink).collect(Collectors.toList()),
                Matchers.containsInAnyOrder("res1", "res2"));
    }

    @Test
    public void shouldHandleEmptyResource() throws Exception {
        CountrySummary countrySummary = new CountrySummary("IND", CountrySummaryDetailDto.builder().build());
        assertNull(countrySummary.getCountryResourceLinks());
    }
}