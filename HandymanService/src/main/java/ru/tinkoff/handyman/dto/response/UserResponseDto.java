package ru.tinkoff.handyman.dto.response;

import lombok.*;
import ru.tinkoff.handyman.model.Account;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String name;
    private List<String> skills;
    private String email;
    private String photo;
    private List<Account> accounts;
}
