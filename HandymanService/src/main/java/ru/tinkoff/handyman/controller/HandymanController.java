package ru.tinkoff.HandymanService.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.HandymanService.server.SystemService;

import java.util.Map;

@RestController
@RequestMapping("/system")
@AllArgsConstructor
public class HandymanController {

    private SystemService systemService;

    @GetMapping("/liveness")
    public ResponseEntity getStatus() {
        return systemService.getStatus();
    }

    @GetMapping("/readiness")
    public Map.Entry<String,String> getServerStatus() {
        return systemService.getServerStatus();
    }

}
