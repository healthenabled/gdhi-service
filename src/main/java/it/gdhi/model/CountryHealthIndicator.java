package it.gdhi.model;

import it.gdhi.model.id.CountryHealthIndicatorId;
import it.gdhi.model.id.CountryHealthIndicatorId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Optional;

import static it.gdhi.utils.Constants.SCORE_DESCRIPTION_NOT_AVAILABLE;

@Entity
@Table(schema = "country_health_data", name="health_indicators")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CountryHealthIndicator {

    @EmbeddedId
    private CountryHealthIndicatorId countryHealthIndicatorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "country_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Country country;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Category category;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "indicator_id", referencedColumnName = "indicator_id", insertable = false, updatable = false)
    private Indicator indicator;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumns({
            @JoinColumn(name = "indicator_id", referencedColumnName = "indicator_id",
                    insertable = false, updatable = false),
            @JoinColumn(name = "indicator_score", referencedColumnName = "score",
                    insertable = false, updatable = false)})
    private IndicatorScore indicatorScore;

    @Column(name="indicator_score")
    private Integer score;

    @Column(name = "supporting_text")
    private String supportingText;

    public CountryHealthIndicator(CountryHealthIndicatorId countryHealthIndicatorId, Integer indicatorScore) {
        this.countryHealthIndicatorId = countryHealthIndicatorId;
        this.score = indicatorScore;
    }

    public CountryHealthIndicator(CountryHealthIndicatorId countryHealthIndicatorId, Integer indicatorScore,
                                  String supportingText) {
        this.countryHealthIndicatorId = countryHealthIndicatorId;
        this.score = indicatorScore;
        this.supportingText = supportingText;
    }

    public String getIndicatorName() {
        return this.indicator.getName();
    }

    public Integer getIndicatorId() {
        return this.indicator.getIndicatorId();
    }

    public String getIndicatorDescription() {
        return this.indicator.getDefinition();
    }

    public String getCountryId() {
        return this.country.getId();
    }

    public String getScoreDescription() {
        return Optional.ofNullable(indicatorScore)
                .map(IndicatorScore::getDefinition)
                .orElse(SCORE_DESCRIPTION_NOT_AVAILABLE);
    }
}
