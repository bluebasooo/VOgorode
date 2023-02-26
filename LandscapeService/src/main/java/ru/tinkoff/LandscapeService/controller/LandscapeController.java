package ru.tinkoff.LandscapeService.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.LandscapeService.dto.ServerDto;
import ru.tinkoff.LandscapeService.service.StatusService;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/system")
public class LandscapeController {

    private StatusService statusService;

    @GetMapping("/liveness")
    public ResponseEntity getStatus() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/readiness")
    public Map<String,HttpStatus> getServerStatus() {
        return Map.of("Landscape", HttpStatus.OK);
    }

    @GetMapping("/connections")
    public Map<String, List<ServerDto>> getConnections() {
        return statusService.getConnections();
    }

}