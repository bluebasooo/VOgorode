package ru.tinkoff.rancher.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/system")
public class RancherController {

    /**
     * @return status of server
     */
    @GetMapping("/liveness")
    public ResponseEntity getStatus() {
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * @return status and name of service
     */
    @GetMapping("/readiness")
    public Map<String,HttpStatus> getServerStatus() {
        return Map.of("RancherService", HttpStatus.OK);
    }
}