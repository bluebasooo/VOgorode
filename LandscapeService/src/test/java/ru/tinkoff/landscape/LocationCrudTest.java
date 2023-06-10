package ru.tinkoff.landscape;

import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.config.JsonPathConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.tinkoff.landscape.dto.LocationDto;
import ru.tinkoff.landscape.model.Location;
import ru.tinkoff.landscape.model.User;
import ru.tinkoff.landscape.repository.LocationRepository;
import ru.tinkoff.landscape.repository.UserRepository;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class LocationCrudTest {

    @LocalServerPort
    private Integer port;

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @DynamicPropertySource
    public static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        JsonConfig jsonConfig = JsonConfig.jsonConfig()
                .numberReturnType(JsonPathConfig.NumberReturnType.DOUBLE);

        RestAssured.config = RestAssured.config()
                .jsonConfig(jsonConfig);

        userRepository.deleteAll();
        userRepository.save(
                User.builder()
                        .login("BRUH")
                        .email("BRUH@gmail.com")
                        .phone("+123456789")
                        .userTypeId(1)
                        .creates(LocalDate.of(2019,1,1))
                        .updates(LocalDate.of(2020, 2,11))
                        .build()
        );
        userRepository.save(
                User.builder()
                        .login("BBB2")
                        .email("BBB2@gmail.com")
                        .phone("+77777777777")
                        .userTypeId(2)
                        .creates(LocalDate.of(2033, 2, 11))
                        .updates(LocalDate.of(2033, 3, 22))
                        .build()
        );
        var userIds = userRepository.findAll().stream().map(User::getId).toList();
        locationRepository.save(
                Location.builder()
                        .userId(userIds.get(0))
                        .longitude(1.0)
                        .latitude(2.0)
                        .build()
        );

    }

    @Test
    public void testGetLocationByUserId() {
        var userId = userRepository.findAll().stream().map(User::getId).toList().get(0);

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/location/getByUserId/" + userId)
        .then()
                .statusCode(200)
                .body("latitude", equalTo(2.0))
                .body("longitude", equalTo(1.0));

    }

    @Test
    public void testCreateLocationByUserId() {
        var userId = userRepository.findAll().stream().map(User::getId).toList().get(1);

        var locationForUser = LocationDto.builder()
                .latitude(12.0)
                .longitude(15.0)
                .build();

        given()
                .body(locationForUser)
                .contentType(ContentType.JSON)
        .when()
                .post("/location/create/" + userId)
        .then()
                .statusCode(200)
                .body("latitude", equalTo(12.0))
                .body("longitude", equalTo(15.0));

        var createdLocation = userRepository.findById(userId).get().getLocation();

        assertNotNull("Should be not null", createdLocation);
        assertThat(createdLocation.getLatitude()).isEqualTo(locationForUser.getLatitude());
        assertThat(createdLocation.getLongitude()).isEqualTo(locationForUser.getLongitude());
    }

    @Test
    public void updateLocation() {
        var userId = userRepository.findAll().stream().map(User::getId).toList().get(0);

        var updatedLocationForUser = LocationDto.builder()
                .latitude(12.0)
                .longitude(15.0)
                .build();

        given()
                .body(updatedLocationForUser)
                .contentType(ContentType.JSON)
        .when()
                .post("/location/updateByUserId/" + userId)
        .then()
                .statusCode(200)
                .body("latitude", equalTo(12.0))
                .body("longitude", equalTo(15.0));

        var createdLocation = userRepository.findById(userId).get().getLocation();

        assertNotNull("Should be not null", createdLocation);
        assertThat(createdLocation.getLatitude()).isEqualTo(updatedLocationForUser.getLatitude());
        assertThat(createdLocation.getLongitude()).isEqualTo(updatedLocationForUser.getLongitude());
    }
}
