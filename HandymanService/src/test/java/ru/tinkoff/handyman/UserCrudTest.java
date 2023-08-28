package ru.tinkoff.handyman;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.tinkoff.handyman.dto.request.CreatingUserDto;
import ru.tinkoff.handyman.exeption.Violation;
import ru.tinkoff.handyman.model.User;
import ru.tinkoff.handyman.repository.UserRepository;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class UserCrudTest {
    @LocalServerPort
    private Integer port;

    private static ObjectMapper jsonMapper = new ObjectMapper();

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @DynamicPropertySource
    public static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("landscape.url", () -> "localhost:8080");
    }

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        userRepository.save(
                User.builder()
                        .name("name")
                        .surname("surname")
                        .skills(List.of("run", "stop"))
                        .email("email@mail.ru")
                        .phone("89996669966")
                        .photo("abc".getBytes())
                        .build()
        );
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    public void isRepoHasOneEntity() {
        assertThat(userRepository.count()).isEqualTo(1);
    }

    @Test
    public void testGetByIdWithExistsUser() {

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/user/" + "1")
        .then()
                .statusCode(200)
                .body("name", equalTo("name"))
                .body("skills", hasItem("run"));
    }

    @Test
    public void testGetByIdWithNotExistsUser() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/user/" + "50")
        .then()
                .statusCode(404)
                .body("message", equalTo("User with id: [50] - not found"))
                .body("statusCode", equalTo(404));
    }

    @Test
    public void testCreateUserWithValidFields() {
        var creatingDto = CreatingUserDto.builder()
                .name("user1")
                .surname("surname1")
                .skills(List.of("run", "mine"))
                .email("m@mail.ru")
                .phone("89995559922")
                .photo("abs".getBytes())
                .build();

        given()
                .body(creatingDto)
                .contentType(ContentType.JSON)
        .when()
                .post("user/create")
        .then()
                .statusCode(200)
                .body("id", equalTo(2))
                .body("name", equalTo("user1"))
                .body("skills", hasItems("run", "mine"))
                .body("email", equalTo("m@mail.ru"));

        assertThat(userRepository.count()).isEqualTo(2);
    }

    @Test
    public void testCreateUserWithInvalidFields() throws JsonProcessingException {
        var creatingDto = CreatingUserDto.builder()
                .name("a")
                .surname("surname1")
                .email("m@mail.ru")
                .phone("8999")
                .photo("abs".getBytes())
                .build();


        given()
                .body(creatingDto)
                .contentType(ContentType.JSON)
        .when()
                .post("user/create")
        .then()
                .statusCode(400)
                .body("typeError", equalTo("Validation Error"))
                .body("violations.find {it.fieldName == 'name'}.message", equalTo("Length of name is between 2 and 20"))
                .body("violations.find {it.fieldName == 'phone'}.message", equalTo("Length of phone is between 11 and 18"))
                .body("violations.find {it.fieldName == 'skills'}.message",equalTo("Skills is required"));

        assertThat(userRepository.count()).isEqualTo(1);
    }

    @Test
    public void testDeleteExistsUser() {
        given()
                .contentType(ContentType.JSON)
                .when()
        .delete("/user/" + "1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("name"))
                .body("skills", hasSize(2))
                .body("skills", hasItem("run"))
                .body("skills", hasItem("stop"))
                .body("email", equalTo("email@mail.ru"));

        assertThat(userRepository.count()).isEqualTo(0);

    }

}
