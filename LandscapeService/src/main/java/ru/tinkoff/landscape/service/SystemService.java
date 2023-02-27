package ru.tinkoff.landscape.service;

import io.grpc.ManagedChannel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SystemService {

    /**
     * @return status of server
     */
    public ResponseEntity getStatus() {
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * @return name and status of server
     */
    public Map.Entry<String,String> getServerStatus() {
        return Map.entry("LandscapeService", "OK");
    }
}
