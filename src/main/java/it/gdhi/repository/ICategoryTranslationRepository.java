package it.gdhi.repository;

import it.gdhi.model.CategoryTranslation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface ICategoryTranslationRepository extends Repository<CategoryTranslation, Long> {

    @Query("SELECT c.name FROM CategoryTranslation c WHERE c.id.languageId = :languageId and c.id.categoryId = :categoryId")
    String findTranslationForLanguage(@Param("languageId") String languageId,
                                      @Param("categoryId") Integer categoryId);
}
