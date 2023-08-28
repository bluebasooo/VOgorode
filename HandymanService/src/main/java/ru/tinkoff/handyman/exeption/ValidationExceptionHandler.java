package ru.tinkoff.handyman.exeption;

import liquibase.pro.packaged.A;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<AppError> catchViolationException(ConstraintViolationException e) {
        List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                            violation.getPropertyPath()
                                    .toString()
                                    .substring(violation.getPropertyPath().toString().lastIndexOf(".") + 1),
                            violation.getMessage()
                        )
                ).toList();
        return new ResponseEntity<>(
                new AppError(HttpStatus.BAD_REQUEST.value(), "Validation Error", "", violations),
                HttpStatus.BAD_REQUEST
        );
    }
}
