package it.gdhi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "validated_config", name="health_indicators")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HealthIndicator {

    @EmbeddedId
    private HealthIndicatorId healthIndicatorId;

    private  Integer indicatorScore;

}
