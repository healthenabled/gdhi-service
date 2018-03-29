package it.gdhi.integration;

import io.restassured.response.Response;
import it.gdhi.Application;
import it.gdhi.dto.HealthIndicatorDto;
import it.gdhi.model.Country;
import it.gdhi.model.CountryResourceLink;
import it.gdhi.model.CountrySummary;
import it.gdhi.model.id.CountryResourceLinkId;
import it.gdhi.model.id.CountrySummaryId;
import it.gdhi.repository.ICountrySummaryRepository;
import it.gdhi.service.MailerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@ActiveProfiles("test")
public class CountryIntegrationTest extends BaseIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ICountrySummaryRepository countrySummaryRepository;
    @Autowired
    private MailerService mailerService;

    private void addCountrySummary(String countryId, String countryName, String status, String alpha2Code, List<CountryResourceLink> countryResourceLinks)  {
        CountrySummary countrySummary = CountrySummary.builder()
                .countrySummaryId(new CountrySummaryId(countryId, status))
                .summary("summary")
                .country(new Country(countryId, countryName, UUID.randomUUID(), alpha2Code))
                .contactName("contactName")
                .contactDesignation("contactDesignation")
                .contactOrganization("contactOrganization")
                .contactEmail("email")
                .dataFeederName("feeder name")
                .dataFeederRole("feeder role")
                .dataFeederEmail("email")
                .dataCollectorName("coll name")
                .dataCollectorRole("coll role")
                .dataFeederRole("coll role")
                .dataCollectorEmail("coll email")
                .countryResourceLinks(countryResourceLinks)
                .build();
        countrySummaryRepository.save(countrySummary);
    }

    @Test
    public void shouldGetHealthIndicatorForACountry() throws Exception {
        String countryId = "IND";
        String status = "PUBLISHED";
        String alpha2Code = "IN";

        Integer categoryId1 = 1;
        Integer categoryId2 = 2;
        Integer categoryId3 = 3;
        Integer indicatorId1_1 = 1;
        Integer indicatorId1_2 = 2;
        Integer indicatorId2_1 = 3;
        Integer indicatorId2_2 = 4;
        Integer indicatorId3_1 = 5;
        Integer indicatorId3_2 = 6;

        addCountrySummary(countryId, "India", status, alpha2Code, new ArrayList<>());

        List<HealthIndicatorDto> healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).status(status).score(1).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).status(status).score(2).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).status(status).score(3).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_2).status(status).score(null).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_1).status(status).score(null).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_2).status(status).score(null).supportingText("sp1").build());

        setupHealthIndicatorsForCountry(countryId, healthIndicatorDtos);

        Response response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/countries/IND/health_indicators");

        assertResponse(response.asString(), "health_indicators.json");
    }


    @Test
    public void shouldGetCountrySummary() throws Exception {
        String countryId = "IND";
        String status = "PUBLISHED";
        String alpha2code = "IN";

        CountryResourceLink countryResourceLink1 = new CountryResourceLink(new CountryResourceLinkId(countryId, "link1",status));
        CountryResourceLink countryResourceLink2 = new CountryResourceLink(new CountryResourceLinkId(countryId, "link2",status));

        List<CountryResourceLink> countryResourceLinks = asList(countryResourceLink1, countryResourceLink2);

        addCountrySummary(countryId, "India", status, alpha2code, countryResourceLinks);

        Response response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/countries/IND/country_summary");

        assertResponse(response.asString(), "country_summary.json");
    }

    @Test
    public void shouldGetCountryDetails() throws Exception {
        String countryId = "IND";
        String status = "DRAFT";
        String alpha2code = "IN";
        CountryResourceLink countryResourceLink1 = new CountryResourceLink(new CountryResourceLinkId(countryId, "link1",status));
        CountryResourceLink countryResourceLink2 = new CountryResourceLink(new CountryResourceLinkId(countryId, "link2",status));
        Integer categoryId1 = 1;
        Integer categoryId2 = 2;
        Integer categoryId3 = 3;
        Integer indicatorId1_1 = 1;
        Integer indicatorId1_2 = 2;
        Integer indicatorId2_1 = 3;
        Integer indicatorId2_2 = 4;
        Integer indicatorId3_1 = 5;
        Integer indicatorId3_2 = 6;
        List<CountryResourceLink> countryResourceLinks = asList(countryResourceLink1, countryResourceLink2);

        addCountrySummary(countryId, "India", status, alpha2code, countryResourceLinks);

        List<HealthIndicatorDto> healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).status(status).score(1).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).status(status).score(2).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).status(status).score(3).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_2).status(status).score(null).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_1).status(status).score(null).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_2).status(status).score(null).supportingText("sp1").build());

        setupHealthIndicatorsForCountry(countryId, healthIndicatorDtos);

        Response response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/countries/IND");

        assertResponse(response.asString(), "country_body.json");
    }

    @Test
    public void shouldSaveAndEditCountryDetails() throws Exception {
        mailerService = mock(MailerService.class);
        doNothing().when(mailerService).send(any(Country.class), anyString(), anyString(), anyString());

        Response response = given()
                .contentType("application/json")
                .when()
                .body(expectedResponseJson("country_body.json"))
                .post("http://localhost:" + port + "/countries/save");

        assertEquals(200, response.getStatusCode());

        response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/countries/IND");

        assertResponse(response.asString(), "country_body.json");

        response = given()
                .contentType("application/json")
                .when()
                .body(expectedResponseJson("country_body_edit.json"))
                .post("http://localhost:" + port + "/countries/save");

        assertEquals(200, response.getStatusCode());

        response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/countries/IND");

        assertResponse(response.asString(), "country_body_edit.json");
    }
}
