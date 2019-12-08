package it.gdhi.internationalization.repository;

import it.gdhi.internationalization.model.IndicatorTranslation;
import it.gdhi.utils.LanguageCode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface IIndicatorTranslationRepository extends Repository<IndicatorTranslation, Long> {

    @Query("SELECT c FROM IndicatorTranslation c " +
            "WHERE c.id.languageId = :languageId and c.id.indicatorId = :indicatorId")
    IndicatorTranslation findTranslationForLanguage(@Param("languageId") LanguageCode languageId,
                                                    @Param("indicatorId") Integer indicatorId);
}
