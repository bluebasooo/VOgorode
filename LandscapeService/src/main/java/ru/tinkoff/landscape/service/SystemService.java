package ru.tinkoff.landscape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SystemService {

    @Autowired
    private BuildProperties properties;

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
        return Map.entry(properties.getName(), "OK");
    }
}
