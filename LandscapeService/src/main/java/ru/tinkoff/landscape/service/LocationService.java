package ru.tinkoff.landscape.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.tinkoff.landscape.dto.LocationDto;
import ru.tinkoff.landscape.exeption.ResourseNotFoundException;
import ru.tinkoff.landscape.model.Location;
import ru.tinkoff.landscape.model.User;
import ru.tinkoff.landscape.repository.LocationRepository;

import java.util.UUID;

@Service
@AllArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final UserService userService;
    private final ModelMapper mapper;

    /**
     *
     * @param userId - id of user
     * @return - Location entity
     */
    public Location getLocationFromRepository(UUID userId) {
        return locationRepository.findById(userId).orElseThrow(
                () -> new ResourseNotFoundException("Location with id " + userId + " not found")
        );
    }

    /**
     *
     * @param userId - id of user
     * @return - DTO with data of location
     */
    public LocationDto getLocationByUserId(UUID userId) {
        return mapper.map(
                getLocationFromRepository(userId),
                LocationDto.class
        );
    }

    /**
     *
     * @param userId - id of user
     * @param locationDto - DTO with data of updating location
     * @return - DTO of updated location
     */
    public LocationDto updateLocationById(UUID userId, LocationDto locationDto) {
        Location updatedLocation = getLocationFromRepository(userId);
        Location requestLocation = mapper.map(locationDto, Location.class);

        mapper.map(requestLocation, updatedLocation);

        updatedLocation = locationRepository.save(updatedLocation);
        return mapper.map(updatedLocation, LocationDto.class);
    }

    /**
     *
     * @param userId - id of user
     * @param locationDto - DTO with data of creating locaiton
     * @return - DTO of created location
     */
    public LocationDto createLocationById(UUID userId, LocationDto locationDto) {
        User user = userService.getUserFromRepository(userId);

        Location addingLocation = mapper.map(locationDto, Location.class);
        addingLocation.setUser(user);
        addingLocation.setUserId(userId);

        addingLocation = locationRepository.save(addingLocation);

        return mapper.map(addingLocation, LocationDto.class);
    }
}
