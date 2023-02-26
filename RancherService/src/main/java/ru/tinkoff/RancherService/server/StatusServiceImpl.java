package ru.tinkoff.RancherService.server;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import ru.tinkoff.RancherService.controller.RancherController;
import ru.tinkoff.proto.ReadinessResponse;
import ru.tinkoff.proto.StatusServiceGrpc;
import ru.tinkoff.proto.VersionResponse;

@GrpcService
public class StatusServiceImpl extends StatusServiceGrpc.StatusServiceImplBase {

    @Autowired
    private BuildProperties properties;

    @Autowired
    private RancherController rancherController;

    @Override
    public void getVersion(Empty request, StreamObserver<VersionResponse> responseObserver) {
        VersionResponse response = VersionResponse.newBuilder()
                .setArtifact(properties.getArtifact())
                .setName(properties.getName())
                .setGroup(properties.getGroup())
                .setVersion(properties.getVersion())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getReadiness(Empty request,StreamObserver<ReadinessResponse> responseObserver) {
        String currentStatus = rancherController.getStatus().toString();

        ReadinessResponse response = ReadinessResponse.newBuilder()
                .setStatus(currentStatus)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
