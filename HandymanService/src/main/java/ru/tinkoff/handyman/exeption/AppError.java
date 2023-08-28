package ru.tinkoff.handyman.exeption;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppError {
    private int statusCode;
    private String typeError;
    private String message;

    private List<Violation> violations;
}
