package db.migration;

import org.springframework.jdbc.core.JdbcTemplate;

public class V3_1__Alter_Table_Countries_Add_Localisation extends BaseMigration {
    @Override
    public void doMigrate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("ALTER TABLE master.countries " +
                                "ADD COLUMN name_spanish varchar, " +
                                "ADD COLUMN name_french varchar, " +
                                "ADD COLUMN name_portuguese varchar, " +
                                "ADD COLUMN name_arabic varchar;");
    }
}



