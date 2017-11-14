package it.gdhi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IndicatorScoreDto {
    private String name;
    private String indicatorDescription;
    private Integer score;
    private String scoreDescription;

    public boolean hasScore() {
        return this.score != null;
    }
}
