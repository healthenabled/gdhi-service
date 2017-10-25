package db.migration;

import org.springframework.jdbc.core.JdbcTemplate;

public class V2_0__Create_Table_Health_indicator extends BaseMigration {

    @Override
    public void doMigrate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("CREATE TABLE master.health_indicators(id INTEGER PRIMARY KEY, name VARCHAR, " +
                                 "definition VARCHAR);");
        jdbcTemplate.execute("CREATE TABLE master.health_indicator_scores(score_id INTEGER, score_type VARCHAR, " +
                                 "score INTEGER, definition VARCHAR, " +
                                 "FOREIGN KEY(score_id) REFERENCES master.health_indicators (id));");
    }

}
