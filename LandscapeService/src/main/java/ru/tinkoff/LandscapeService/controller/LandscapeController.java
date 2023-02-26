package ru.tinkoff.LandscapeService.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.LandscapeService.dto.ServerDto;
import ru.tinkoff.LandscapeService.service.StatusService;
import ru.tinkoff.LandscapeService.service.SystemService;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/system")
public class LandscapeController {

    private StatusService statusService;
    private SystemService systemService;

    @GetMapping("/liveness")
    public ResponseEntity getStatus() {
        return systemService.getStatus();
    }

    @GetMapping("/readiness")
    public Map.Entry<String,String> getServerStatus() {
        return systemService.getServerStatus();
    }

    @GetMapping("/connections")
    public Map<String, List<ServerDto>> getConnections() {
        return statusService.getConnections();
    }

}