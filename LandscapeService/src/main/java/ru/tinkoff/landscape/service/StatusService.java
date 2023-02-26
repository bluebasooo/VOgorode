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

    public VersionResponse getVersion(StatusServiceGrpc.StatusServiceBlockingStub serviceBlockingStub) {
        return serviceBlockingStub.getVersion(Empty.getDefaultInstance());
    }

    public ReadinessResponse getReadiness(StatusServiceGrpc.StatusServiceBlockingStub serviceBlockingStub) {
        return serviceBlockingStub.getReadiness(Empty.getDefaultInstance());
    }

    private ServerDto responseMapping(StatusServiceGrpc.StatusServiceBlockingStub serviceBlockingStub, String adress) {
        VersionResponse versionResponse = getVersion(serviceBlockingStub);
        ReadinessResponse readinessResponse = getReadiness(serviceBlockingStub);

        return ServerDto.builder()
                .host(adress)
                .status(readinessResponse.getStatus().toString())
                .artifact(versionResponse.getArtifact())
                .name(versionResponse.getName())
                .group(versionResponse.getGroup())
                .version(versionResponse.getVersion())
                .build();
    }

    private Map<String, String> getChannels() {
        return grpcChannelsProperties.getClient().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, channel -> channel.getValue().getAddress().toString()));
    }

    private Map<String, StatusServiceGrpc.StatusServiceBlockingStub> getStubs(Map<String, String> channels) {
        return channels.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getValue, server -> StatusServiceGrpc.newBlockingStub(
                                ManagedChannelBuilder.forTarget(server.getValue())
                                        .usePlaintext()
                                        .build()
                        )));
    }

    private Map<String, List<ServerDto>> getAllConnections(Map<String, StatusServiceGrpc.StatusServiceBlockingStub> strubs) {
        return strubs.entrySet().stream()
                .map(strub -> responseMapping(strub.getValue(),strub.getKey()))
                .collect(Collectors.groupingBy(ServerDto::getName));

    }
    public Map<String, List<ServerDto>> getConnections() {
        return getAllConnections(getStubs(getChannels()));
    }
}
