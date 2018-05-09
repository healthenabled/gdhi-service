package it.gdhi.repository;

import it.gdhi.model.Phase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface IPhaseRepository extends Repository<Phase, Integer> {

    @Query("SELECT p from Phase p order by p.phaseId asc")
    List<Phase> findAll();
}
