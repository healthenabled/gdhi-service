package db.migration;

import org.springframework.jdbc.core.JdbcTemplate;

public class V2_21__Updating_Indicator_Text extends BaseMigration{

    @Override
    public void doMigrate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET definition = 'National health priority areas are not supported by digital health at any scale.' WHERE indicator_id = 17 AND score = 1");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET definition = 'Few national health priority areas are supported by digital health, and implementation initiated (< 25% priority areas).' WHERE indicator_id = 17 AND score = 2");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET definition = 'Some national health priority areas supported by a diverse range of nationally scaled digital health services and applications (25-50% of priority areas).' WHERE indicator_id = 17 AND score = 3");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET definition = 'The majority, but not all national health priority areas (50-75% of priority areas) supported by a diverse range of nationally scaled digital health services and applications.' WHERE indicator_id = 17 AND score = 4");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET definition = 'All nationally prioritized health areas supported by a diverse range of national-scale digital health services and applications (>75%) with monitoring and evaluation systems and results.' WHERE indicator_id = 17 AND score = 5");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET definition = 'A secure registry exists, is available and in active use and includes 26-75% of the relevant population.' WHERE indicator_id = 19 AND score = 4");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET definition = 'A master patient index exists, is available and in active use and includes 26-75% of the relevant population.' WHERE indicator_id = 27 AND score = 4");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET definition = 'A secure digital birth registry exists, is available and in active use and includes 26-75% of the relevant population.' WHERE indicator_id = 28 AND score = 4");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET definition = 'A secure death registry exists, is available and in active use and includes 26-75% of the relevant population.' WHERE indicator_id = 29 AND score = 4");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET definition = 'A secure immunization registry exists, is available and in active use and includes 26-75% of the relevant population.' WHERE indicator_id = 30 AND score = 4");

    }
}
