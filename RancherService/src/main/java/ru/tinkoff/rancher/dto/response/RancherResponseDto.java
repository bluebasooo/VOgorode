package ru.tinkoff.rancher.dto.response;


import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RancherResponseDto {
    private UUID id;
    private String login;
    private Double latitude;
    private Double longitude;
    private Double area;
    private List<String> works;
}
