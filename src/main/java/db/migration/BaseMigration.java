package db.migration;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class BaseMigration implements SpringJdbcMigration {

    protected JdbcTemplate jdbcTemplate;

    @Override
    public void migrate(JdbcTemplate jdbcTemplate) throws Exception {
        this.jdbcTemplate = jdbcTemplate;
        doMigrate(jdbcTemplate);
    }

    public abstract void doMigrate(JdbcTemplate jdbcTemplate);

}
