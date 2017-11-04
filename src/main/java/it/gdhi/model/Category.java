package it.gdhi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "master", name="categories")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    private Integer id;

    private String name;

}
