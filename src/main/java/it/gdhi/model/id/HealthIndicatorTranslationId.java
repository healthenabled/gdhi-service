package it.gdhi.model.id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HealthIndicatorTranslationId implements Serializable {

    @Column(name = "indicator_id")
    private Integer indicatorId;

    @Column(name = "language_id")
    private String languageId;

}
