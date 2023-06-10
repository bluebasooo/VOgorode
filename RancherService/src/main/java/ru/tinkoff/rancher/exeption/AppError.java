package ru.tinkoff.rancher.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppError {
    private int statusCode;
    private String message;

    private List<Violation> violations;
}
