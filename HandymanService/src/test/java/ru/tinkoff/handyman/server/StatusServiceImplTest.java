package ru.tinkoff.handyman.server;

import com.google.protobuf.Empty;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.tinkoff.proto.ReadinessResponse;
import ru.tinkoff.proto.StatusServiceGrpc;
import ru.tinkoff.proto.VersionResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(properties = {
        "grpc.server.port = 8080",
        "grpc.client.inProgress.address = localhost:8080",
        "grpc.client.inProgress.negotiation-type= plaintext"
})
@DirtiesContext
class StatusServiceImplTest {

    @GrpcClient("inProgress")
    private StatusServiceGrpc.StatusServiceBlockingStub service;

    @Autowired
    private BuildProperties properties;

    @Test
    @DirtiesContext
    void getVersion() {
        //GIVEN
        VersionResponse appVersion = VersionResponse.newBuilder()
                .setArtifact(properties.getArtifact())
                .setName(properties.getName())
                .setGroup(properties.getGroup())
                .setVersion(properties.getVersion())
                .build();

        //WHEN
        VersionResponse response = service.getVersion(Empty.getDefaultInstance());

        //THEN
        assertNotNull(response);
        assertEquals(response, appVersion);
    }

    @Test
    @DirtiesContext
    void getReadiness() {
        //GIVEN
        ReadinessResponse serverStatus = ReadinessResponse.newBuilder()
                .setStatus("<200 OK OK,[]>")
                .build();

        //WHEN
        ReadinessResponse response = service.getReadiness(Empty.getDefaultInstance());

        //THEN
        assertNotNull(response);
        assertEquals(response, serverStatus);
    }
}