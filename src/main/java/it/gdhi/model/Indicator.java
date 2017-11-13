package it.gdhi.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(schema = "master", name="health_indicators")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Indicator {

    @Id
    @Column(name = "indicator_id")
    private Integer indicatorId;

    private String name;

    private String definition;

}
