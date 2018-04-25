package it.gdhi.integration;

import io.restassured.response.Response;
import it.gdhi.Application;
import it.gdhi.dto.HealthIndicatorDto;
import it.gdhi.model.Country;
import it.gdhi.model.CountryResourceLink;
import it.gdhi.model.CountrySummary;
import it.gdhi.model.id.CountryResourceLinkId;
import it.gdhi.model.id.CountrySummaryId;
import it.gdhi.repository.ICountryRepository;
import it.gdhi.repository.ICountrySummaryRepository;
import it.gdhi.service.MailerService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.*;

import static io.restassured.RestAssured.given;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@ActiveProfiles("test")
public class CountryIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ICountryRepository iCountryRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private ICountrySummaryRepository countrySummaryRepository;

    @Autowired
    private MailerService mailerService;

    private UUID COUNTRY_UUID = null;

    private void addCountrySummary(String countryId, String countryName, String status, String alpha2Code, UUID countryUUID, String collectedDate,
                                   List<CountryResourceLink> countryResourceLinks) throws Exception {
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
        Date date = fmt.parse(collectedDate);
        CountrySummary countrySummary = CountrySummary.builder()
                .countrySummaryId(new CountrySummaryId(countryId, status))
                .summary("summary")
                .country(new Country(countryId, countryName, countryUUID, alpha2Code))
                .contactName("contactName")
                .contactDesignation("contactDesignation")
                .contactOrganization("contactOrganization")
                .contactEmail("email")
                .dataFeederName("feeder name")
                .dataFeederRole("feeder role")
                .dataFeederEmail("email")
                .dataApproverName("coll name")
                .dataApproverRole("coll role")
                .dataFeederRole("coll role")
                .dataApproverEmail("coll email")
                .collectedDate(date)
                .countryResourceLinks(countryResourceLinks)
                .build();
        countrySummaryRepository.save(countrySummary);

        COUNTRY_UUID = iCountryRepository.find("IND").getUniqueId();
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

        addCountrySummary(countryId, "India", status, alpha2Code, COUNTRY_UUID, "04-04-2018",new ArrayList<>());

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


        CountryResourceLink countryResourceLink1 = new CountryResourceLink(new CountryResourceLinkId(countryId,
                "link1",status), new Date(), null);
        CountryResourceLink countryResourceLink2 = new CountryResourceLink(new CountryResourceLinkId(countryId,
                "link2",status), new Date(), null);

        List<CountryResourceLink> countryResourceLinks = asList(countryResourceLink1, countryResourceLink2);

        addCountrySummary(countryId, "India", status, alpha2code, COUNTRY_UUID, "04-04-2018" ,countryResourceLinks);

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

        CountryResourceLink countryResourceLink1 = new CountryResourceLink(new CountryResourceLinkId(countryId,
                "link1",status), new Date(), null);
        CountryResourceLink countryResourceLink2 = new CountryResourceLink(new CountryResourceLinkId(countryId,
                "link2",status), new Date(), null);
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

        addCountrySummary(countryId, "India", status, alpha2code, COUNTRY_UUID, "04-04-2018", countryResourceLinks);

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
                .get("http://localhost:" + port + "/countries/" + COUNTRY_UUID.toString());

        assertResponse(response.asString(), "country_body.json");
    }

    @Test
    public void shouldSaveCountryDetailsWhenNoDateIsProvided() throws Exception {
        addCountrySummary("IND", "India", "NEW", "IN", UUID.randomUUID(), "04-04-2018", new ArrayList<>());
        mailerService = mock(MailerService.class);
        doNothing().when(mailerService).send(any(Country.class), anyString(), anyString(), anyString());

        Response response = given()
                .contentType("application/json")
                .when()
                .body(expectedResponseJson("country_body_no_date.json"))
                .post("http://localhost:" + port + "/countries/save");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void shouldSaveCountryDetailsWhenNullResourceIsProvided() throws Exception {
        addCountrySummary("IND", "India", "NEW", "IN", UUID.randomUUID(), "04-04-2018", new ArrayList<>());
        mailerService = mock(MailerService.class);
        doNothing().when(mailerService).send(any(Country.class), anyString(), anyString(), anyString());

        Response response = given()
                .contentType("application/json")
                .when()
                .body(expectedResponseJson("country_body_null_resource.json"))
                .post("http://localhost:" + port + "/countries/save");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void shouldSaveAndEditCountryDetails() throws Exception {
        addCountrySummary("IND", "India", "NEW", "IN", UUID.randomUUID(), "04-04-2018", new ArrayList<>());
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
                .get("http://localhost:" + port + "/countries/" + COUNTRY_UUID.toString());

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
                .get("http://localhost:" + port + "/countries/" + COUNTRY_UUID.toString());

        assertResponse(response.asString(), "country_body_edit.json");
    }

    @Test
    public void shouldSubmitCountryDetails() throws Exception {
        addCountrySummary("IND", "India", "NEW", "IN", UUID.randomUUID(),  "2018-04-04", new ArrayList<>());
        mailerService = mock(MailerService.class);
        doNothing().when(mailerService).send(any(Country.class), anyString(), anyString(), anyString());

        Response response = given()
                .contentType("application/json")
                .when()
                .body(expectedResponseJson("country_body_edit.json"))
                .post("http://localhost:" + port + "/countries/submit");

        assertEquals(200, response.getStatusCode());

        response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/countries/" + COUNTRY_UUID.toString());

        assertResponse(response.asString(), "country_body_review_pending.json");
    }

    @Test
    public void shouldDeleteCountryData() throws Exception {
        String countryId = "IND";
        String status = "REVIEW_PENDING";
        String alpha2code = "IN";

        CountryResourceLink countryResourceLink1 = new CountryResourceLink(new CountryResourceLinkId(countryId,
                "link1",status), new Date(), null);
        CountryResourceLink countryResourceLink2 = new CountryResourceLink(new CountryResourceLinkId(countryId,
                "link2",status), new Date(), null);
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

        addCountrySummary(countryId, "India", status, alpha2code, COUNTRY_UUID, "2018-04-04", countryResourceLinks);

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
                .delete("http://localhost:" + port + "/countries/" + COUNTRY_UUID.toString() + "/delete");

        assertEquals(200, response.getStatusCode());
    }

    //TODO: rewrite this test
    @Ignore
    @Test
    public void shouldGetAllCountrySummary() throws Exception {

        addCountrySummary("AUS", "AUSTRALIA", "NEW", "AU", COUNTRY_UUID, "2018-04-04" , emptyList());
        addCountrySummary("AUS", "AUSTRALIA", "DRAFT", "AU", COUNTRY_UUID, "2018-04-04" , emptyList());
        addCountrySummary("AUS", "AUSTRALIA", "REVIEW_PENDING", "AU", COUNTRY_UUID, "2018-04-04" , emptyList());
        addCountrySummary("AUS", "AUSTRALIA", "PUBLISHED", "AU", COUNTRY_UUID, "2018-04-04" , emptyList());
        addCountrySummary("IND", "INDIA", "NEW", "IN", COUNTRY_UUID, "2018-04-04" , emptyList());

        Response response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/admin/view_form_details");


        assertResponse(response.asString(), "admin_view_form_details.json");
    }

    @Test
    public void shouldGetGlobalAvgBenchmarkForGivenCountry() throws Exception {
        UUID indiaUUID = iCountryRepository.find("IND").getUniqueId();

        Integer categoryId1 = 1;
        Integer indicatorId1 = 1;

        addCountrySummary("IND", "INDIA", "PUBLISHED", "IN", indiaUUID, "2018-04-04" , emptyList());
        addCountrySummary("PAK", "PAKISTAN", "PUBLISHED", "PK", UUID.randomUUID(), "2018-04-04" , emptyList());
        addCountrySummary("ARE", "UNITED ARAB EMIRATES", "PUBLISHED", "UE", UUID.randomUUID(), "2018-04-04" , emptyList());
        addCountrySummary("LKA", "SRI LANKA", "DRAFT", "SL", UUID.randomUUID(), "2018-04-04" , emptyList());
        addCountrySummary("IND", "INDIA", "NEW", "IN", UUID.randomUUID(), "2018-04-04" , emptyList());


        List<HealthIndicatorDto> healthIndicatorDto = setUpHealthIndicatorDto(categoryId1, indicatorId1, "PUBLISHED", 1);
        setupHealthIndicatorsForCountry("IND", healthIndicatorDto);

        List<HealthIndicatorDto> healthIndicatorDto1 = setUpHealthIndicatorDto(categoryId1, indicatorId1, "PUBLISHED", 2);
        setupHealthIndicatorsForCountry("PAK", healthIndicatorDto1);

        List<HealthIndicatorDto> healthIndicatorDto2 = setUpHealthIndicatorDto(categoryId1, indicatorId1, "PUBLISHED", -1);
        setupHealthIndicatorsForCountry("ARE", healthIndicatorDto2);

        List<HealthIndicatorDto> healthIndicatorDto3 = setUpHealthIndicatorDto(categoryId1, indicatorId1, "DRAFT", 5);
        setupHealthIndicatorsForCountry("LKA", healthIndicatorDto3);

        Response response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/countries/IND/benchmark/global");

        assertEquals(200, response.getStatusCode());

        assertEquals(response.asString(), "{\"1\":{\"benchmarkScore\":2,\"benchmarkValue\":\"Below\"}}");
    }

    private List<HealthIndicatorDto> setUpHealthIndicatorDto(Integer indicatorId, Integer categoryId, String status, Integer score) {
        return asList(
                HealthIndicatorDto.builder().categoryId(categoryId).indicatorId(indicatorId).status(status).score(score).supportingText("Some text").build());
    }

}
