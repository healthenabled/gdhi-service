package db.migration;

import org.springframework.jdbc.core.JdbcTemplate;

public class V3_3__Insert_i18_Language extends BaseMigration {
    @Override
    public void doMigrate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update(
                "INSERT INTO i18n.language(id, language_name) VALUES('ar', 'arabic'); " +
                    "INSERT INTO i18n.language(id, language_name) VALUES('en', 'english'); " +
                    "INSERT INTO i18n.language(id, language_name) VALUES('fr', 'french'); " +
                    "INSERT INTO i18n.language(id, language_name) VALUES('pt', 'portuguese'); " +
                    "INSERT INTO i18n.language(id, language_name) VALUES('es', 'spanish'); "
        );
    }
}
