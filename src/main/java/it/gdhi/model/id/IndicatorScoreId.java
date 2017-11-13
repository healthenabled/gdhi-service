package it.gdhi.model.id;

import javax.persistence.Column;
import java.io.Serializable;

public class IndicatorScoreId implements Serializable {
    @Column(name = "indicator_id")
    private Integer indicatorId;
    private Integer score;
}
