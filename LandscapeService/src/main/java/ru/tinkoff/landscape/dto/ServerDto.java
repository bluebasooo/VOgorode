package ru.tinkoff.landscape.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServerDto {
    private String host;
    private String status;
    private String artifact;
    private String name;
    private String group;
    private String version;
}
