package it.gdhi.repository;

import it.gdhi.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ICategoryRepository extends Repository<Category, Long> {

    @Query("SELECT c from Category c order by c.id")
    List<Category> findAll();

}

