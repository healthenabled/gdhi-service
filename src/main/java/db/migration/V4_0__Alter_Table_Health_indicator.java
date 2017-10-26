package db.migration;

import org.springframework.jdbc.core.JdbcTemplate;

public class V4_0__Alter_Table_Health_indicator extends BaseMigration {

    @Override
    public void doMigrate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("CREATE TABLE master.scores(score INTEGER, score_type VARCHAR);");
        jdbcTemplate.execute("ALTER TABLE master.health_indicator_scores drop column score_type;");
    }

}
