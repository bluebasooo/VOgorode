package ru.tinkoff.landscape.exeption;

import lombok.*;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class AppError {
    private final int statusCode;
    private final String message;

    private final List<Violation> violations;
}
