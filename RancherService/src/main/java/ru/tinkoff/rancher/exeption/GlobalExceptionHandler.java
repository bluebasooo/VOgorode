package ru.tinkoff.rancher.exeption;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourseNotFoundException.class)
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourseNotFoundException e) {
        return new ResponseEntity<>(
                new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage(), null),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<AppError> catchFeignException(FeignException e){
        try {
            AppError err = new ObjectMapper().readValue(e.contentUTF8(), AppError.class);

            return new ResponseEntity<>(
                    err,
                    HttpStatus.valueOf(e.status())
            );
        } catch(JsonProcessingException exp) {
            return new ResponseEntity<>(
                    new AppError(500, exp.getMessage(), null),
                    HttpStatus.valueOf(500));
        }
    }

}
