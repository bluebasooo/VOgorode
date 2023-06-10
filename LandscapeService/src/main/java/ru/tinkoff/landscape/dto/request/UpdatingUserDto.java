package ru.tinkoff.landscape.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpdatingUserDto {
    private String login;

    private String email;

    private String phone;

    private int userTypeId;
}
