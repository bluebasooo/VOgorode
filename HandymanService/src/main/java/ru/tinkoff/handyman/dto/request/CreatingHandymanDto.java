package ru.tinkoff.handyman.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class CreatingHandymanDto {

    @NotBlank
    private String login;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String phone;

    @NotNull
    private Double longitude;

    @NotNull
    private Double latitude;
}
