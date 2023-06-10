package ru.tinkoff.landscape.exeption;

import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourseNotFoundException.class)
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourseNotFoundException e) {
        return new ResponseEntity<>(
                new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage(), null),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppError> catchMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .toList();

        return new ResponseEntity<>(
                new AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect Fileds", violations),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<AppError> catchPSQLException(PSQLException e) {
        return new ResponseEntity<>(
                new AppError(HttpStatus.BAD_REQUEST.value(), e.getServerErrorMessage().getDetail(), null),
                HttpStatus.valueOf(400)
        );
    }
}
