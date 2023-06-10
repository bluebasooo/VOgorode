package ru.tinkoff.handyman.dto;

import lombok.*;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class WorksDto {
    private final List<String> works;
}
