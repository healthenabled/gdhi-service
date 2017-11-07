package it.gdhi.repository;

import it.gdhi.model.CategoryIndicator;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ICategoryIndicatorMappingRepository extends Repository<CategoryIndicator, Long> {

    List<CategoryIndicator> findAll();

}

