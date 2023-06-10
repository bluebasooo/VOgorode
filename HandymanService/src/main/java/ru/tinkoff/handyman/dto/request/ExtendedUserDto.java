package ru.tinkoff.handyman.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ExtendedUserDto {
    private UUID id;
    private String login;
    private String email;
    private String phone;
    private Integer userTypeId;
}
