package db.migration;

import org.springframework.jdbc.core.JdbcTemplate;

public class V1_0__Create_Tables extends BaseMigration {

    @Override
    public void doMigrate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("CREATE SCHEMA master;");
        jdbcTemplate.execute("CREATE TABLE master.countries(code VARCHAR PRIMARY KEY, name VARCHAR);");
    }

}
