package it.gdhi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.gdhi.dto.HealthIndicatorDto;
import it.gdhi.model.HealthIndicator;
import it.gdhi.model.id.HealthIndicatorId;
import it.gdhi.repository.IHealthIndicatorRepository;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BaseIntegrationTest {

    @Autowired
    private DataSource dataSource;

    private ObjectMapper mapper = new ObjectMapper();

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    IHealthIndicatorRepository healthIndicatorRepository;
    @After
    public void tearDown() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"validated_config.health_indicators",
                "validated_config.countries_summary", "validated_config.country_resource_links");
    }

    ObjectMapper getMapper() {
        return this.mapper;
    }

    String expectedResponseJson(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("responses/" + fileName).getFile())));
    }

    void setupHealthIndicatorsForCountry(String countryId, List<HealthIndicatorDto> healthIndicatorDtos) {
        healthIndicatorDtos.stream().forEach(healthIndicator -> {
            HealthIndicatorId healthIndicatorId1 = new HealthIndicatorId(countryId,healthIndicator.getCategoryId(),healthIndicator.getIndicatorId());
            HealthIndicator healthIndicatorSetupData1 = new HealthIndicator(healthIndicatorId1, healthIndicator.getScore(), healthIndicator.getSupportingText());
            healthIndicatorRepository.save(healthIndicatorSetupData1);
        });
    }

    void assertResponse(String responseJSON, String expectedJsonFileName) throws IOException {
        String expectedJSON = expectedResponseJson(expectedJsonFileName);
        HashMap actualMap = getMapper().readValue(responseJSON, HashMap.class);
        HashMap expectedMap = getMapper().readValue(expectedJSON, HashMap.class);
        assertEquals(expectedMap, actualMap);
    }

    SimpleDateFormat getDateFormat() {
        return dateFormat;
    }
}
