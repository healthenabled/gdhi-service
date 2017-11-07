package it.gdhi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryIndicatorId implements Serializable {

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "indicator_id")
    private Integer indicatorId;

}
