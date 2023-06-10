package ru.tinkoff.landscape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.landscape.config.MapperConfig;
import ru.tinkoff.landscape.dto.request.CreatingUserDto;
import ru.tinkoff.landscape.dto.request.UpdatingUserDto;
import ru.tinkoff.landscape.dto.response.ExtendedUserResponseDto;
import ru.tinkoff.landscape.dto.response.UserResponseDto;
import ru.tinkoff.landscape.model.User;
import ru.tinkoff.landscape.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.argThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    private User userFromRepo;

    @BeforeEach
    public void init() {
        userRepository = Mockito.mock(UserRepository.class);
        var mapper = new MapperConfig().mapper();
        userService = new UserService(userRepository, mapper);

        userFromRepo = User.builder()
                .id(UUID.randomUUID())
                .login("login1")
                .email("login1@domer.ru")
                .phone("8-888-333-22-11")
                .userTypeId(1)
                .creates(LocalDate.now())
                .updates(LocalDate.now())
                .location(null)
                .build();
    }

    @Test
    @DisplayName("Test of service login in creating")
    public void testCreateUser() {
        //GIVEN
        var userDto = CreatingUserDto.builder()
                .login(userFromRepo.getLogin())
                .email(userFromRepo.getEmail())
                .phone(userFromRepo.getPhone())
                .userTypeId(userFromRepo.getUserTypeId())
                .build();

        Mockito.when(userRepository.save(any())).thenReturn(userFromRepo);

        var rUserDto = UserResponseDto.builder()
                .id(userFromRepo.getId())
                .login(userDto.getLogin())
                .userTypeId(userDto.getUserTypeId())
                .build();


        //WHEN
        var savedUser = userService.createUser(userDto);


        //THEN
        Mockito.verify(userRepository).save(argThat(x -> {
            assertThat(x.getLogin()).isEqualTo(userFromRepo.getLogin());
            assertThat(x.getEmail()).isEqualTo(userFromRepo.getEmail());
            assertThat(x.getPhone()).isEqualTo(userFromRepo.getPhone());
            assertThat(x.getUserTypeId()).isEqualTo(userFromRepo.getUserTypeId());
            return true;
        }));

        assertThat(savedUser.getId()).isEqualTo(userFromRepo.getId());
        assertThat(savedUser.getLogin()).isEqualTo(userDto.getLogin());
        assertThat(savedUser.getUserTypeId()).isEqualTo(userDto.getUserTypeId());
    }

    @Test
    public void testUpdateUserById() {
        //GIVEN
        var id = userFromRepo.getId();
        var updatingUserDto = UpdatingUserDto.builder()
                .login("login2")
                .userTypeId(2)
                .build();
        var updatingUser = User.builder()
                        .id(userFromRepo.getId())
                        .login(updatingUserDto.getLogin())
                        .email(userFromRepo.getEmail())
                        .phone(userFromRepo.getPhone())
                        .userTypeId(updatingUserDto.getUserTypeId())
                        .creates(userFromRepo.getCreates())
                        .updates(LocalDate.now())
                        .build();
        var expectedUserDto = ExtendedUserResponseDto.builder()
                .id(userFromRepo.getId())
                .login(updatingUserDto.getLogin())
                .email(userFromRepo.getEmail())
                .phone(userFromRepo.getPhone())
                .userTypeId(updatingUserDto.getUserTypeId())
                .build();

        Mockito.when(userRepository.save(any(User.class))).thenReturn(updatingUser);
        Mockito.when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(userFromRepo));

        //WHEN
        var userDto = userService.updateUserById(id, updatingUserDto);

        //THEN
        Mockito.verify(userRepository).save(argThat(x -> {
            assertThat(x.getId()).isEqualTo(updatingUser.getId());
            assertThat(x.getLogin()).isEqualTo(updatingUser.getLogin());
            assertThat(x.getEmail()).isEqualTo(updatingUser.getEmail());
            assertThat(x.getPhone()).isEqualTo(updatingUser.getPhone());
            assertThat(x.getUserTypeId()).isEqualTo(updatingUser.getUserTypeId());
            return true;
        }));

        assertThat(userDto.getId()).isEqualTo(expectedUserDto.getId());
        assertThat(userDto.getLogin()).isEqualTo(expectedUserDto.getLogin());
        assertThat(userDto.getUserTypeId()).isEqualTo(expectedUserDto.getUserTypeId());
    }
}
