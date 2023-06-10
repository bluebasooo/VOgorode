package ru.tinkoff.landscape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.Example;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.tinkoff.landscape.dto.request.CreatingUserDto;
import ru.tinkoff.landscape.dto.request.UpdatingUserDto;
import ru.tinkoff.landscape.model.User;
import ru.tinkoff.landscape.repository.UserRepository;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class UserCrudTest {

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
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
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
        RestAssured.baseURI = "http://localhost:" + port;

    }

    @Test
    public void isRepoHasTwoEntity() {
        assertThat(userRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    public void testGetById() {
        var ids = userRepository.findAll().stream().map(User::getId).toList();

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/user/getById/" + ids.get(0))
        .then()
                .statusCode(200)
                .body("id", equalTo(ids.get(0).toString()))
                .body("login", equalTo("BRUH"))
                .body("userTypeId", equalTo(1));
    }

    @Test
    public void testCreateUser() {
        var userDto = CreatingUserDto.builder()
                .login("NewUser")
                .email("user@gmail.com")
                .phone("+7777777787")
                .userTypeId(1)
                .build();
        var exampleUser = Example.of(
                User.builder()
                        .login(userDto.getLogin())
                        .email(userDto.getEmail())
                        .phone(userDto.getPhone())
                        .build()
        );

        given()
                .body(userDto)
                .contentType(ContentType.JSON)
        .when()
                .post("/user/create")
        .then()
                .statusCode(200)
                .body("login", equalTo(userDto.getLogin()))
                .body("userTypeId", equalTo(userDto.getUserTypeId()));

        assertThat(
                userRepository.findOne(exampleUser).isPresent()
        ).isTrue();

    }


    @Test
    public void testDeleteUser() {
        var userToDelete = userRepository.findAll().get(1);

        given()
                .contentType(ContentType.JSON)
        .when()
                .delete("/user/deleteById/" + userToDelete.getId())
        .then()
                .statusCode(200)
                .body("login", equalTo(userToDelete.getLogin()));

        assertThat(userRepository.count()).isEqualTo(1);
    }

    @Test
    public void testGetExtendedUserById() {
        var user = userRepository.findAll().get(0);

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("user/getExtendedUserById/" + user.getId())
        .then()
                .body("phone", equalTo(user.getPhone()))
                .body("email", equalTo(user.getEmail()));
    }

    @Test
    public void testUpdateUserById() {
        var userToUpdate = userRepository.findAll().get(0);
        var changes = UpdatingUserDto.builder()
                .login("NewLogin")
                .userTypeId(2)
                .build();


        given()
                .body(changes)
                .contentType(ContentType.JSON)
        .when()
                .post("user/updateById/" + userToUpdate.getId())
        .then()
                .statusCode(200)
                .body("login", equalTo(changes.getLogin()))
                .body("email", equalTo(userToUpdate.getEmail()))
                .body("phone", equalTo(userToUpdate.getPhone()))
                .body("userTypeId", equalTo(changes.getUserTypeId()));

        assertThat(userRepository.count()).isEqualTo(2);

        var updatedUser = userRepository.findOne(
                Example.of(
                        User.builder()
                                .login(changes.getLogin())
                                .build()
                )
        );
        assertThat(updatedUser.isPresent()).isTrue();
        assertThat(updatedUser.get().getLogin()).isEqualTo(changes.getLogin());
        assertThat(updatedUser.get().getUpdates()).isAfter(userToUpdate.getUpdates());
        assertThat(updatedUser.get().getUserTypeId()).isEqualTo(changes.getUserTypeId());
    }
}
