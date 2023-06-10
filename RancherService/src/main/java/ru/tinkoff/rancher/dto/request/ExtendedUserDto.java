package ru.tinkoff.rancher.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExtendedUserDto {
    private UUID id;
    private String login;
    private String email;
    private String phone;
    private Integer userTypeId;
}
