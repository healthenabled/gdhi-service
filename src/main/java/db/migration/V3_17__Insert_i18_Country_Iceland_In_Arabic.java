package db.migration;

import org.springframework.jdbc.core.JdbcTemplate;

public class V3_17__Insert_i18_Country_Iceland_In_Arabic extends BaseMigration {
    @Override
    public void doMigrate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("INSERT INTO i18n.country(country_id, language_id, name) VALUES('ISL', 'ar', 'آيسلندا'); ");
    }
}
