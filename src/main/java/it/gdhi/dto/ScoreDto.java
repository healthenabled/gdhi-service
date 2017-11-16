package it.gdhi.dto;

import it.gdhi.model.IndicatorScore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ScoreDto {
    private Integer score;
    private String scoreDefinition;

    public ScoreDto(IndicatorScore indicatorScore) {
        this.score = indicatorScore.getScore();
        this.scoreDefinition = indicatorScore.getDefinition();
    }
}
