package db.migration;

import org.springframework.jdbc.core.JdbcTemplate;

public class V1_04__Create_Indicator_Scores_Table extends BaseMigration {

    @Override
    public void doMigrate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("CREATE TABLE master.scores(score INTEGER, score_type VARCHAR);");
    }

}
