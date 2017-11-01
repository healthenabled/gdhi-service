package db.migration;

import org.springframework.jdbc.core.JdbcTemplate;

public class V1_11__Insert_Category_Names extends BaseMigration {
    @Override
    public void doMigrate(JdbcTemplate jdbcTemplate) {

        jdbcTemplate.update("INSERT INTO master.categories(id, name) VALUES" +
                "(1, \'Leadership and Governance\')," +
                "(2, \'Strategy and Investment\')," +
                "(3, \'Legislation, Policy, and Compliance\')," +
                "(4, \'Workforce\'),"+
                "(5, \'Standards and Interoperability\')," +
                "(6, \'Infrastructure\')," +
                "(7, \'Services and Applications\');");
    }
}



