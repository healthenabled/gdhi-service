package it.gdhi.model.id;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IndicatorScoreId implements Serializable {
    @Column(name = "indicator_id")
    private Integer indicatorId;
    private Integer score;
}
