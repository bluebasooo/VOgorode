package ru.tinkoff.rancher.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatingUserDto {

    private String login;

    private String email;

    private String phone;

    private int userTypeId;
}
