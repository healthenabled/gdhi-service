package it.gdhi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.gdhi.dto.HealthIndicatorDto;
import it.gdhi.model.CountryHealthIndicator;
import it.gdhi.model.id.CountryHealthIndicatorId;
import it.gdhi.repository.ICountryHealthIndicatorRepository;
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
    ICountryHealthIndicatorRepository healthIndicatorRepository;

    @After
    public void tearDown() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"country_health_data.health_indicators",
                "country_health_data.countries_summary", "country_health_data.country_resource_links");
    }

    ObjectMapper getMapper() {
        return this.mapper;
    }

    String expectedResponseJson(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("responses/" + fileName).getFile())));
    }

    void setupHealthIndicatorsForCountry(String countryId, List<HealthIndicatorDto> healthIndicatorDtos) {
        healthIndicatorDtos.stream().forEach(healthIndicator -> {
            CountryHealthIndicatorId countryHealthIndicatorId1 = new CountryHealthIndicatorId(countryId,healthIndicator.getCategoryId(),healthIndicator.getIndicatorId());
            CountryHealthIndicator countryHealthIndicatorSetupData1 = new CountryHealthIndicator(countryHealthIndicatorId1, healthIndicator.getScore(), healthIndicator.getSupportingText());
            healthIndicatorRepository.save(countryHealthIndicatorSetupData1);
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
