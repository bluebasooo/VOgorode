package ru.tinkoff.rancher.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.rancher.service.SystemService;

import java.util.Map;

@RestController
@RequestMapping("/system")
@AllArgsConstructor
public class SystemController {

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
     * End point to change status of server manually to "Malfunction"
     * @param isMalfunction - param to change state of server
     */
    @GetMapping("/force/malfunction")
    public void forceMalfunction(@RequestParam boolean isMalfunction) {
        systemService.forceMalfunction(isMalfunction);
    }

}