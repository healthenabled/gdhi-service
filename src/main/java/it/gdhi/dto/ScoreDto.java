package it.gdhi.dto;

import it.gdhi.model.IndicatorScore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class ScoreDto {
    private Long scoreId;
    private Integer score;
    private String scoreDefinition;

    ScoreDto(IndicatorScore indicatorScore) {
        this.scoreId = indicatorScore.getId();
        this.score = indicatorScore.getScore();
        this.scoreDefinition = indicatorScore.getDefinition();
    }
}
