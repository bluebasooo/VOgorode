package ru.tinkoff.rancher.dto.request;

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
