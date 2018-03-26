package db.migration;

import org.springframework.jdbc.core.JdbcTemplate;

public class V2_01__Rename_Validated_Config_schema extends BaseMigration {

    @Override
    public void doMigrate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("ALTER SCHEMA validated_config RENAME TO country_health_data");
    }
}
