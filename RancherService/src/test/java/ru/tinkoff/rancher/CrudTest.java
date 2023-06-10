package ru.tinkoff.rancher;

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
import ru.tinkoff.rancher.dto.request.CreatingRancherDto;
import ru.tinkoff.rancher.dto.request.ExtendedUserDto;
import ru.tinkoff.rancher.dto.request.LocationDto;
import ru.tinkoff.rancher.dto.request.UserDto;
import ru.tinkoff.rancher.model.Rancher;
import ru.tinkoff.rancher.repository.RancherRepository;

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
    private RancherRepository rancherRepository;

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
        rancherRepository.deleteAll();
        RestAssured.baseURI = "http://localhost:" + port;
        JsonConfig jsonConfig = JsonConfig.jsonConfig()
                .numberReturnType(JsonPathConfig.NumberReturnType.DOUBLE);

        RestAssured.config = RestAssured.config()
                .jsonConfig(jsonConfig);
        var rancher = Rancher.builder()
                .id(UUID.randomUUID())
                .login("NewLogin")
                .latitude(1.0)
                .longitude(2.0)
                .area(12.0)
                .build();
        rancherRepository.insert(rancher);
    }

    @DynamicPropertySource
    public static void appProperties(DynamicPropertyRegistry registry) {
        mongo.start();
        registry.add("spring.data.mongodb.uri", mongo::getReplicaSetUrl);
        registry.add("landscape.url", landscape::baseUrl);
    }

    @Test
    public void testFromLocalMongoRepository() {
        var rancher = rancherRepository.findAll().get(0);

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/rancher/getFromRepo/" + rancher.getId())
        .then()
                .statusCode(200)
                .body("id", equalTo(rancher.getId().toString()));
    }

    @Test
    public void testCreateRancher() throws JsonProcessingException {
        var userDtoResponse = UserDto.builder()
                .id(UUID.randomUUID())
                .login("NewUser")
                .userTypeId(2)
                .build();

        var landscapeDtoResponse = LocationDto.builder()
                .latitude(1.0)
                .longitude(5.0)
                .build();

        var rancherDtoRequest = CreatingRancherDto.builder()
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
                .body(rancherDtoRequest)
        .when()
                .post("/rancher/create")
        .then()
                .statusCode(200)
                .body("id", equalTo(userDtoResponse.getId().toString()));

        assertThat(rancherRepository.count()).isEqualTo(2);
    }

    @Test
    public void testGetRancherById() throws JsonProcessingException {
        var rancher = rancherRepository.findAll().get(0);
        var rancherRequest = ExtendedUserDto.builder()
                .id(rancher.getId())
                .login("NewLogin")
                .email("newemail@gmail.com")
                .phone("+77777777777")
                .userTypeId(1)
                .build();

        landscape.stubFor(
                WireMock.get("/user/getExtendedUserById/" + rancher.getId())
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(jsonMapper.writeValueAsString(rancherRequest))
                        )
        );

        given()
                .when()
                .get("/rancher/getById/" + rancher.getId())
                .then()
                .statusCode(200)
                .body("id", equalTo(rancher.getId().toString()))
                .body("login", equalTo(rancherRequest.getLogin()))
                .body("email", equalTo(rancherRequest.getEmail()))
                .body("phone", equalTo(rancherRequest.getPhone()))
                .body("latitude", equalTo(rancher.getLatitude()))
                .body("area", equalTo(rancher.getArea()))
                .body("longitude", equalTo(rancher.getLongitude()));

    }

    @Test
    public void testDeleteById() throws JsonProcessingException {
        var rancher = rancherRepository.findAll().get(0);
        var rancherRequestDto = ExtendedUserDto.builder()
                .id(rancher.getId())
                .login("NewLogin")
                .email("newemail@gmail.com")
                .phone("+77777777777")
                .userTypeId(1)
                .build();

        landscape.stubFor(
                WireMock.get("/user/getExtendedUserById/" + rancher.getId())
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(jsonMapper.writeValueAsString(rancherRequestDto))
                        )
        );

        given()
        .when()
                .delete("/rancher/deleteById/" + rancher.getId())
        .then()
                .statusCode(200)
                .body("id", equalTo(rancher.getId().toString()))
                .body("login", equalTo(rancherRequestDto.getLogin()))
                .body("email", equalTo(rancherRequestDto.getEmail()))
                .body("phone", equalTo(rancherRequestDto.getPhone()))
                .body("latitude", equalTo(rancher.getLatitude()))
                .body("area", equalTo(rancher.getArea()))
                .body("longitude", equalTo(rancher.getLongitude()));

        assertThat(rancherRepository.count()).isEqualTo(0);
    }



    @AfterAll
    public static void closeDown() {
        landscape.stop();
    }
}
