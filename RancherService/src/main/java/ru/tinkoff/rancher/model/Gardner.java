package ru.tinkoff.rancher.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Gardner {
    private Long id;
    private String name;
    private String surname;
    private List<Field> fields;
    private String email;
    private String phone;
}
