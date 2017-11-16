package it.gdhi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "master", name="categories")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    private Integer id;

    private String name;

    @OneToMany
    @JoinTable(schema = "master", name="categories_indicators",
            joinColumns=@JoinColumn(name="category_id"),
            inverseJoinColumns=@JoinColumn(name="indicator_id"))
    @OrderBy
    private List<Indicator> indicators;

    public Category(Integer categoryId, String name) {
        this.id = categoryId;
        this.name = name;
    }
}
