package it.gdhi.integration;

import io.restassured.response.Response;
import it.gdhi.Application;
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

import static io.restassured.RestAssured.given;
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


        HealthIndicatorId healthIndicatorId1 = new HealthIndicatorId(countryId,categoryId1,indicatorId1_1);
        HealthIndicator healthIndicatorSetupData1 = new HealthIndicator(healthIndicatorId1, 1, "sp1");
        healthIndicatorRepository.save(healthIndicatorSetupData1);

        HealthIndicatorId healthIndicatorId2 = new HealthIndicatorId(countryId,categoryId1,indicatorId1_2);
        HealthIndicator healthIndicatorSetupData2 = new HealthIndicator(healthIndicatorId2, 2, "sp1");
        healthIndicatorRepository.save(healthIndicatorSetupData2);

        HealthIndicatorId healthIndicatorId3 = new HealthIndicatorId(countryId,categoryId2,indicatorId2_1);
        HealthIndicator healthIndicatorSetupData3 = new HealthIndicator(healthIndicatorId3, 3, "sp1");
        healthIndicatorRepository.save(healthIndicatorSetupData3);

        HealthIndicatorId healthIndicatorId4 = new HealthIndicatorId(countryId,categoryId2, indicatorId2_2);
        HealthIndicator healthIndicatorSetupData4 = new HealthIndicator(healthIndicatorId4, null, "sp1");
        healthIndicatorRepository.save(healthIndicatorSetupData4);

        HealthIndicatorId healthIndicatorId5 = new HealthIndicatorId(countryId,categoryId3,indicatorId3_1);
        HealthIndicator healthIndicatorSetupData5 = new HealthIndicator(healthIndicatorId5, null, "sp1");
        healthIndicatorRepository.save(healthIndicatorSetupData5);

        HealthIndicatorId healthIndicatorId6 = new HealthIndicatorId(countryId,categoryId3,indicatorId3_2);
        HealthIndicator healthIndicatorSetupData6 = new HealthIndicator(healthIndicatorId6, null, "sp1");
        healthIndicatorRepository.save(healthIndicatorSetupData6);


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
}
