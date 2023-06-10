package ru.tinkoff.rancher.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class CreatingRancherDto {

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

    @Min(0)
    private Double area;
}
