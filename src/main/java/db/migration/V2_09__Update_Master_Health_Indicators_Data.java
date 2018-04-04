package db.migration;

import org.springframework.jdbc.core.JdbcTemplate;

public class V2_09__Update_Master_Health_Indicators_Data extends BaseMigration {

    @Override
    public void doMigrate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("UPDATE master.health_indicators SET definition='Does the country have an eHealth or digital health strategy or framework and a costed digital health plan?' " +
                "where indicator_id=3");
        jdbcTemplate.update("UPDATE master.health_indicators SET definition='What is the estimated percent (%) of the annual public spending on health committed to digital health?' " +
                "where indicator_id=4");

        jdbcTemplate.update("UPDATE master.health_indicators SET definition='Is digital health part of curriculum for health and health-related support professionals in training, in general?' " +
                "where indicator_id=9");

        jdbcTemplate.update("UPDATE master.health_indicators SET definition='Is there a national digital health (eHealth) architectural framework and/or health information exchange (HIE) established?' " +
                "where indicator_id=13");

        jdbcTemplate.update("UPDATE master.health_indicators SET definition='Extract the WEF Network Readiness Index score' " +
                "where indicator_id=15");

        jdbcTemplate.update("UPDATE master.health_indicators SET name ='Digital identity management of service providers, administrators, and facilities for digital health, including location data for GIS mapping'" +
                ", definition='Are health system registries of uniquely identifiable providers, administrators, and " +
                "public facilities (and private if applicable) available, accessible and current? Is the data geotagged to enable GIS mapping?' " +
                "where indicator_id=18");

        jdbcTemplate.update("UPDATE master.health_indicators SET name ='Digital identity management of individuals for health'" +
                ", definition='Are secure registries or a master patient index of uniquely identifiable individuals " +
                "available, accessible and current for use for health-related purposes?' " +
                "where indicator_id=19");

        //New health indicators

        jdbcTemplate.update("INSERT INTO master.health_indicators (indicator_id, code, name, definition)" +
                " VALUES (20, '9a', " +
                "'Digital health integrated in health and related professional pre-service training (prior to deployment)'," +
                "'Specifically, is digital health part of curriculum for doctors/physicians in medical training?');");
        jdbcTemplate.update("INSERT INTO master.health_indicators (indicator_id, code, name, definition)" +
                " VALUES (21, '9b', " +
                "'Digital health integrated in health and related professional pre-service training (prior to deployment)'," +
                "'Specifically, is digital health part of curriculum for nurses in pre-service training?');");
        jdbcTemplate.update("INSERT INTO master.health_indicators (indicator_id, code, name, definition)" +
                " VALUES (22, '9c', " +
                "'Digital health integrated in health and related professional pre-service training (prior to deployment)'," +
                "'Specifically, is digital health part of curriculum for health and health-related support professionals in training for community health workers?');");

        jdbcTemplate.update("INSERT INTO master.health_indicators (indicator_id, code, name, definition)" +
                " VALUES (23, '10a', " +
                "'Digital health integrated in health and related professional in-service training (after deployment)'," +
                "'Specifically, is digital health part of curriculum for doctors/physicians in the workforce?');");
        jdbcTemplate.update("INSERT INTO master.health_indicators (indicator_id, code, name, definition)" +
                " VALUES (24, '10b', " +
                "'Digital health integrated in health and related professional in-service training (after deployment)'," +
                "'Specifically, is digital health part of curriculum for nurses in the workforce?');");
        jdbcTemplate.update("INSERT INTO master.health_indicators (indicator_id, code, name, definition)" +
                " VALUES (25, '10c', " +
                "'Digital health integrated in health and related professional in-service training (after deployment)'," +
                "'Specifically, is digital health part of curriculum for community health workers in the workforce?');");

        jdbcTemplate.update("INSERT INTO master.health_indicators (indicator_id, code, name, definition)" +
                " VALUES (26, '11a', " +
                "'Training of digital health work force'," +
                "'Specifically, is training in health and/or biomedical informatics (in either public or private institutions) producing trained informaticists or health information systems specialists?');");

        jdbcTemplate.update("INSERT INTO master.health_indicators (indicator_id, code, name, definition)" +
                " VALUES (27, '19a', " +
                "'Digital identity management of individuals for health'," +
                "'Specifically, is there a secure master patient index of uniquely identifiable individuals available, accessible and current for use for health-related purposes?');");
        jdbcTemplate.update("INSERT INTO master.health_indicators (indicator_id, code, name, definition)" +
                " VALUES (28, '19b', " +
                "'Digital identity management of individuals for health'," +
                "'Specifically, is there a secure birth registry of uniquely identifiable individuals available, accessible and current for use for health-related purposes?');");
        jdbcTemplate.update("INSERT INTO master.health_indicators (indicator_id, code, name, definition)" +
                " VALUES (29, '19c', " +
                "'Digital identity management of individuals for health'," +
                "'Specifically, is there a secure death registry of uniquely identifiable individuals available, accessible and current for use for health-related purposes?');");
        jdbcTemplate.update("INSERT INTO master.health_indicators (indicator_id, code, name, definition)" +
                " VALUES (30, '19d', " +
                "'Digital identity ' || 'management ' || 'of' || ' ' || 'individuals for health'," +
                "'Specifically, is there a secure immunization registry of uniquely identifiable individuals available, accessible and current for use for health-related purposes?');");

        //Update health indicator scores

        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='No coordinating body exists and/or nascent governance structure for digital health is constituted on a case-by-case basis.' " +
                "where indicator_id=1 and score=1");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Governance structure and any related working groups have a scope of work (SOW) and conduct regular meetings with stakeholder participation and/or consultation.' " +
                "where indicator_id=1 and score=3");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='The digital health governance structure is institutionalized, consults with other ministries, and monitors implementation of digital health. It is relatively protected from interference or organizational changes. It is nationally recognized as the lead for digital health.The governance structure and its technical working groups emphasize gender balance in membership.' " +
                "where indicator_id=1 and score=5");

        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Digital health is not included in the national health strategy. It is being implemented in an ad hoc fashion in health programs.' " +
                "where indicator_id=2 and score=1");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='There is some discussion of inclusion of digital health in national health or other relevant national strategies or plans. Proposed language for inclusion of digital health in national health or relevant national strategies and/or plans has been made and is under review.' " +
                "where indicator_id=2 and score=2");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Digital health is included in national health or relevant national strategies and/or plans.' " +
                "where indicator_id=2 and score=3");

        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='There is no digital health strategy or framework. Draft digital health strategy or framework developed, but not officially reviewed.' " +
                "where indicator_id=3 and score=1");

        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='No budget line item for digital health available. A budget line item for digital health exists but proportion not available.' " +
                "where indicator_id=4 and score=1");

        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='There is no law on data security (storage, transmission, use) that is relevant to digital health.' " +
                "where indicator_id=5 and score=1");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='There is a law on data security (storage, transmission, use) that is relevant to digital health that has been proposed and is under review.' " +
                "where indicator_id=5 and score=2");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='There is a law on data security (storage, transmission, use) that is relevant to digital health that has been passed, but has not yet been fully implemented.' " +
                "where indicator_id=5 and score=3");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='There is a law on data security (storage, transmission, use) that is relevant to digital health that has been implemented, but not consistenly enforced.' " +
                "where indicator_id=5 and score=4");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='There is a law on data security (storage, transmission, use) that is relevant to digital health that has been implemented and enforced consistently.' " +
                "where indicator_id=5 and score=5");

        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='There is no law to protect individual privacy, governing ownership, access and sharing of individually identifiable digital health data.' " +
                "where indicator_id=6 and score=1");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='There is a law to protect individual privacy, governing ownership, access and sharing of individually identifiable digital health data that has been proposed and is under review.' " +
                "where indicator_id=6 and score=2");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='There is a law to protect individual privacy, governing ownership, access and sharing of individually identifiable digital health data that has been passed, but not yet fully implemented.' " +
                "where indicator_id=6 and score=3");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='There is a law to protect individual privacy, governing ownership, access and sharing of individually identifiable digital health data that has been implemented, but not consistenly enforced.' " +
                "where indicator_id=6 and score=4");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='There is a law to protect individual privacy, governing ownership, access and sharing of individually identifiable digital health data that has been implemented and is enforced consistently.' " +
                "where indicator_id=6 and score=5");

        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='There are no protocols, policies, frameworks or accepted processes governing the clinical and patient care use of connected medical devices and digital health services (e.g. telemedicine, applications), particularly in relation to safety, data integrity and quality of care.' " +
                "where indicator_id=7 and score=1");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Protocols, policies, frameworks or accepted processes governing the clinical and patient care use of connected medical devices and digital health services (e.g. telemedicine, applications), particularly in relation to safety, data integrity and quality of care have been proposed and are under review.' " +
                "where indicator_id=7 and score=2");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Protocols, policies, frameworks or accepted processes governing the clinical and patient care use of connected medical devices and digital health services (e.g. telemedicine, applications), particularly in relation to safety, data integrity and quality of care have been passed, but are not fully implemented.' " +
                "where indicator_id=7 and score=3");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Protocols, policies, frameworks or accepted processes governing the clinical and patient care use of connected medical devices and digital health services (e.g. telemedicine, applications), particularly in relation to safety, data integrity and quality of care have been implemented, but not consistenly enforced.' " +
                "where indicator_id=7 and score=4");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Protocols, policies, frameworks or accepted processes governing the clinical and patient care use of connected medical devices and digital health services (e.g. telemedicine, applications), particularly in relation to safety, data integrity and quality of care have been implemented and are enforced consistently.' " +
                "where indicator_id=7 and score=5");

        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='There are no protocols, policies, frameworks or accepted processes in place to support secure cross-border data exchange and storage.' " +
                "where indicator_id=8 and score=1");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Protocols, policies, frameworks or accepted processes for cross boarder data exchange and storage have been proposed and are under review.' " +
                "where indicator_id=8 and score=2");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Protocols, policies, frameworks or accepted processes for cross boarder data exchange and storage have been passed, but are not fully implemented.' " +
                "where indicator_id=8 and score=3");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Protocols, policies, frameworks or accepted processes for cross boarder data exchange and storage have been implemented, but not consistently enforced.' " +
                "where indicator_id=8 and score=4");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Protocols, policies, frameworks or accepted processes for cross boarder data exchange and storage have been implemented and enforced consistently.' " +
                "where indicator_id=8 and score=5");

        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='There is no digital health curriculum for health professionals as part of pre-service training requirements.' " +
                "where indicator_id=9 and score=1");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Digital health curriculum proposed and under review as part of pre-service training requirements.' " +
                "where indicator_id=9 and score=2");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Digital health curriculum implementation underway covering an estimated 0-25% of health professionals in pre-service training.' " +
                "where indicator_id=9 and score=3");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Digital health taught in relevant institutions with an estimated 50-75% health professionals receiving pre-service training.' " +
                "where indicator_id=9 and score=4");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Digital health taught in relevant institutions with >75% of health professionals receiving pre-service training.' " +
                "where indicator_id=9 and score=5");

        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='There is no digital health curriculum as part of in-service (continuing education) training for health professionals in the workforce.' " +
                "where indicator_id=10 and score=1");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Digital health curriculum proposed and under review as part of in-service (continuing edication) training for health professionals in the workforce.' " +
                "where indicator_id=10 and score=2");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Digital health curriculum is implemented as part of in-service (continuing edication) training for 0-25% health professionals in the workforce.' " +
                "where indicator_id=10 and score=3");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Digital health curriculum is implemented as part of in-service (continuing edication) training for 50-75% health professionals in the workforce.' " +
                "where indicator_id=10 and score=4");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Digital health curriculum is implemented as part of in-service (continuing edication) training for >75% health professionals in the workforce.' " +
                "where indicator_id=10 and score=5");

        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='There is no training available for digital heath workforce available in the country.' " +
                "where indicator_id=11 and score=1");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Digital heath workforce needs assessed, gaps identified and training options under development.' " +
                "where indicator_id=11 and score=2");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Professional training is available, but graduates are not yet deployed.' " +
                "where indicator_id=11 and score=3");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Trained digital health professionals available and deployed, but essential personnel gaps remain.' " +
                "where indicator_id=11 and score=4");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Sufficient numbers of trained digital health professionals available to support national digital health needs.' " +
                "where indicator_id=11 and score=5");

        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='No workforce strategy, policy, or guide that recognizes digital health is in place. Distribution of digital health work force is ad hoc.' " +
                "where indicator_id=12 and score=1");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='A national needs assessment shows the number and types of skills needed to support digital health with an explicit focus on training cadres of female health workers.' " +
                "where indicator_id=12 and score=2");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Digital health staff roles and responsibilities are mapped to the government''s workforce and career schemes and 25-50% of needed public sector digital health workforce in place.' " +
                "where indicator_id=12 and score=3");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='An HR policy and strategic plan exists that identifies skills and functions needed to support digital health with an explicit focus on training cadres of female health workers and an estimated 50-75% of public sector digital health workforce in place.' " +
                "where indicator_id=12 and score=4");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='A long-term plan is in place to grow and sustain staff with the skills needed to sustain digital health at national and subnational levels with an explicit focus on training cadres of female health workers with an estimated >75% of positions needed filled. Performance management systems are in place to ensure growth and sustainability of the digital health workforce with sufficient supply to meet digital health needs and little staff turnover.' " +
                "where indicator_id=12 and score=5");

        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='There is no national digital health (eHealth) architectural framework and/or health information exchange (HIE) established.' " +
                "where indicator_id=13 and score=1");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='The government leads, manages, and enforces implementation of the national digital health architecture and/or the health information exchange (HIE), which are fully implemented following industry standards.' " +
                "where indicator_id=13 and score=4");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='The national digital health architecture and/or health information exchange (HIE) provides core data exchange functions and is periodically reviewed and updated to meet the needs of the changing digital health architecture. There is continuous learning, innovation, and quality control. Data is actively used for national health strategic planning and budgeting.' " +
                "where indicator_id=13 and score=5");

        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='There are no digital health / health information standards for data exchange, transmission, messaging, security, privacy, and hardware.' " +
                "where indicator_id=14 and score=1");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='There are some digital health / health information standards for data exchange, transmission, messaging, security, privacy, and hardware that have been adopted and/or are used.' " +
                "where indicator_id=14 and score=2");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Digital health / health information standards for data exchange, transmission, messaging, security, privacy, and hardware have been published and disseminated in the country under the government’s leadership.' " +
                "where indicator_id=14 and score=3");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Digital health / health information industry-based technical standards for data exchange, transmission, messaging, security, privacy, and hardware are in use in the majority of applications and systems to ensure the availability of high-quality data. Conformance testing is routinely carried out to certify implementers.' " +
                "where indicator_id=14 and score=4");

        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='There is no articulated plan for supporting digital health infrastructure (including equipment- computers/ tablets/ phones, supplies, software, devices, etc.) provision and maintenance.' " +
                "where indicator_id=16 and score=1");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='A plan for supporting digital health infrastructure (including equipment- computers/ tablets/ phones, supplies, software, devices, etc.) provision and maintenance has been developed, but not implemented.' " +
                "where indicator_id=16 and score=2");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='A plan for supporting digital health infrastructure (including equipment- computers/ tablets/ phones, supplies, software, devices, etc.) provision and maintenance has been implemented partially, but not consistently with estimated 0-25% of necessary digital health infrastructure needed in public healthcare service sector available and in use.' " +
                "where indicator_id=16 and score=3");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='A plan for supporting digital health infrastructure (including equipment- computers/ tablets/ phones, supplies, software, devices, etc.) provision and maintenance has been implemented partially and consistently with estimated 25-50% of necessary digital health infrastructure needed in public healthcare service sector available and in use.' " +
                "where indicator_id=16 and score=4");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Digital health infrastructure (including equipment- computers/ tablets/ phones, supplies, software, devices, etc.) is available, in use, and regularly maintained and upgraded in >75% of public healthcare service sector.' " +
                "where indicator_id=16 and score=5");

        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='National prioritiy areas are not supported by digital health at any scale.' " +
                "where indicator_id=17 and score=1");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Few national priority areas are supported by digital health, and implemention initiated (< 25% priority areas).' " +
                "where indicator_id=17 and score=2");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Some national priority areas supported by scaled digital health systems (25-50% of priority areas).' " +
                "where indicator_id=17 and score=3");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='The majority, but not all national priority areas (50-75% of priority areas) supported by scaled digital health systems.' " +
                "where indicator_id=17 and score=4");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='All nationally prioritized areas supported by national-scale digital health systems (>75%) with monitoring and evaluation systems and results.' " +
                "where indicator_id=17 and score=5");

        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Health system registries of uniquely identifiable providers, administrators, and public facilities (and private if applicable) are not available, accessible and current.' " +
                "where indicator_id=18 and score=1");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Health system registries of uniquely identifiable providers, administrators, and public facilities (and private if applicable) are being developed but are not available for use.' " +
                "where indicator_id=18 and score=2");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Health system registries of uniquely identifiable providers, administrators, and public facilities (and private if applicable) are available for use, but incomplete, partially available, used sporadically, and irregularly maintained.' " +
                "where indicator_id=18 and score=3");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Health system registries of uniquely identifiable providers, administrators, and public facilities (and private if applicable) are available, used, and regularly updated and maintained. The data is geo-tagged to enable GIS mapping.' " +
                "where indicator_id=18 and score=4");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Health system registries of uniquely identifiable providers, administrators, and public facilities (and private if applicable) are available, up-to-date with geo-tagged data, and used for health system and service strategic planning and budgeting.' " +
                "where indicator_id=18 and score=5");

        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='No secure registry or master patient index exists.' " +
                "where indicator_id=19 and score=1");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='A secure registry exists, but is incomplete / partially available, used, and irregularly maintained.' " +
                "where indicator_id=19 and score=2");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='A secure registry exists, is available and in active use and includes <25% of the relevant population.' " +
                "where indicator_id=19 and score=3");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='A secure registry exists, is available and in active use and includes 25-50% of the relevant population.' " +
                "where indicator_id=19 and score=4");
        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='A secure registry exists, is available and in active use and includes >75% of the relevant population. The data is available, used, and curated.' " +
                "where indicator_id=19 and score=5");

        jdbcTemplate.update("UPDATE master.health_indicator_scores SET" +
                " definition='Missing or Not Applicable' " +
                "where score is null");

        //Insert new health indicator scores
        jdbcTemplate.update("insert into master.health_indicator_scores values\n" +
                "(20, null, 'Missing or Not Applicable'),\n" +
                "(20, 1, 'There is no digital health curriculum for doctors/physicians as part of pre-service training requirements.')," +
                "(20, 2, 'Digital health curriculum proposed and under review as part of pre-service training requirements for doctors/physicians.'),\n" +
                "(20, 3, 'Digital health curriculum implementation underway covering an estimated 0-25% doctors/physicians in pre-service training.'),\n" +
                "(20, 4, 'Digital health taught in relevant institutions with an estimated 50-75% of doctors/physicians receiving pre-service training.'),\n" +
                "(20, 5, 'Digital health taught in relevant instutitions with >75% of doctors/physicians receiving pre-service training.');");

        jdbcTemplate.update("insert into master.health_indicator_scores values\n" +
                "(21, null, 'Missing or Not Applicable'),\n" +
                "(21, 1, 'There is no digital health curriculum for nurses as part of pre-service training requirements.'),\n" +
                "(21, 2, 'Digital health curriculum proposed and under review as part of pre-service training requirements for nurses.'),\n" +
                "(21, 3, 'Digital health curriculum implementation underway covering an estimated 0-25% or health professionals in pre-service training.'),\n" +
                "(21, 4, 'Digital health taught in relevant institutions with an estimated 50-75% of nurses receiving pre-service training.'),\n" +
                "(21, 5, 'Digital health taught in relevant institutions with >75% of nurses receiving pre-service training.');");

        jdbcTemplate.update("insert into master.health_indicator_scores values\n" +
                "(22, null, 'Missing or Not Applicable'),\n" +
                "(22, 1, 'There is no digital health curriculum for health professionals as part of pre-service training requirements for community health workers.'),\n" +
                "(22, 2, 'Digital health curriculum proposed and under review as part of pre-service training requirements for community health workers.'),\n" +
                "(22, 3, 'Digital health curriculum implementation underway covering an estimated 0-25% of community health workers in pre-service training.'),\n" +
                "(22, 4, 'Digital health taught in relevant institutions with an estimated 50-75% of community health workers receiving pre-service training.'),\n" +
                "(22, 5, 'Digital health taught in relevant institutions with >75% of community health workers receiving pre-service training.');");

        jdbcTemplate.update("insert into master.health_indicator_scores values\n" +
                "(23, null, 'Missing or Not Applicable'),\n" +
                "(23, 1, 'There is no digital health curriculum as part of in-service (continuing education) training for doctors/physicians in the workforce.'),\n" +
                "(23, 2, 'Digital health curriculum proposed and under review as part of in-service (continuing education) training for doctors/physicians in the workforce.'),\n" +
                "(23, 3, 'Digital health curriculum is implemented as part of in-service (continuing education) training for 0-25% of doctors/physicians in the workforce.'),\n" +
                "(23, 4, 'Digital health curriculum is implemented as part of in-service (continuing education) training for 50-75% of doctors/physicians in the workforce.'),\n" +
                "(23, 5, 'Digital health curriculum is implemented as part of in-service (continuing education) training for >75% of doctors/physicians in the workforce.');");

        jdbcTemplate.update("insert into master.health_indicator_scores values\n" +
                "(24, null, 'Missing or Not Applicable'),\n" +
                "(24, 1, 'There is no digital health curriculum as part of in-service (continuing education) training for nurses in the workforce.'),\n" +
                "(24, 2, 'Digital health curriculum proposed and under review as part of in-service (continuing education) training for nurses in the workforce.'),\n" +
                "(24, 3, 'Digital health curriculum is implemented as part of in-service (continuing education) training for 0-25% of nurses in the workforce.'),\n" +
                "(24, 4, 'Digital health curriculum is implemented as part of in-service (continuing education) training for 50-75% of nurses in the workforce.'),\n" +
                "(24, 5, 'Digital health curriculum is implemented as part of in-service (continuing education) training for >75% of nurses in the workforce.');");

        jdbcTemplate.update("insert into master.health_indicator_scores values\n" +
                "(25, null, 'Missing or Not Applicable'),\n" +
                "(25, 1, 'There is no digital health curriculum as part of in-service (continuing education) training for community health workers in the workforce.'),\n" +
                "(25, 2, 'Digital health curriculum proposed and under review as part of in-service (continuing education) training for community health workers in the workforce.'),\n" +
                "(25, 3, 'Digital health curriculum is implemented as part of in-service (continuing education) training for 0-25% of community health workers in the workforce.'),\n" +
                "(25, 4, 'Digital health curriculum is implemented as part of in-service (continuing education) training for 50-75% of community health workers in the workforce.'),\n" +
                "(25, 5, 'Digital health curriculum is implemented as part of in-service (continuing education) training for >75% of community health workers in the workforce.');");

        jdbcTemplate.update("insert into master.health_indicator_scores values\n" +
                "(26, null, 'Missing or Not Applicable'),\n" +
                "(26, 1, 'There is no training available in informatics or health information systems available in the country.'),\n" +
                "(26, 2, 'Health informatics workforce needs assessed, gaps identified and training options under development.'),\n" +
                "(26, 3, 'Professional training in health informatics is available, but graduates are not yet deployed.'),\n" +
                "(26, 4, 'Trained informatics professionals available and deployed, but essential personnel gaps remain.'),\n" +
                "(26, 5, 'Sufficient numbers of trained health informatics professionals available to support national health information system needs.');");

        jdbcTemplate.update("insert into master.health_indicator_scores values\n" +
                "(27, null, 'Missing or Not Applicable'),\n" +
                "(27, 1, 'No secure master patient index exists.'),\n" +
                "(27, 2, 'A master patient index exists, but is incomplete / partially available, used, and irregularly maintained.'),\n" +
                "(27, 3, 'A master patient index exists, is available and in active use and includes <25% of the relevant population.'),\n" +
                "(27, 4, 'A master patient index exists, is available and in active use and includes 25-50% of the relevant population.'),\n" +
                "(27, 5, 'A master patient index exists, is available and in active use and includes >75% of the relevant population. The data is available, used, and curated.');");

        jdbcTemplate.update("insert into master.health_indicator_scores values\n" +
                "(28, null, 'Missing or Not Applicable'),\n" +
                "(28, 1, 'No secure birth registry exists.'),\n" +
                "(28, 2, 'A secure birth registry exists, but is incomplete / partially available, used, and irregularly maintained.'),\n" +
                "(28, 3, 'A secure birth registry exists, is available and in active use and includes <25% of the relevant population.'),\n" +
                "(28, 4, 'A secure birth registry exists, is available and in active use and includes 25-50% of the relevant population.'),\n" +
                "(28, 5, 'A secure birth registry exists, is available and in active use and includes >75% of the relevant population. The data is available, used, and curated.');");

        jdbcTemplate.update("insert into master.health_indicator_scores values\n" +
                "(29, null, 'Missing or Not Applicable'),\n" +
                "(29, 1, 'No secure death registry exists.'),\n" +
                "(29, 2, 'A secure death registry exists, but is incomplete / partially available, used, and irregularly maintained.'),\n" +
                "(29, 3, 'A secure death registry exists, is available and in active use and includes <25% of the relevant population.'),\n" +
                "(29, 4, 'A secure death registry exists, is available and in active use and includes 25-50% of the relevant population.'),\n" +
                "(29, 5, 'A secure death registry exists, is available and in active use and includes >75% of the relevant population. The data is available, used, and curated.');");

        jdbcTemplate.update("insert into master.health_indicator_scores values\n" +
                "(30, null, 'Missing or Not Applicable'),\n" +
                "(30, 1, 'No secure immunization registry exists.'),\n" +
                "(30, 2, 'A secure immunization registry exists, but is incomplete / partially available, used, and irregularly maintained.'),\n" +
                "(30, 3, 'A secure immunization registry exists, is available and in active use and includes <25% of the relevant population.'),\n" +
                "(30, 4, 'A secure immunization registry exists, is available and in active use and includes 25-50% of the relevant population.'),\n" +
                "(30, 5, 'A secure immunization registry exists, is available and in active use and includes >75% of the relevant population. The data is available, used, and curated.');");

        //Insert Category indicator mappings
        jdbcTemplate.execute("insert into master.categories_indicators(category_id, indicator_id) values \n" +
                "(4, 20),(4, 21),(4, 22),(4, 23),\n" +
                "(4, 24),(4, 25),(4, 26),(7, 27),\n" +
                "(7, 28),(7, 29),(7, 30);");

    }
}
