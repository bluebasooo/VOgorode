package ru.tinkoff.handyman.controller;


import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.handyman.service.SystemService;

import java.util.Map;

@RestController
@RequestMapping("/system")
@AllArgsConstructor
public class HandymanController {


    private SystemService systemService;

    /**
     * @return status of server
     */
    @GetMapping("/liveness")
    public ResponseEntity getStatus() {
        return systemService.getStatus();
    }

    /**
     * @return status and name of service
     */
    @GetMapping("/readiness")

    public Map.Entry<String,String> getServerStatus() {
        return systemService.getServerStatus();
    }

}
