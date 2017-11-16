package it.gdhi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema ="master", name="categories_indicators")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CategoryIndicator implements Serializable {

    @EmbeddedId
    private CategoryIndicatorId categoryIndicatorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "indicator_id", referencedColumnName = "indicator_id", insertable = false, updatable = false)
    private Indicator indicator;

    public Integer getCategoryId() {
        return category.getId();
    }

    public Integer getIndicatorId() {
        return indicator != null ? indicator.getIndicatorId() : null;
    }

    public String getCategoryName() {
        return category.getName();
    }

    public String getIndicatorName() {
        return indicator != null ? indicator.getName() : null;
    }

    public String getIndicatorDefinition() {
        return indicator != null ? indicator.getDefinition() : null;
    }
}
