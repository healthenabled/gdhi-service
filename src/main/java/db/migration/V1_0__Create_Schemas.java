package db.migration;

import org.springframework.jdbc.core.JdbcTemplate;

public class V1_0__Create_Schemas extends BaseMigration {

    @Override
    public void doMigrate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("CREATE SCHEMA master;");
        jdbcTemplate.execute("CREATE SCHEMA validated_config;");
    }

}
