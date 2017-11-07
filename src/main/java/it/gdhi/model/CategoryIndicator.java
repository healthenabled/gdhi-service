package it.gdhi.model;

import com.fasterxml.jackson.annotation.JsonView;
import it.gdhi.view.CategoryIndicatorView;
import lombok.AllArgsConstructor;
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

    @JsonView(CategoryIndicatorView.class)
    public Integer getCategoryId() {
        return category.getId();
    }

    @JsonView(CategoryIndicatorView.class)
    public Integer getIndicatorId() {
        return indicator.getIndicatorId();
    }

    @JsonView(CategoryIndicatorView.class)
    public String getCategoryName() {
        return category.getName();
    }

    @JsonView(CategoryIndicatorView.class)
    public String getIndicatorName() {
        return indicator.getName();
    }

    @JsonView(CategoryIndicatorView.class)
    public String getIndicatorDefinition() {
        return indicator.getDefinition();
    }
}
