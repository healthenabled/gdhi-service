package it.gdhi.service;

import it.gdhi.dto.BenchmarkDto;
import it.gdhi.model.Country;
import it.gdhi.model.CountryHealthIndicator;
import it.gdhi.model.Indicator;
import it.gdhi.model.IndicatorScore;
import it.gdhi.repository.ICountryHealthIndicatorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static it.gdhi.utils.FormStatus.PUBLISHED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BenchMarkServiceTest {
    @Mock
    ICountryHealthIndicatorRepository iCountryHealthIndicatorRepository;

    @InjectMocks
    BenchMarkService benchMarkService;

    @Test
    public void shouldGetGlobalBenchmarkDataForACountry() {
        String countryId = "IND";
        Integer benchmarkType = -1;
        Integer indicatorId1 = 1;
        Integer indicatorId2 = 2;
        Integer indicatorId3 = 3;

        CountryHealthIndicator countryHealthIndicator1 = buildCountryHealthIndicator(indicatorId1, "PAK", 2);
        CountryHealthIndicator countryHealthIndicator2 = buildCountryHealthIndicator(indicatorId2, "AFG", 1);
        CountryHealthIndicator countryHealthIndicator3 = buildCountryHealthIndicator(indicatorId3, "IRE", 1);

        CountryHealthIndicator countryHealthIndicatorForInd1 = buildCountryHealthIndicator(indicatorId1, countryId, 1);
        CountryHealthIndicator countryHealthIndicatorForInd2 = buildCountryHealthIndicator(indicatorId2, countryId, 1);
        CountryHealthIndicator countryHealthIndicatorForInd3 = buildCountryHealthIndicator(indicatorId3, countryId, 3);

        when(iCountryHealthIndicatorRepository.findHealthIndicatorsByStatusAndPhase(PUBLISHED.name(), benchmarkType))
                .thenReturn(Arrays.asList(countryHealthIndicator1, countryHealthIndicator2, countryHealthIndicator3,
                        countryHealthIndicatorForInd1, countryHealthIndicatorForInd2, countryHealthIndicatorForInd3));

        when(iCountryHealthIndicatorRepository.findHealthIndicatorsByCountryIdAndStatus(countryId, PUBLISHED.name()))
                .thenReturn(
                        Arrays.asList(countryHealthIndicatorForInd1, countryHealthIndicatorForInd2, countryHealthIndicatorForInd3
                        ));

        Map<Integer, BenchmarkDto> expectedBenchMark = new HashMap<>();
        expectedBenchMark.put(indicatorId1, new BenchmarkDto(2, BenchMarkService.BENCHMARK_BELOW_PAR_VALUE));
        expectedBenchMark.put(indicatorId2, new BenchmarkDto(1, BenchMarkService.BENCHMARK_AT_PAR_VALUE));
        expectedBenchMark.put(indicatorId3, new BenchmarkDto(2, BenchMarkService.BENCHMARK_ABOVE_PAR_VALUE));

        Map<Integer, BenchmarkDto> benchmarkFor = benchMarkService.getBenchmarkFor(countryId, benchmarkType);

        assertThat(expectedBenchMark.get(indicatorId1)).isEqualToComparingFieldByField(benchmarkFor.get(indicatorId1));
        assertThat(expectedBenchMark.get(indicatorId2)).isEqualToComparingFieldByField(benchmarkFor.get(indicatorId2));
        assertThat(expectedBenchMark.get(indicatorId3)).isEqualToComparingFieldByField(benchmarkFor.get(indicatorId3));
    }

    @Test
    public void shouldGetGlobalBenchmarkForACountryWithSomeCountryHavingNA() {
        String countryId = "IND";
        Integer benchmarkType = -1;
        Integer indicatorId1 = 1;
        Integer indicatorId2 = 2;
        Integer indicatorId3 = 3;

        CountryHealthIndicator countryHealthIndicator1 = buildCountryHealthIndicator(indicatorId1, "PAK", -1);
        CountryHealthIndicator countryHealthIndicator2 = buildCountryHealthIndicator(indicatorId2, "AFG", 1);
        CountryHealthIndicator countryHealthIndicator3 = buildCountryHealthIndicator(indicatorId3, "IRE", 1);

        CountryHealthIndicator countryHealthIndicatorForInd1 = buildCountryHealthIndicator(indicatorId1, countryId, -1);
        CountryHealthIndicator countryHealthIndicatorForInd2 = buildCountryHealthIndicator(indicatorId2, countryId, -1);

        when(iCountryHealthIndicatorRepository.findHealthIndicatorsByStatusAndPhase(PUBLISHED.name(), benchmarkType))
                .thenReturn(Arrays.asList(countryHealthIndicator1, countryHealthIndicator2,
                        countryHealthIndicatorForInd1, countryHealthIndicatorForInd2, countryHealthIndicator3));

        when(iCountryHealthIndicatorRepository.findHealthIndicatorsByCountryIdAndStatus(countryId, PUBLISHED.name()))
                .thenReturn(
                        Arrays.asList(countryHealthIndicatorForInd1, countryHealthIndicatorForInd2
                        ));

        Map<Integer, BenchmarkDto> benchmarkFor = benchMarkService.getBenchmarkFor(countryId, benchmarkType);

        assertTrue(benchmarkFor.equals(new HashMap<Integer, BenchmarkDto>()));
    }

    @Test
    public void shouldGetGlobalBenchmarkForACountryOnlyUsingMainIndicators() {
        String countryId = "IND";
        Integer benchmarkType = -1;
        Integer indicatorId1 = 1;
        Integer indicatorId2 = 2;
        Integer indicatorId3 = 3;

        CountryHealthIndicator countryHealthIndicator2 = buildCountryHealthIndicator(indicatorId2, "AFG", 1);

        CountryHealthIndicator countryHealthIndicatorForInd1 = buildCountryHealthIndicator(indicatorId1, countryId, 1);
        CountryHealthIndicator countryHealthIndicatorForInd2 = buildCountryHealthIndicator(indicatorId2, countryId, 1);

        CountryHealthIndicator countryHealthIndicatorForInd3 = CountryHealthIndicator.builder()
                .indicator(new Indicator(indicatorId3, "indicator 3", "code",
                        3, 1, new ArrayList<IndicatorScore>(), "some definition"))
                .country(new Country(countryId, "India", UUID.randomUUID(), "PK"))
                .score(1).build();


        when(iCountryHealthIndicatorRepository.findHealthIndicatorsByStatusAndPhase(PUBLISHED.name(), benchmarkType))
                .thenReturn(Arrays.asList(countryHealthIndicatorForInd3, countryHealthIndicator2,
                        countryHealthIndicatorForInd1, countryHealthIndicatorForInd2));

        when(iCountryHealthIndicatorRepository.findHealthIndicatorsByCountryIdAndStatus(countryId, PUBLISHED.name()))
                .thenReturn(
                        Arrays.asList(countryHealthIndicatorForInd1, countryHealthIndicatorForInd2
                        ));

        Map<Integer, BenchmarkDto> expectedBenchMark = new HashMap<>();
        expectedBenchMark.put(indicatorId1, new BenchmarkDto(1, BenchMarkService.BENCHMARK_AT_PAR_VALUE));
        expectedBenchMark.put(indicatorId2, new BenchmarkDto(1, BenchMarkService.BENCHMARK_AT_PAR_VALUE));

        Map<Integer, BenchmarkDto> benchmarkFor = benchMarkService.getBenchmarkFor(countryId, benchmarkType);

        assertThat(expectedBenchMark.get(indicatorId1)).isEqualToComparingFieldByField(benchmarkFor.get(indicatorId1));
        assertThat(expectedBenchMark.get(indicatorId2)).isEqualToComparingFieldByField(benchmarkFor.get(indicatorId2));
    }

    private CountryHealthIndicator buildCountryHealthIndicator(Integer indicatorId, String countryId, Integer score) {
        return CountryHealthIndicator.builder()
                .indicator(new Indicator(indicatorId, "indicator name", "some definition", 1))
                .country(new Country(countryId, "Some Country", UUID.randomUUID(), "SC"))
                .score(score).build();
    }
}