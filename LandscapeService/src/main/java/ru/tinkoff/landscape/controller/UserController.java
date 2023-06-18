package ru.tinkoff.landscape.controller;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.landscape.dto.request.CreatingUserDto;
import ru.tinkoff.landscape.dto.response.ExtendedUserResponseDto;
import ru.tinkoff.landscape.dto.request.UpdatingUserDto;
import ru.tinkoff.landscape.dto.response.UserResponseDto;
import ru.tinkoff.landscape.model.User;
import ru.tinkoff.landscape.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    /**
     *
     * @param creatingUserDto - DTO with creating data
     * @return - DTO with created user
     */
    @Timed
    @PostMapping("/create")
    public UserResponseDto createUser(@RequestBody CreatingUserDto creatingUserDto) {
        return service.createUser(creatingUserDto);
    }

    /**
     *
     * @return all users from repository
     */
    @Timed
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return service.getAllUser();
    }

    /**
     *
     * @param id - id of user
     * @return - DTO with data about user
     */
    @Timed
    @GetMapping("/getById/{id}")
    public UserResponseDto getUserById(@PathVariable UUID id) {
        return service.getUserById(id);
    }

    /**
     *
     * @param id - id of user
     * @return - DTO with extended data
     */
    @Timed
    @GetMapping("/getExtendedUserById/{id}")
    public ExtendedUserResponseDto getExtendedUserById(@PathVariable UUID id) {
        return service.getExtendedUserById(id);
    }

    /**
     *
     * @param id - id of user
     * @param updatingUserDto - DTO with information about updating fields
     * @return - DTO of updated user with extended user data
     */
    @Timed
    @PostMapping("/updateById/{id}")
    public ExtendedUserResponseDto updateUserById(@PathVariable UUID id,@RequestBody UpdatingUserDto updatingUserDto) {
        return service.updateUserById(id, updatingUserDto);
    }

    /**
     *
     * @param id - id of user
     * @return - DTO of deleted user with extended data
     */
    @Timed
    @DeleteMapping("/deleteById/{id}")
    public ExtendedUserResponseDto deleteUserById(@PathVariable UUID id) {
        return service.deleteUserById(id);
    }

}
