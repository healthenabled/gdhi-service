package db.migration;

import org.springframework.jdbc.core.JdbcTemplate;

public class V3_18__Update_Master_Health_Indicators_Data extends BaseMigration {
    @Override
    public void doMigrate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("UPDATE master.health_indicators SET DEFINITION=" +
                "'Is digital health included and budgeted for in national health or relevant national strategies " +
                "and/or plan(s)? The focus of this indicator is on the inclusion of digital health or eHealth in " +
                "the national health strategy.' WHERE indicator_id=2");
        jdbcTemplate.update("UPDATE master.health_indicators SET NAME=" +
                "'Laws or Regulations for privacy, confidentiality and access to health information (Privacy)' " +
                "WHERE indicator_id=6");
        jdbcTemplate.update("UPDATE master.health_indicators SET DEFINITION=" +
                "'Are there protocols, policies, frameworks or accepted processes in place to support secure " +
                "cross-border data exchange and storage? This includes health-related data coming into a country, " +
                "going out of a country, and/or being used in a country related to an individual from another country.' " +
                "WHERE indicator_id=8");
        jdbcTemplate.update("UPDATE master.health_indicators SET DEFINITION=" +
                "'Specifically, is digital health part of curriculum for health and health-related support professionals" +
                " in the workforce in general? [Defined as community health workers, nurses, doctors, allied health, " +
                "health managers/administrators, and technologists]' WHERE indicator_id=10");
        jdbcTemplate.update("UPDATE master.health_indicators SET DEFINITION=" +
                "'In general, is training in digital health / health informatics / health information systems / " +
                "biomedical informatics degree programs (in either public or private institutions) producing trained " +
                "digital health workers?' WHERE indicator_id=11");
        jdbcTemplate.update("UPDATE master.health_indicators SET DEFINITION=" +
                "'Specifically, is there a secure birth registry of uniquely identifiable individuals available, " +
                "accessible and current for use for health-related purposes?' WHERE indicator_id=28");
        jdbcTemplate.update("UPDATE master.health_indicators SET NAME=" +
                "'Training of digital health workforce' " +
                "WHERE indicator_id=11");
        jdbcTemplate.update("UPDATE master.health_indicators SET NAME=" +
                "'Training of digital health workforce' " +
                "WHERE indicator_id=26");
        jdbcTemplate.update("UPDATE master.health_indicators SET DEFINITION=" +
                "'Extract the WEF Network Readiness Index score' " +
                "WHERE indicator_id=15");
        jdbcTemplate.update("UPDATE master.health_indicators SET DEFINITION='Public sector priorities (eg. 14 " +
                "domains included in ISO TR 14639) are supported by nationally-scaled digital health systems. " +
                "(Use separate worksheet to determine the country''s specified priority areas, whether digital " +
                "systems are in place, and whether those systems are national-scale.) [eg. Country X chooses 4 priority " +
                "areas, uses digital systems to address 2 of the 4, with only 1 being at national scale, receives a " +
                "score of 25%.]' where indicator_id=17");
    }
}
