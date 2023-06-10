package ru.tinkoff.landscape.dto.request;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Builder
@AllArgsConstructor
public class CreatingUserDto {

    @NotBlank
    private String login;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String phone;

    @Min(1)
    @Max(2)
    private Integer userTypeId;
}
