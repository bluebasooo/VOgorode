package ru.tinkoff.landscape.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.landscape.response.ServerStateResponse;
import ru.tinkoff.landscape.service.StatusService;
import ru.tinkoff.landscape.service.SystemService;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/system")
public class LandscapeController {

    private StatusService statusService;
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

    /**
     * Method send requests to other services and get information about that
     * @return information about gRPC servers
     */
    @GetMapping("/connections")
    public Map<String, List<ServerStateResponse>> getConnections() {
        return statusService.getConnections();
    }

}