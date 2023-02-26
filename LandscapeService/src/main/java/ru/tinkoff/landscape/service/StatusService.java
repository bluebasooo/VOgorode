package ru.tinkoff.landscape.service;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannelBuilder;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.client.config.GrpcChannelsProperties;
import org.springframework.stereotype.Service;
import ru.tinkoff.landscape.dto.ServerDto;
import ru.tinkoff.proto.ReadinessResponse;
import ru.tinkoff.proto.StatusServiceGrpc;
import ru.tinkoff.proto.VersionResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StatusService {

    private GrpcChannelsProperties grpcChannelsProperties;

    /**
     * @param serviceBlockingStub - stub to connect to server
     * @return response of the server which contains information about server
     */
    public VersionResponse getVersion(StatusServiceGrpc.StatusServiceBlockingStub serviceBlockingStub) {
        return serviceBlockingStub.getVersion(Empty.getDefaultInstance());
    }

    /**
     * @param serviceBlockingStub - stub to connect to server
     * @return response of the server which contains information about status of the server
     */
    public ReadinessResponse getReadiness(StatusServiceGrpc.StatusServiceBlockingStub serviceBlockingStub) {
        return serviceBlockingStub.getReadiness(Empty.getDefaultInstance());
    }

    /**
     * Do mapping of the response on a ServerDTO
     * @param serviceBlockingStub - stub to connect to server
     * @param address - host and port of server
     * @return ServerDTO - class which represent data about server
     */
    private ServerDto responseMapping(StatusServiceGrpc.StatusServiceBlockingStub serviceBlockingStub, String address) {
        VersionResponse versionResponse = getVersion(serviceBlockingStub);
        ReadinessResponse readinessResponse = getReadiness(serviceBlockingStub);

        return ServerDto.builder()
                .host(address)
                .status(readinessResponse.getStatus().toString())
                .artifact(versionResponse.getArtifact())
                .name(versionResponse.getName())
                .group(versionResponse.getGroup())
                .version(versionResponse.getVersion())
                .build();
    }

    /**
     * @return name and address of server in Map from application properties
     */
    private Map<String, String> getChannels() {
        return grpcChannelsProperties.getClient().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, channel -> channel.getValue().getAddress().toString()));
    }

    /**
     * @param channels - name and addresses of servers
     * @return instances of stubs servers
     */
    private Map<String, StatusServiceGrpc.StatusServiceBlockingStub> getStubs(Map<String, String> channels) {
        return channels.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getValue, server -> StatusServiceGrpc.newBlockingStub(
                                ManagedChannelBuilder.forTarget(server.getValue())
                                        .usePlaintext()
                                        .build()
                        )));
    }

    /**
     * Method get info about servers by doing requests to servers
     * @param stubs - instances of stubs servers
     * @return - info about services
     */
    private Map<String, List<ServerDto>> getAllConnections(Map<String, StatusServiceGrpc.StatusServiceBlockingStub> stubs) {
        return stubs.entrySet().stream()
                .map(strub -> responseMapping(strub.getValue(),strub.getKey()))
                .collect(Collectors.groupingBy(ServerDto::getName));

    }

    /**
     * @return - info about services
     */
    public Map<String, List<ServerDto>> getConnections() {
        return getAllConnections(getStubs(getChannels()));
    }
}
