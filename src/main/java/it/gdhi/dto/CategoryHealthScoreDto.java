package it.gdhi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryHealthScoreDto {

    private Integer id;

    private String name;

    private Double overallScore;

    private Integer phase;

    private List<IndicatorScoreDto> indicators;

    public CategoryHealthScoreDto(Integer id, String name, Integer phase) {
        this.id = id;
        this.name = name;
        this.phase = phase;

    }
}
