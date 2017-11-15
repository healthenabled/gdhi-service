package it.gdhi.model;

import it.gdhi.model.id.HealthIndicatorId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(schema = "validated_config", name="health_indicators")
@AllArgsConstructor
@NoArgsConstructor
@Getter
//TODO add not null and foreign key constraints for cat_id, ind_id. score -  foreign key
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

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumns({
            @JoinColumn(name = "indicator_id", referencedColumnName = "indicator_id",
                    insertable = false, updatable = false),
            @JoinColumn(name = "indicator_score", referencedColumnName = "score",
                    insertable = false, updatable = false)})
    private IndicatorScore indicatorScore;
    //TODO test null score
    @Column(name="indicator_score")
    private Integer score;

    public HealthIndicator(HealthIndicatorId healthIndicatorId, Integer indicatorScore) {
        this.healthIndicatorId = healthIndicatorId;
        this.score = indicatorScore;
    }

    public String getIndicatorName() {
        return Optional.ofNullable(indicatorScore)
                .map(IndicatorScore::getIndicator)
                .map(Indicator::getName)
                .orElse(null);
    }

    public Integer getIndicatorId() {
        return Optional.ofNullable(indicatorScore)
                .map(IndicatorScore::getIndicator)
                .map(Indicator::getIndicatorId)
                .orElse(null);
    }

    public String getIndicatorDescription() {
        return Optional.ofNullable(indicatorScore)
                .map(IndicatorScore::getIndicator)
                .map(Indicator::getDefinition)
                .orElse(null);
    }

    public String getScoreDescription() {
        return Optional.ofNullable(indicatorScore)
                .map(IndicatorScore::getDefinition)
                .orElse(null);
    }
}
