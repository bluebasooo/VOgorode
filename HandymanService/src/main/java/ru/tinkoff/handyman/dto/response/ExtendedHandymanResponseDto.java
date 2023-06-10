package ru.tinkoff.handyman.dto.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtendedHandymanResponseDto {
    private UUID id;
    private String login;
    private String email;
    private String phone;
    private Double latitude;
    private Double longitude;
    private List<String> works;
}
