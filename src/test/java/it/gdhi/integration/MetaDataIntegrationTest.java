package it.gdhi.integration;

import io.restassured.response.Response;
import it.gdhi.Application;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static it.gdhi.utils.LanguageCode.USER_LANGUAGE;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@ActiveProfiles("test")
public class MetaDataIntegrationTest extends BaseIntegrationTest{

    @LocalServerPort
    private int port;

    @Test
    public void shouldReturnAllPhases() throws IOException {

        Response response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/phases");

        assertEquals(200, response.getStatusCode());
        assertResponse(response.asString(), "phases.json");
    }

    @Test
    @Ignore("Only for local")
    public void shouldReturnHealthIndicatorOptions() throws IOException {
        Response response = given()
                .contentType("application/json")
                .header(USER_LANGUAGE, "en")
                .when()
                .get("http://localhost:" + port + "/health_indicator_options");

        assertEquals(200, response.getStatusCode());
        assertResponse(response.asString(), "health_indicator_options_en.json");
    }

    @Test
    @Ignore("Only for local")
    public void shouldReturnHealthIndicatorOptionsInFrench() throws IOException {
        Response response = given()
                .contentType("application/json")
                .header(USER_LANGUAGE, "fr")
                .when()
                .get("http://localhost:" + port + "/health_indicator_options");

        assertEquals(200, response.getStatusCode());
        assertResponse(response.asString(), "health_indicator_options_fr.json");
    }

    @Override
    void assertResponse(String responseJSON, String expectedJsonFileName) throws IOException {
        String expectedJSON = expectedResponseJson(expectedJsonFileName);
        ArrayList actualList = getMapper().readValue(responseJSON, ArrayList.class);
        ArrayList expectedList = getMapper().readValue(expectedJSON, ArrayList.class);
        assertEquals(expectedList, actualList);
    }
}
