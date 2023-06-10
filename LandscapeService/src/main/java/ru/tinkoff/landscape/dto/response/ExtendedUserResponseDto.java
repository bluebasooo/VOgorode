package ru.tinkoff.landscape.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtendedUserResponseDto {
    private UUID id;

    private String login;

    private String email;

    private String phone;

    private Integer userTypeId;
}
