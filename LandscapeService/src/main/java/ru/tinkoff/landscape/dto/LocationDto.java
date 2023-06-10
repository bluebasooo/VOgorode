package ru.tinkoff.landscape.dto;

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
