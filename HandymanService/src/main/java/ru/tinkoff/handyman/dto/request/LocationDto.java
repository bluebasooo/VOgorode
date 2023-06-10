package ru.tinkoff.handyman.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDto {
    private Double latitude;
    private Double longitude;
}
