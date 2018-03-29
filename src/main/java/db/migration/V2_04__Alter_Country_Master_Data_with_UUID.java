package db.migration;

import org.springframework.jdbc.core.JdbcTemplate;

public class V2_04__Alter_Country_Master_Data_with_UUID extends BaseMigration {

    @Override
    public void doMigrate(JdbcTemplate jdbcTemplate) {

        jdbcTemplate.execute("ALTER TABLE master.countries " +
                "ADD COLUMN unique_id UUID DEFAULT uuid_generate_v4() NOT NULL");
    }
}
