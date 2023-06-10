package ru.tinkoff.handyman.exeption;

import lombok.Getter;

import java.util.List;

@Getter
public class ServiceBadResponseException extends RuntimeException {
    private final int statusCode;
    private final List<Violation> violation;
    public ServiceBadResponseException(AppError errorBody) {
        super(errorBody.getMessage());
        statusCode = errorBody.getStatusCode();
        violation = errorBody.getViolations();
    }
}
