package it.gdhi.internationalization.model;

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
public class CategoryTranslationId implements Serializable {

    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "language_id")
    private String languageId;

}
