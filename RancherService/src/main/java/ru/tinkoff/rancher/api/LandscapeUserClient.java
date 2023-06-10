package ru.tinkoff.rancher.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.tinkoff.rancher.dto.request.CreatingUserDto;
import ru.tinkoff.rancher.dto.request.ExtendedUserDto;
import ru.tinkoff.rancher.dto.request.UserDto;

import java.util.UUID;

@Service
@FeignClient(name = "user",url = "${landscape.url}/user")
public interface LandscapeUserClient {

    @GetMapping("/getExtendedUserById/{id}")
    ExtendedUserDto getExtendedUserById(@PathVariable UUID id);

    @PostMapping("/create")
    UserDto createUser(@RequestBody CreatingUserDto userDto);
}
