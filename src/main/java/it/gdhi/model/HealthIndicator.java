package it.gdhi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Table(schema = "validated_config", name="health_indicators")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HealthIndicator {

    @EmbeddedId
    private HealthIndicatorId healthIndicatorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "country_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Country country;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "indicator_id", referencedColumnName = "indicator_id", insertable = false, updatable = false)
    private Indicator indicator;

    private Integer indicatorScore;

    public HealthIndicator(HealthIndicatorId healthIndicatorId, Integer indicatorScore) {
        this.healthIndicatorId = healthIndicatorId;
        this.indicatorScore = indicatorScore;
    }

}
