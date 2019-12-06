package db.migration;

import org.springframework.jdbc.core.JdbcTemplate;

public class V3_8__Insert_i18_Health_Indicator_es_fr_pt extends BaseMigration {
    @Override
    public void doMigrate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update(
            "INSERT INTO i18n.health_indicator(score_id, language_id, definition) VALUES(, '', ''); "
        );
    }
}
