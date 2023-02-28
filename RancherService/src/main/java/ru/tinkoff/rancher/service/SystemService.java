package ru.tinkoff.rancher.service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SystemService {
    private final ManagedChannel channel;

    @Autowired
    private BuildProperties properties;

    private boolean isMalfunction;

    private static final Logger LOG = LoggerFactory.getLogger(SystemService.class);

    /**
     * Constructor to get channel to server
     * @param port - server port
     */
    @Autowired
    public SystemService(@Value("${grpc.server.port}") int port) {
        this.channel = ManagedChannelBuilder
                .forAddress("localhost",port)
                .usePlaintext()
                .build();
    }

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
        return Map.entry("RancherService", isMalfunction ? "Malfunction" : channel.getState(true).toString());
    }

    /**
     * Changes status of server manually to "Malfunction"
     * @param isMalfunction - param to change state of server
     */
    public void forceMalfunction(boolean isMalfunction) {
        this.isMalfunction = isMalfunction;
        LOG.warn("Status of server - " + properties.getName() + " change to Malfunction manually!");
    }
}
