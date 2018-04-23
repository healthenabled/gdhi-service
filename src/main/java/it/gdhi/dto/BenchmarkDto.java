package it.gdhi.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BenchmarkDto {

    public Integer benchmarkScore;
    public String benchmarkValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BenchmarkDto that = (BenchmarkDto) o;
        return Objects.equals(benchmarkScore, that.benchmarkScore) &&
                Objects.equals(benchmarkValue, that.benchmarkValue);
    }

    @Override
    public int hashCode() {

        return Objects.hash(benchmarkScore, benchmarkValue);
    }

}
