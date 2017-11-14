package it.gdhi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryHealthScoreDto {

    private String name;

    private Integer phase;

    private IndicatorScoreDto indicators;

    public CategoryHealthScoreDto(String name, Integer phase) {
        this.name = name;
        this.phase = phase;
    }
}
