package ru.tinkoff.handyman.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class RepositoryExceptionHandler {

    @ExceptionHandler(ResourseNotFoundException.class)
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourseNotFoundException e) {
        return new ResponseEntity<>(
                new AppError(HttpStatus.NOT_FOUND.value(),"Not found error" ,e.getMessage(), null),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<AppError> catchSQLException(SQLException e) {
        return new ResponseEntity<>(
                new AppError(HttpStatus.BAD_REQUEST.value(), "Constraint is failed", e.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }

}
