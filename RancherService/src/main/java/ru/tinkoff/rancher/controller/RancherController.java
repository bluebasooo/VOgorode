package ru.tinkoff.RancherService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.RancherService.server.SystemService;

import java.util.Map;

@RestController
@RequestMapping("/system")
public class RancherController {

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