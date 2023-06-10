package ru.tinkoff.rancher.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.tinkoff.rancher.dto.request.LocationDto;

import java.util.UUID;

@Service
@FeignClient(name="location", url="${landscape.url}/location")
public interface LandscapeLocationClient {
    @PostMapping("/create/{userId}")
    LocationDto createLocation(@PathVariable UUID userId, @RequestBody LocationDto locationDto);
}
