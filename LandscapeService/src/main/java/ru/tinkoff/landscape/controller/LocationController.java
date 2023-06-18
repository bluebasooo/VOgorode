package ru.tinkoff.landscape.controller;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.landscape.dto.LocationDto;
import ru.tinkoff.landscape.model.Location;
import ru.tinkoff.landscape.service.LocationService;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    /**
     * Method gets location of user
     * @param userId - id of user
     * @return - DTO with location data
     */
    @Timed
    @GetMapping("/getByUserId/{userId}")
    public LocationDto getLocationByUserId(@PathVariable UUID userId) {
        return locationService.getLocationByUserId(userId);
    }

    /**
     * Method creates location to user
     * @param userId - id of user which location we add
     * @param locationDto - DTO with data of location
     * @return - DTO with data of created location
     */
    @Timed
    @PostMapping("/create/{userId}")
    public LocationDto createLocationById(@PathVariable UUID userId, @RequestBody LocationDto locationDto) {
        return locationService.createLocationById(userId, locationDto);
    }

    /**
     * Method update location of user
     * @param userId - id of user which location we update
     * @param locationDto - DTO with data of updating location
     * @return - DTO of updated location
     */
    @Timed
    @PostMapping("/updateByUserId/{userId}")
    public LocationDto updateLocationById(@PathVariable UUID userId, @RequestBody LocationDto locationDto) {
        return locationService.updateLocationById(userId, locationDto);
    }
}
