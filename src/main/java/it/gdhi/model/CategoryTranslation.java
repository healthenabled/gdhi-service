package it.gdhi.model;

import it.gdhi.model.id.CategoryTranslationId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(schema = "i18n", name = "category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoryTranslation {

    @EmbeddedId
    private CategoryTranslationId id;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Category category;
}
