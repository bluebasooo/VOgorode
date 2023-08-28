package ru.tinkoff.handyman.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CreatingUserDto {

    @NotBlank(message = "Name shoud be not blank")
    @NotNull(message = "Name is required")
    @Size(min=2, max=20, message = "Length of name is between 2 and 20")
    private final String name;

    @NotBlank(message = "Surname should be not blank")
    @NotNull(message = "Surname is required")
    @Size(min=2, max=20, message = "Length of surname is between 2 and 20")
    private final String surname;

    @NotNull(message = "Skills is required")
    private final List<String> skills;

    @NotBlank(message = "Email should be not blank")
    @NotNull(message = "Email is required")
    @Email(message = "Current email is not acceptable")
    private final String email;

    @NotNull(message = "Phone is should be not blank")
    @NotNull(message = "Phone is required")
    @Size(min = 11, max = 18, message = "Length of phone is between 11 and 18")
    private final String phone;

    @NotNull(message = "Photo is required")
    private byte[] photo;
}
