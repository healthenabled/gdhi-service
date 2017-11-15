package it.gdhi.model;

import it.gdhi.model.id.IndicatorScoreId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(schema = "master", name="health_indicator_scores")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class IndicatorScore {
    @EmbeddedId
    private IndicatorScoreId id;

    private String definition;
}
