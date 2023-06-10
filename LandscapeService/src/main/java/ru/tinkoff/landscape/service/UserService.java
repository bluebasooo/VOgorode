package ru.tinkoff.landscape.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.landscape.dto.request.CreatingUserDto;
import ru.tinkoff.landscape.dto.response.ExtendedUserResponseDto;
import ru.tinkoff.landscape.dto.request.UpdatingUserDto;
import ru.tinkoff.landscape.dto.response.UserResponseDto;
import ru.tinkoff.landscape.exeption.ResourseNotFoundException;
import ru.tinkoff.landscape.model.User;
import ru.tinkoff.landscape.repository.UserRepository;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Validated
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    /**
     *
     * @param creatingUserDto - DTO with of creating user
     * @return - DTO of created user
     */
    public UserResponseDto createUser(@Valid CreatingUserDto creatingUserDto) {
        User addingUser = mapper.map(creatingUserDto, User.class);
        addingUser.setCreates(LocalDate.now());
        addingUser.setUpdates(LocalDate.now());

        addingUser = userRepository.save(addingUser);
        return mapper.map(addingUser, UserResponseDto.class);
    }

    /**
     *
     * @return - all users from repository
     */
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUserFromRepository(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResourseNotFoundException("User with id " + id + " not found")
        );
    }

    /**
     *
     * @param id - id of user
     * @return - DTO of user
     */
    public UserResponseDto getUserById(UUID id) {
        return mapper.map(
                getUserFromRepository(id),
                UserResponseDto.class
        );
    }

    /**
     *
     * @param id - id of user
     * @param updatingUserDto - DTO with data to updating
     * @return - DTO with extended data about updated user
     */
    public ExtendedUserResponseDto updateUserById(UUID id, UpdatingUserDto updatingUserDto) {
        User userToUpdate = getUserFromRepository(id);
        User userFromRequest = mapper.map(updatingUserDto, User.class);
        userFromRequest.setUpdates(LocalDate.now());

        mapper.map(userFromRequest, userToUpdate);

        userToUpdate = userRepository.save(userToUpdate);

        return mapper.map(userToUpdate, ExtendedUserResponseDto.class);
    }

    /**
     *
     * @param id - id of user
     * @return - DTO with extended data about deleted user
     */
    public ExtendedUserResponseDto deleteUserById(UUID id) {
        User deletedUser = getUserFromRepository(id);
        userRepository.deleteById(id);
        return mapper.map(deletedUser, ExtendedUserResponseDto.class);
    }

    /**
     *
     * @param id - id of user
     * @return - DTO with extended data about user
     */
    public ExtendedUserResponseDto getExtendedUserById(UUID id) {
        return mapper.map(
                getUserFromRepository(id),
                ExtendedUserResponseDto.class
        );
    }
}
