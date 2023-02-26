package ru.tinkoff.rancher.server;

import io.grpc.ManagedChannel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SystemService {
    ManagedChannel channel;

    public ResponseEntity getStatus() {
        return new ResponseEntity(HttpStatus.OK);
    }

    public Map.Entry<String,String> getServerStatus() {
        return Map.entry("Rancher", channel.getState(true).toString());
    }
}
