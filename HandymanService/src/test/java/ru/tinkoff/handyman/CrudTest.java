package ru.tinkoff.handyman;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.config.JsonPathConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.tinkoff.handyman.dto.request.CreatingHandymanDto;
import ru.tinkoff.handyman.dto.request.ExtendedUserDto;
import ru.tinkoff.handyman.dto.request.LocationDto;
import ru.tinkoff.handyman.dto.request.UserDto;
import ru.tinkoff.handyman.model.Handyman;
import ru.tinkoff.handyman.repository.HandymanRepository;

import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class CrudTest {

    @LocalServerPort
    private Integer port;

    public ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired
    private HandymanRepository handymanRepository;

    private static WireMockServer landscape;

    @Container
    public static MongoDBContainer mongo = new MongoDBContainer("mongo");

    @BeforeAll
    public static void up() {
        landscape = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
        landscape.start();

    }

    @BeforeEach
    public void setUp() {
        handymanRepository.deleteAll();
        RestAssured.baseURI = "http://localhost:" + port;
        JsonConfig jsonConfig = JsonConfig.jsonConfig()
                .numberReturnType(JsonPathConfig.NumberReturnType.DOUBLE);

        RestAssured.config = RestAssured.config()
                .jsonConfig(jsonConfig);
        var handyman = Handyman.builder()
                .id(UUID.randomUUID())
                .latitude(1.0)
                .longitude(2.0)
                .build();
        handymanRepository.insert(handyman);
    }

    @DynamicPropertySource
    public static void appProperties(DynamicPropertyRegistry registry) {
        mongo.start();
        registry.add("spring.data.mongodb.uri", mongo::getReplicaSetUrl);
        registry.add("landscape.url", landscape::baseUrl);
    }

    @Test
    public void testFromLocalMongoRepository() {
        var uuid = UUID.randomUUID();

        var handyman = Handyman.builder()
                .id(uuid)
                .longitude(1.9)
                .latitude(2.3)
                .build();

        handymanRepository.insert(handyman);

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/handyman/getFromRepo/" + uuid)
        .then()
                .statusCode(200)
                .body("id", equalTo(handyman.getId().toString()));
    }

    @Test
    public void testCreateHandyman() throws JsonProcessingException {
        var userDtoResponse = UserDto.builder()
                .id(UUID.randomUUID())
                .login("NewUser")
                .userTypeId(1)
                .build();

        var landscapeDtoResponse = LocationDto.builder()
                .latitude(1.0)
                .longitude(5.0)
                .build();

        var handymanDtoRequest = CreatingHandymanDto.builder()
                .login(userDtoResponse.getLogin())
                .email("newemail@mail.ru")
                .phone("+777766677777")
                .longitude(landscapeDtoResponse.getLongitude())
                .latitude(landscapeDtoResponse.getLatitude())
                .build();

        landscape.stubFor(
                WireMock.post("/user/create")
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(jsonMapper.writeValueAsString(userDtoResponse))
                        )
        );
        landscape.stubFor(
                WireMock.post(urlMatching("/location/create/.*"))
                        .willReturn(aResponse()
                                .withStatus(200)
                        )
        );

        given()
                .contentType(ContentType.JSON)
                .body(handymanDtoRequest)
        .when()
                .post("/handyman/create")
        .then()
                .statusCode(200)
                .body("id", equalTo(userDtoResponse.getId().toString()));

        assertThat(handymanRepository.count()).isEqualTo(2);
    }

    @Test
    public void testGetHandymanById() throws JsonProcessingException {
        var handyman = handymanRepository.findAll().get(0);
        var handymanRequest = ExtendedUserDto.builder()
                .id(handyman.getId())
                .login("NewLogin")
                .email("newemail@gmail.com")
                .phone("+77777777777")
                .userTypeId(1)
                .build();

        landscape.stubFor(
                WireMock.get("/user/getExtendedUserById/" + handyman.getId())
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(jsonMapper.writeValueAsString(handymanRequest))
                        )
        );

        given()
        .when()
                .get("/handyman/getById/" + handyman.getId())
        .then()
                .statusCode(200)
                .body("id", equalTo(handyman.getId().toString()))
                .body("login", equalTo(handymanRequest.getLogin()))
                .body("email", equalTo(handymanRequest.getEmail()))
                .body("phone", equalTo(handymanRequest.getPhone()))
                .body("latitude", equalTo(handyman.getLatitude()))
                .body("longitude", equalTo(handyman.getLongitude()));

    }

    @Test
    public void testDeleteById() throws JsonProcessingException {
        var handyman = handymanRepository.findAll().get(0);
        var handymanRequest = ExtendedUserDto.builder()
                .id(handyman.getId())
                .login("NewLogin")
                .email("newemail@gmail.com")
                .phone("+77777777777")
                .userTypeId(1)
                .build();

        landscape.stubFor(
                WireMock.get("/user/getExtendedUserById/" + handyman.getId())
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(jsonMapper.writeValueAsString(handymanRequest))
                        )
        );

        given()
        .when()
                .delete("handyman/deleteById/" + handyman.getId())
        .then()
                .statusCode(200)
                .body("id", equalTo(handyman.getId().toString()))
                .body("login", equalTo(handymanRequest.getLogin()))
                .body("email", equalTo(handymanRequest.getEmail()))
                .body("phone", equalTo(handymanRequest.getPhone()))
                .body("latitude", equalTo(handyman.getLatitude()))
                .body("longitude", equalTo(handyman.getLongitude()));

        assertThat(handymanRepository.count()).isEqualTo(0);
    }



    @AfterAll
    public static void closeDown() {
        landscape.stop();
    }
}

