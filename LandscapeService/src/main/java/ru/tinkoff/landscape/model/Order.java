package ru.tinkoff.landscape.model;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private Long gardenId;
    private Long userId;
    private WorkType workType;
    private OrderStatus orderStatus;
    private LocalDate created;
}
