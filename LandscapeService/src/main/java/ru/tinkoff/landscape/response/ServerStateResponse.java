package ru.tinkoff.landscape.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServerStateResponse {
    private String host;
    private String status;
    private String artifact;
    private String name;
    private String group;
    private String version;
}
