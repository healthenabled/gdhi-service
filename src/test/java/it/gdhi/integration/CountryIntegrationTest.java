package it.gdhi.integration;

import io.restassured.response.Response;
import it.gdhi.Application;
import it.gdhi.dto.HealthIndicatorDto;
import it.gdhi.model.Country;
import it.gdhi.model.CountryPhase;
import it.gdhi.model.CountryResourceLink;
import it.gdhi.model.CountrySummary;
import it.gdhi.model.id.CountryResourceLinkId;
import it.gdhi.model.id.CountrySummaryId;
import it.gdhi.repository.ICountryPhaseRepository;
import it.gdhi.repository.ICountryRepository;
import it.gdhi.repository.ICountrySummaryRepository;
import it.gdhi.service.CountryHealthDataService;
import it.gdhi.service.MailerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private ICountryPhaseRepository countryPhaseRepository;

    @Autowired
    private MailerService mailerService;

    @Autowired
    CountryHealthDataService countryHealthDataService;

    private UUID INDIA_UUID = null;
    private String INDIA_ID = "IND";

    @Before
    public void setup() {
        INDIA_UUID = iCountryRepository.find(INDIA_ID).getUniqueId();
    }

    @Test
    public void shouldGetHealthIndicatorForACountry() throws Exception {
        String countryId = INDIA_ID;
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

        addCountrySummary(countryId, "India", status, alpha2Code, INDIA_UUID, "04-04-2018",new ArrayList<>());

        List<HealthIndicatorDto> healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_1).status(status).score(1).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId1).indicatorId(indicatorId1_2).status(status).score(2).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_1).status(status).score(3).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId2).indicatorId(indicatorId2_2).status(status).score(null).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_1).status(status).score(null).supportingText("sp1").build(),
                HealthIndicatorDto.builder().categoryId(categoryId3).indicatorId(indicatorId3_2).status(status).score(null).supportingText("sp1").build());

        setupHealthIndicatorsForCountry(countryId, healthIndicatorDtos);
        setUpCountryPhase(countryId, 2);

        Response response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/countries/IND/health_indicators");

        assertResponse(response.asString(), "health_indicators.json");
    }

    @Test
    public void shouldGetCountrySummary() throws Exception {
        String countryId = INDIA_ID;
        String status = "PUBLISHED";
        String alpha2code = "IN";


        CountryResourceLink countryResourceLink1 = new CountryResourceLink(new CountryResourceLinkId(countryId,
                "link1",status), new Date(), null);
        CountryResourceLink countryResourceLink2 = new CountryResourceLink(new CountryResourceLinkId(countryId,
                "link2",status), new Date(), null);

        List<CountryResourceLink> countryResourceLinks = asList(countryResourceLink1, countryResourceLink2);

        addCountrySummary(countryId, "India", status, alpha2code, INDIA_UUID, "04-04-2018" ,countryResourceLinks);

        Response response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/countries/IND/country_summary");

        assertResponse(response.asString(), "country_summary.json");
    }

    @Test
    public void shouldGetCountryDetails() throws Exception {
        String countryId = INDIA_ID;
        String status = "DRAFT";
        String alpha2code = "IN";

        CountryResourceLink countryResourceLink1 = new CountryResourceLink(new CountryResourceLinkId(countryId,
                "link1",status), new Date(), null);
        CountryResourceLink countryResourceLink2 = new CountryResourceLink(new CountryResourceLinkId(countryId,
                "link2",status), new Date(), null);
        List<CountryResourceLink> countryResourceLinks = asList(countryResourceLink1, countryResourceLink2);

        addCountrySummary(countryId, "India", status, alpha2code, INDIA_UUID, "04-04-2018", countryResourceLinks);

        List<HealthIndicatorDto> healthIndicatorDtos = asList(
                HealthIndicatorDto.builder().categoryId(1).indicatorId(1).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(1).indicatorId(2).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(2).indicatorId(3).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(2).indicatorId(4).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(3).indicatorId(5).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(3).indicatorId(6).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(3).indicatorId(7).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(3).indicatorId(8).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(4).indicatorId(9).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(4).indicatorId(10).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(4).indicatorId(11).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(4).indicatorId(12).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(5).indicatorId(13).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(5).indicatorId(14).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(6).indicatorId(15).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(6).indicatorId(16).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(7).indicatorId(17).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(7).indicatorId(18).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(7).indicatorId(19).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(4).indicatorId(20).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(4).indicatorId(21).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(4).indicatorId(22).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(4).indicatorId(23).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(4).indicatorId(24).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(4).indicatorId(25).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(4).indicatorId(26).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(7).indicatorId(27).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(7).indicatorId(28).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(7).indicatorId(29).status(status).score(1).supportingText("blah@blah.com").build(),
                HealthIndicatorDto.builder().categoryId(7).indicatorId(30).status(status).score(1).supportingText("blah@blah.com").build());


        setupHealthIndicatorsForCountry(countryId, healthIndicatorDtos);

        Response response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/countries/" + INDIA_UUID.toString());
        String responseJson = response.asString();
        assertResponse(responseJson, "country_body.json");
    }

    @Test
    public void shouldSaveCountryDetailsWhenNoDateIsProvided() throws Exception {
        addCountrySummary(INDIA_ID, "India", "NEW", "IN", UUID.randomUUID(), "04-04-2018", new ArrayList<>());

        Response response = given()
                .contentType("application/json")
                .when()
                .body(expectedResponseJson("country_body_no_date.json"))
                .post("http://localhost:" + port + "/countries/save");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void shouldSaveCountryDetailsWhenNullResourceIsProvided() throws Exception {
        addCountrySummary(INDIA_ID, "India", "NEW", "IN", UUID.randomUUID(), "04-04-2018", new ArrayList<>());
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
        addCountrySummary(INDIA_ID, "India", "NEW", "IN", UUID.randomUUID(), "04-04-2018", new ArrayList<>());
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
                .get("http://localhost:" + port + "/countries/" + INDIA_UUID.toString());
        String responseJson = response.asString();
        assertResponse(responseJson, "country_body.json");

        response = given()
                .contentType("application/json")
                .when()
                .body(expectedResponseJson("country_body_edit.json"))
                .post("http://localhost:" + port + "/countries/save");

        assertEquals(200, response.getStatusCode());

        response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/countries/" + INDIA_UUID.toString());
        responseJson = response.asString();
        assertResponse(responseJson, "country_body_edit.json");
    }

    @Test
    public void shouldSubmitCountryDetails() throws Exception {
        addCountrySummary(INDIA_ID, "India", "NEW", "IN", UUID.randomUUID(),  "2018-04-04", new ArrayList<>());

        Response response = given()
                .contentType("application/json")
                .when()
                .body(expectedResponseJson("country_body_edit.json"))
                .post("http://localhost:" + port + "/countries/submit");

        assertEquals(201, response.getStatusCode());

        response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/countries/" + INDIA_UUID.toString());
        String responseJson = response.asString();
        assertResponse(responseJson, "country_body_review_pending.json");
    }

    @Test
    public void shouldDeleteCountryData() throws Exception {
        String countryId = INDIA_ID;
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

        addCountrySummary(countryId, "India", status, alpha2code, INDIA_UUID, "2018-04-04", countryResourceLinks);

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
                .delete("http://localhost:" + port + "/countries/" + INDIA_UUID.toString() + "/delete");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void shouldGetAllCountrySummaries() throws Exception {
        UUID indiaUUID = iCountryRepository.find(INDIA_ID).getUniqueId();;
        UUID australiaUUID = iCountryRepository.find("AUS").getUniqueId();;

        addCountrySummary("AUS", "AUSTRALIA", "NEW", "AU", australiaUUID, "2018-04-04" , emptyList());
        addCountrySummary("AUS", "AUSTRALIA", "DRAFT", "AU", australiaUUID, "2018-04-04" , emptyList());
        addCountrySummary("AUS", "AUSTRALIA", "REVIEW_PENDING", "AU", australiaUUID, "2018-04-04" , emptyList());
        addCountrySummary("AUS", "AUSTRALIA", "PUBLISHED", "AU", australiaUUID, "2018-04-04" , emptyList());
        addCountrySummary(INDIA_ID, "INDIA", "NEW", "IN", indiaUUID, "2018-04-04" , emptyList());

        Response response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/countries/country_status_summaries");

        String expectedJson = "{\n" +
                "  \"NEW\": [{\n" +
                "    \"countryName\": \"India\",\n" +
                "    \"countryUUID\": \""+ indiaUUID.toString() +"\",\n" +
                "    \"status\": \"NEW\",\n" +
                "    \"contactName\": \"contactName\",\n" +
                "    \"contactEmail\": \"email\"\n" +
                "  }, {\n" +
                "    \"countryName\": \"Australia\",\n" +
                "    \"countryUUID\": \""+ australiaUUID.toString() +"\",\n" +
                "    \"status\": \"NEW\",\n" +
                "    \"contactName\": \"contactName\",\n" +
                "    \"contactEmail\": \"email\"\n" +
                "  }],\n" +
                "  \"PUBLISHED\": [{\n" +
                "    \"countryName\": \"Australia\",\n" +
                "    \"countryUUID\": \""+ australiaUUID.toString() +"\",\n" +
                "    \"status\": \"PUBLISHED\",\n" +
                "    \"contactName\": \"contactName\",\n" +
                "    \"contactEmail\": \"email\"\n" +
                "  }],\n" +
                "  \"DRAFT\": [{\n" +
                "    \"countryName\": \"Australia\",\n" +
                "    \"countryUUID\": \""+ australiaUUID.toString() +"\",\n" +
                "    \"status\": \"DRAFT\",\n" +
                "    \"contactName\": \"contactName\",\n" +
                "    \"contactEmail\": \"email\"\n" +
                "  }],\n" +
                "  \"REVIEW_PENDING\": [{\n" +
                "    \"countryName\": \"Australia\",\n" +
                "    \"countryUUID\": \""+ australiaUUID.toString() +"\",\n" +
                "    \"status\": \"REVIEW_PENDING\",\n" +
                "    \"contactName\": \"contactName\",\n" +
                "    \"contactEmail\": \"email\"\n" +
                "  }]\n" +
                "}";
        assertStringResponse(response.asString(), expectedJson);
    }

    @Test
    public void shouldGetGlobalAvgBenchmarkForGivenCountry() throws Exception {
        Integer categoryId1 = 1;
        Integer indicatorId1 = 1;

        addCountrySummary(INDIA_ID, "INDIA", "PUBLISHED", "IN", INDIA_UUID, "2018-04-04" , emptyList());
        addCountrySummary("PAK", "PAKISTAN", "PUBLISHED", "PK", UUID.randomUUID(), "2018-04-04" , emptyList());
        addCountrySummary("ARE", "UNITED ARAB EMIRATES", "PUBLISHED", "UE", UUID.randomUUID(), "2018-04-04" , emptyList());
        addCountrySummary("LKA", "SRI LANKA", "DRAFT", "SL", UUID.randomUUID(), "2018-04-04" , emptyList());
        addCountrySummary(INDIA_ID, "INDIA", "NEW", "IN", UUID.randomUUID(), "2018-04-04" , emptyList());


        List<HealthIndicatorDto> healthIndicatorDto = setUpHealthIndicatorDto(categoryId1, indicatorId1, "PUBLISHED", 1);
        setupHealthIndicatorsForCountry(INDIA_ID, healthIndicatorDto);

        List<HealthIndicatorDto> healthIndicatorDto1 = setUpHealthIndicatorDto(categoryId1, indicatorId1, "PUBLISHED", 2);
        setupHealthIndicatorsForCountry("PAK", healthIndicatorDto1);

        List<HealthIndicatorDto> healthIndicatorDto2 = setUpHealthIndicatorDto(categoryId1, indicatorId1, "PUBLISHED", -1);
        setupHealthIndicatorsForCountry("ARE", healthIndicatorDto2);

        List<HealthIndicatorDto> healthIndicatorDto3 = setUpHealthIndicatorDto(categoryId1, indicatorId1, "DRAFT", 5);
        setupHealthIndicatorsForCountry("LKA", healthIndicatorDto3);

        setUpCountryPhase(INDIA_ID, 1);
        setUpCountryPhase("PAK", 2);
        setUpCountryPhase("ARE", null);
        setUpCountryPhase("LKA", 5);

        Response response = given()
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/countries/IND/benchmark/-1");

        assertEquals(200, response.getStatusCode());

        assertEquals(response.asString(), "{\"1\":{\"benchmarkScore\":2,\"benchmarkValue\":\"Below\"}}");
    }

    private void setUpCountryPhase(String countryId, Integer countryPhaseValue) {
        CountryPhase countryPhase = CountryPhase.builder().countryId(countryId).countryOverallPhase(countryPhaseValue).build();
        countryPhaseRepository.save(countryPhase);
    }

    private List<HealthIndicatorDto> setUpHealthIndicatorDto(Integer indicatorId, Integer categoryId, String status, Integer score) {
        return asList(HealthIndicatorDto.builder()
                                        .categoryId(categoryId)
                                        .indicatorId(indicatorId)
                                        .status(status)
                                        .score(score)
                                        .supportingText("Some text")
                                        .build());
    }

    private void addCountrySummary(String countryId, String countryName, String status, String alpha2Code, UUID
            countryUUID, String collectedDate,
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
    }
}
