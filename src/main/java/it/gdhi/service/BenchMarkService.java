package it.gdhi.service;

import it.gdhi.dto.BenchmarkDto;
import it.gdhi.model.CountryHealthIndicator;
import it.gdhi.repository.ICountryHealthIndicatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static it.gdhi.utils.FormStatus.PUBLISHED;
import static it.gdhi.utils.ScoreUtils.convertScoreToPhase;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.groupingBy;

@Service
public class BenchMarkService {

    public static final String BENCHMARK_AT_PAR_VALUE="At";
    public static final String BENCHMARK_ABOVE_PAR_VALUE="Above";
    public static final String BENCHMARK_BELOW_PAR_VALUE="Below";

    @Autowired
    private ICountryHealthIndicatorRepository iCountryHealthIndicatorRepository;


    private Map<Integer, Double> calculateBenchmarkScoresForIndicators(String benchmarkType) {
        List<CountryHealthIndicator> publishedCountryHealthIndicators = iCountryHealthIndicatorRepository
                .findHealthIndicatorsByStatus(PUBLISHED.name());

        Map<Integer, Double> indicatorBenchmarkScores = publishedCountryHealthIndicators.stream()
                .filter(indicator -> indicator.isScoreValid() && indicator.getIndicator().getParentId()
                        == null)
                .collect(groupingBy(h -> h.getIndicatorId(),
                        averagingInt(CountryHealthIndicator::getScore)));

        return indicatorBenchmarkScores;
    }


    public Map<Integer, BenchmarkDto> getBenchmarkFor(String countryId, String benchmarkType) {
        Map<Integer, Double> indicatorBenchmarkScores = calculateBenchmarkScoresForIndicators(benchmarkType);

        List<CountryHealthIndicator> countryHealthIndicator = iCountryHealthIndicatorRepository
                .findHealthIndicatorsFor(countryId);

        Map<Integer, BenchmarkDto> benchmarkScoresForCountry = countryHealthIndicator.stream()
                .filter(indicator ->
                        (indicator.isScoreValid() && indicator.getIndicator().getParentId() == null
                                && indicatorBenchmarkScores.containsKey(indicator.getIndicatorId()
                        )))
                .collect(Collectors.toMap(CountryHealthIndicator::getIndicatorId,
                        indicator -> getBenchMarkDtoFor(indicator.getScore(),
                                indicatorBenchmarkScores.get(indicator.getIndicatorId()))));


        return benchmarkScoresForCountry;
    }

    private BenchmarkDto getBenchMarkDtoFor(Integer indicatorCountryScore, Double indicatorBenchmarkScore) {
        Integer benchmarkScore = convertScoreToPhase(indicatorBenchmarkScore);
        String benchmarkValue;
        benchmarkValue = indicatorCountryScore > benchmarkScore ? BENCHMARK_ABOVE_PAR_VALUE :
                (indicatorCountryScore == benchmarkScore ? BENCHMARK_AT_PAR_VALUE : BENCHMARK_BELOW_PAR_VALUE);

        return new BenchmarkDto(benchmarkScore, benchmarkValue);
    }
}
