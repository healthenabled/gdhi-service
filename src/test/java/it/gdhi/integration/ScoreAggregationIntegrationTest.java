package it.gdhi.integration;

import io.restassured.response.Response;
import it.gdhi.Application;
import it.gdhi.dto.HealthIndicatorDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static io.restassured.RestAssured.given;
import static java.util.Arrays.asList;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@ActiveProfiles("test")
public class ScoreAggregationIntegrationTest extends BaseIntegrationTest {
    @LocalServerPort
    private int port;

    @Test
    public void shouldGetOverAllGlobalScore() throws Exception {
        String india = "IND";
        String uk = "GBR";
        String pakistan = "PAK";
        Integer categoryId1 = 1;
        Integer categoryId2 = 2;
        Integer categoryId3 = 3;
        Integer categoryId4 = 4;
        Integer indicatorId1_1 = 1;
        Integer indicatorId1_2 = 2;
        Integer indicatorId2_1 = 3;
        Integer indicatorId2_2 = 4;
        Integer indicatorId3_1 = 5;
        Integer indicatorId3_2 = 6;
        Integer indicatorId4_1 = 7;

        List<HealthIndicatorDto> healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).score(1).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).score(2).supportingText("sp2").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).score(3).supportingText("sp3").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_2).score(null).supportingText("sp4").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_1).score(null).supportingText("sp5").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_2).score(null).supportingText("sp6").build(),
                HealthIndicatorDto.builder().categoryId(categoryId4).indicatorId(indicatorId4_1).score(null).supportingText("sp19").build());

        setupHealthIndicatorsForCountry(india, healthIndicatorDtos);

        healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).score(4).supportingText("sp7").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).score(5).supportingText("sp8").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).score(3).supportingText("sp9").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_2).score(null).supportingText("sp10").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_1).score(null).supportingText("sp11").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_2).score(4).supportingText("sp12").build(),
                HealthIndicatorDto.builder().categoryId(categoryId4).indicatorId(indicatorId4_1).score(null).supportingText("sp20").build());

        setupHealthIndicatorsForCountry(uk, healthIndicatorDtos);

        healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).score(null).supportingText("sp13").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).score(null).supportingText("sp14").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).score(null).supportingText("sp15").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_2).score(null).supportingText("sp16").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_1).score(null).supportingText("sp17").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_2).score(null).supportingText("sp18").build(),
                HealthIndicatorDto.builder().categoryId(categoryId4).indicatorId(indicatorId4_1).score(null).supportingText("sp21").build());

        setupHealthIndicatorsForCountry(pakistan, healthIndicatorDtos);

        Response response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/global_health_indicators");

        assertResponse(response.asString(), "global_indicators.json");

    }

    @Test
    public void shouldFilterOverAllGlobalScoreByCategoryAndPhase() throws Exception {
        String india = "IND";
        String uk = "GBR";
        String pakistan = "PAK";
        Integer categoryId1 = 1;
        Integer categoryId2 = 2;
        Integer categoryId3 = 3;
        Integer categoryId4 = 4;
        Integer indicatorId1_1 = 1;
        Integer indicatorId1_2 = 2;
        Integer indicatorId2_1 = 3;
        Integer indicatorId2_2 = 4;
        Integer indicatorId3_1 = 5;
        Integer indicatorId3_2 = 6;
        Integer indicatorId4_1 = 7;

        List<HealthIndicatorDto> healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).score(1).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).score(1).supportingText("sp2").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).score(3).supportingText("sp3").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_2).score(null).supportingText("sp4").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_1).score(null).supportingText("sp5").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_2).score(null).supportingText("sp6").build(),
                HealthIndicatorDto.builder().categoryId(categoryId4).indicatorId(indicatorId4_1).score(null).supportingText("sp19").build());

        setupHealthIndicatorsForCountry(india, healthIndicatorDtos);

        healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).score(1).supportingText("sp7").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).score(5).supportingText("sp8").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).score(3).supportingText("sp9").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_2).score(null).supportingText("sp10").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_1).score(null).supportingText("sp11").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_2).score(4).supportingText("sp12").build(),
                HealthIndicatorDto.builder().categoryId(categoryId4).indicatorId(indicatorId4_1).score(null).supportingText("sp20").build());

        setupHealthIndicatorsForCountry(uk, healthIndicatorDtos);

        healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).score(null).supportingText("sp13").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).score(null).supportingText("sp14").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).score(null).supportingText("sp15").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_2).score(null).supportingText("sp16").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_1).score(null).supportingText("sp17").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_2).score(null).supportingText("sp18").build(),
                HealthIndicatorDto.builder().categoryId(categoryId4).indicatorId(indicatorId4_1).score(null).supportingText("sp21").build());

        setupHealthIndicatorsForCountry(pakistan, healthIndicatorDtos);

        Response response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/global_health_indicators?categoryId="+categoryId1+"&phase=1");

        assertResponse(response.asString(), "filtered_global_indicators.json");

    }

    @Test
    public void shouldFilterOverAllGlobalScoreByCategory() throws Exception {
        String india = "IND";
        String uk = "GBR";
        String pakistan = "PAK";
        Integer categoryId1 = 1;
        Integer categoryId2 = 2;
        Integer categoryId3 = 3;
        Integer categoryId4 = 4;
        Integer indicatorId1_1 = 1;
        Integer indicatorId1_2 = 2;
        Integer indicatorId2_1 = 3;
        Integer indicatorId2_2 = 4;
        Integer indicatorId3_1 = 5;
        Integer indicatorId3_2 = 6;
        Integer indicatorId4_1 = 7;

        List<HealthIndicatorDto> healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).score(1).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).score(1).supportingText("sp2").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).score(3).supportingText("sp3").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_2).score(null).supportingText("sp4").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_1).score(null).supportingText("sp5").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_2).score(null).supportingText("sp6").build(),
                HealthIndicatorDto.builder().categoryId(categoryId4).indicatorId(indicatorId4_1).score(null).supportingText("sp19").build());

        setupHealthIndicatorsForCountry(india, healthIndicatorDtos);

        healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).score(1).supportingText("sp7").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).score(5).supportingText("sp8").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).score(3).supportingText("sp9").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_2).score(null).supportingText("sp10").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_1).score(null).supportingText("sp11").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_2).score(4).supportingText("sp12").build(),
                HealthIndicatorDto.builder().categoryId(categoryId4).indicatorId(indicatorId4_1).score(null).supportingText("sp20").build());

        setupHealthIndicatorsForCountry(uk, healthIndicatorDtos);

        healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).score(null).supportingText("sp13").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).score(null).supportingText("sp14").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).score(null).supportingText("sp15").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_2).score(null).supportingText("sp16").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_1).score(null).supportingText("sp17").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_2).score(null).supportingText("sp18").build(),
                HealthIndicatorDto.builder().categoryId(categoryId4).indicatorId(indicatorId4_1).score(null).supportingText("sp21").build());

        setupHealthIndicatorsForCountry(pakistan, healthIndicatorDtos);

        Response response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/global_health_indicators?categoryId="+categoryId1);

        assertResponse(response.asString(), "global_indicators_filtered_by_category.json");

    }

    @Test
    public void shouldFilterOverAllGlobalScoreByPhase() throws Exception {
        String india = "IND";
        String uk = "GBR";
        String pakistan = "PAK";
        Integer categoryId1 = 1;
        Integer categoryId2 = 2;
        Integer categoryId3 = 3;
        Integer categoryId4 = 4;
        Integer indicatorId1_1 = 1;
        Integer indicatorId1_2 = 2;
        Integer indicatorId2_1 = 3;
        Integer indicatorId2_2 = 4;
        Integer indicatorId3_1 = 5;
        Integer indicatorId3_2 = 6;
        Integer indicatorId4_1 = 7;

        List<HealthIndicatorDto> healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).score(1).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).score(1).supportingText("sp2").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).score(3).supportingText("sp3").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_2).score(null).supportingText("sp4").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_1).score(null).supportingText("sp5").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_2).score(null).supportingText("sp6").build(),
                HealthIndicatorDto.builder().categoryId(categoryId4).indicatorId(indicatorId4_1).score(null).supportingText("sp19").build());

        setupHealthIndicatorsForCountry(india, healthIndicatorDtos);

        healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).score(1).supportingText("sp7").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).score(5).supportingText("sp8").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).score(3).supportingText("sp9").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_2).score(null).supportingText("sp10").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_1).score(null).supportingText("sp11").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_2).score(4).supportingText("sp12").build(),
                HealthIndicatorDto.builder().categoryId(categoryId4).indicatorId(indicatorId4_1).score(null).supportingText("sp20").build());

        setupHealthIndicatorsForCountry(uk, healthIndicatorDtos);

        healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).score(null).supportingText("sp13").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).score(null).supportingText("sp14").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).score(null).supportingText("sp15").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_2).score(null).supportingText("sp16").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_1).score(null).supportingText("sp17").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_2).score(null).supportingText("sp18").build(),
                HealthIndicatorDto.builder().categoryId(categoryId4).indicatorId(indicatorId4_1).score(null).supportingText("sp21").build());

        setupHealthIndicatorsForCountry(pakistan, healthIndicatorDtos);

        Response response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/global_health_indicators?phase=4");

        assertResponse(response.asString(), "global_indicators_filtered_by_phase.json");

    }

    @Test
    public void shouldGetOverAllScoreForAllCountries() throws Exception {
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
                .get("http://localhost:" + port + "/countries_health_indicator_scores");

        assertResponse(response.asString(), "countries_health_indicators.json");
    }

    @Test
    public void shouldGetFilteredOverAllScoreForAllCountriesGivenCategoryAndPhase() throws Exception {
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
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).score(3).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).score(1).supportingText("sp2").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).score(3).supportingText("sp3").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_2).score(null).supportingText("sp4").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_1).score(null).supportingText("sp5").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_2).score(null).supportingText("sp6").build());

        setupHealthIndicatorsForCountry(india, healthIndicatorDtos);

        healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).score(1).supportingText("sp7").build(),
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
                .get("http://localhost:" + port + "/countries_health_indicator_scores?categoryId="+categoryId1+"&phase=2");

        assertResponse(response.asString(), "filtered_countries_health_indicators.json");
    }

    @Test
    public void shouldGetFilteredOverAllScoreForAllCountriesGivenCategory() throws Exception {
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
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).score(3).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).score(1).supportingText("sp2").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).score(3).supportingText("sp3").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_2).score(null).supportingText("sp4").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_1).score(null).supportingText("sp5").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_2).score(null).supportingText("sp6").build());

        setupHealthIndicatorsForCountry(india, healthIndicatorDtos);

        healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).score(1).supportingText("sp7").build(),
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
                .get("http://localhost:" + port + "/countries_health_indicator_scores?categoryId="+categoryId1);

        assertResponse(response.asString(), "countries_health_indicators_filter_by_category.json");
    }

    @Test
    public void shouldGetFilteredOverAllScoreForAllCountriesGivenPhase() throws Exception {
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
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).score(3).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).score(1).supportingText("sp2").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).score(3).supportingText("sp3").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_2).score(null).supportingText("sp4").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_1).score(null).supportingText("sp5").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_2).score(null).supportingText("sp6").build());

        setupHealthIndicatorsForCountry(india, healthIndicatorDtos);

        healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).score(1).supportingText("sp7").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).score(0).supportingText("sp8").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).score(5).supportingText("sp9").build(),
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
                .get("http://localhost:" + port + "/countries_health_indicator_scores?phase=3");

        assertResponse(response.asString(), "countries_health_indicators_filter_by_phase.json");
    }

}
