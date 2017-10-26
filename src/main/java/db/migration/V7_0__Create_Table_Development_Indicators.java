 package db.migration;

import org.springframework.jdbc.core.JdbcTemplate;

public class V7_0__Create_Table_Development_Indicators extends BaseMigration {
    @Override
    public void doMigrate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("CREATE SCHEMA validated_config;");

        jdbcTemplate.execute("CREATE TABLE validated_config.development_indicators(country_code VARCHAR PRIMARY KEY ,"+
                        "gni_per_capita NUMERIC ,"+
                        "total_population DOUBLE PRECISION , life_expectancy NUMERIC ,health_expenditure NUMERIC ,"+
                        "ncd_deaths_per_capita_total NUMERIC , under_5_mortality NUMERIC , doing_business_index NUMERIC ,"+
                        "adult_literacy NUMERIC , " +
                        "FOREIGN KEY(country_code) REFERENCES master.countries (code));");
    }
} 