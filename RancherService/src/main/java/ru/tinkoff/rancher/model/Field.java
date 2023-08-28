package ru.tinkoff.rancher.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Field {

    private Long gardnerId;
    private String address;
    private Long latitude;
    private Long longitude;
    private Long area;
}
