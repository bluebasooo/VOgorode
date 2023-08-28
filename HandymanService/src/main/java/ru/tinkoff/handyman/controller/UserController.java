package ru.tinkoff.handyman.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.handyman.dto.request.CreatingUserDto;
import ru.tinkoff.handyman.dto.response.UserResponseDto;
import ru.tinkoff.handyman.service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public UserResponseDto createUser(@RequestBody CreatingUserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/{userId}")
    public UserResponseDto getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/{userId}")
    public UserResponseDto updateUserById(@PathVariable Long userId) {
        return userService.updateUserById(userId);
    }

    @DeleteMapping("/{userId}")
    public UserResponseDto deleteUserById(@PathVariable Long userId) {
        return userService.deleteUserById(userId);
    }

}
