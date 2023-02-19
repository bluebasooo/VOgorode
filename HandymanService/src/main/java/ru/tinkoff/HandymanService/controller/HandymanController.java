package ru.tinkoff.HandymanService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/system")
public class HandymanController {

    @GetMapping("/liveness")
    public ResponseEntity getStatus() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/readiness")
    public Map<String,HttpStatus> getServerStatus() {
        return Map.of("Handyman", HttpStatus.OK);
    }
}
