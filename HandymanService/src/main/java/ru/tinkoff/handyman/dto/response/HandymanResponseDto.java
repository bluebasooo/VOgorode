package ru.tinkoff.handyman.dto.response;


import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HandymanResponseDto {
    private UUID id;
    private Double latitude;
    private Double longitude;
    private List<String> works;
}
