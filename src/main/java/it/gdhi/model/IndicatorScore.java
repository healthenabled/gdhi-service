package it.gdhi.model;

import it.gdhi.model.id.IndicatorScoreId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "indicator_id", referencedColumnName = "indicator_id", insertable = false, updatable = false)
    private Indicator indicator;
    private String definition;
}
