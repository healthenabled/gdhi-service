package it.gdhi.integration;

import io.restassured.response.Response;
import it.gdhi.Application;
import it.gdhi.dto.HealthIndicatorDto;
import it.gdhi.model.HealthIndicator;
import it.gdhi.model.id.HealthIndicatorId;
import it.gdhi.repository.IHealthIndicatorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@ActiveProfiles("test")
public class CountryIntegrationTest extends BaseIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private IHealthIndicatorRepository healthIndicatorRepository;

    @Test
    public void shouldGetHealthIndicatorForACountry() throws Exception {
        String countryId = "IND";
        Integer categoryId1 = 1;
        Integer categoryId2 = 2;
        Integer categoryId3 = 3;
        Integer indicatorId1_1 = 1;
        Integer indicatorId1_2 = 2;
        Integer indicatorId2_1 = 3;
        Integer indicatorId2_2 = 4;
        Integer indicatorId3_1 = 5;
        Integer indicatorId3_2 = 6;

        List<HealthIndicatorDto> healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).score(1).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).score(2).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).score(3).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_2).score(null).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_1).score(null).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_2).score(null).supportingText("sp1").build());

        setupHealthIndicatorsForCountry(countryId, healthIndicatorDtos);

        Response response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/countries/IND/health_indicators");

        String responseJSON = response.asString();
        String expectedJSON = expectedResponseJson("health_indicators.json");
        HashMap actualMap = getMapper().readValue(responseJSON, HashMap.class);
        HashMap expectedMap = getMapper().readValue(expectedJSON, HashMap.class);
        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void shouldGetOverAllScore() throws Exception {
        String india = "IND";
        String uk = "GBR";
        String pakistan = "PAK";
        Integer categoryId1 = 1;
        Integer categoryId2 = 2;
        Integer categoryId3 = 3;
        Integer indicatorId1_1 = 1;
        Integer indicatorId1_2 = 2;
        Integer indicatorId2_1 = 3;
        Integer indicatorId2_2 = 4;
        Integer indicatorId3_1 = 5;
        Integer indicatorId3_2 = 6;

        List<HealthIndicatorDto> healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).score(1).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).score(2).supportingText("sp2").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).score(3).supportingText("sp3").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_2).score(null).supportingText("sp4").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_1).score(null).supportingText("sp5").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_2).score(null).supportingText("sp6").build());

        setupHealthIndicatorsForCountry(india, healthIndicatorDtos);

        healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).score(4).supportingText("sp7").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).score(5).supportingText("sp8").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).score(3).supportingText("sp9").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_2).score(null).supportingText("sp10").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_1).score(null).supportingText("sp11").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_2).score(null).supportingText("sp12").build());

        setupHealthIndicatorsForCountry(uk, healthIndicatorDtos);

        healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).score(null).supportingText("sp13").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).score(null).supportingText("sp14").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).score(null).supportingText("sp15").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_2).score(null).supportingText("sp16").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_1).score(null).supportingText("sp17").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_2).score(null).supportingText("sp18").build());

        setupHealthIndicatorsForCountry(pakistan, healthIndicatorDtos);

        Response response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/global_health_indicators");

        String responseJSON = response.asString();
        String expectedJSON = expectedResponseJson("global_indicators.json");
        HashMap actualMap = getMapper().readValue(responseJSON, HashMap.class);
        HashMap expectedMap = getMapper().readValue(expectedJSON, HashMap.class);
        assertEquals(expectedMap, actualMap);

    }

    private void setupHealthIndicatorsForCountry(String countryId, List<HealthIndicatorDto> healthIndicatorDtos) {
        healthIndicatorDtos.stream().forEach(healthIndicator -> {
            HealthIndicatorId healthIndicatorId1 = new HealthIndicatorId(countryId,healthIndicator.getCategoryId(),healthIndicator.getIndicatorId());
            HealthIndicator healthIndicatorSetupData1 = new HealthIndicator(healthIndicatorId1, healthIndicator.getScore(), healthIndicator.getSupportingText());
            healthIndicatorRepository.save(healthIndicatorSetupData1);
        });
    }
}
