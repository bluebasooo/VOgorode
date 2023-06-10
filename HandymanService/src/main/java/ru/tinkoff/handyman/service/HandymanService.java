package ru.tinkoff.handyman.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.handyman.api.LandscapeLocationClient;
import ru.tinkoff.handyman.api.LandscapeUserClient;
import ru.tinkoff.handyman.dto.WorksDto;
import ru.tinkoff.handyman.dto.request.*;
import ru.tinkoff.handyman.dto.response.ExtendedHandymanResponseDto;
import ru.tinkoff.handyman.dto.response.HandymanResponseDto;
import ru.tinkoff.handyman.exeption.ResourseNotFoundException;
import ru.tinkoff.handyman.model.Handyman;
import ru.tinkoff.handyman.repository.HandymanRepository;

import java.util.List;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class HandymanService {
    private final HandymanRepository handymanRepository;
    private final ModelMapper mapper;
    private final LandscapeUserClient userClient;
    private final LandscapeLocationClient locationClient;

    /**
     *
     * @return list of all handyman in repository
     */
    public List<Handyman> getAllHandyman() {
        return handymanRepository.findAll();
    }

    /**
     *
     * @param userId - id of user in landscape service
     * @return handyman entity from HandymanRepository
     */
    public Handyman getHandymanFromRepository(UUID userId) {
        return handymanRepository.findById(userId).orElseThrow(
                () -> new ResourseNotFoundException("Handyman with id: " + userId + " not found")
        );
    }

    /**
     * Method gets users data from landscape service and use it for itself and return data with DTO
     * @param userId - id of user in landscape service
     * @return - DTO extended with user data from landscape service
     */
    public ExtendedHandymanResponseDto getHandymanById(UUID userId) {
        ExtendedUserDto userDto = userClient.getExtendedUserById(userId);

        ExtendedHandymanResponseDto extendedHandymanResponseDto = mapper.map(userDto, ExtendedHandymanResponseDto.class);
        Handyman handyman = getHandymanFromRepository(userId);

        mapper.map(handyman, extendedHandymanResponseDto);
        return extendedHandymanResponseDto;
    }

    /**
     * Method gets user data from landscape service by DTO from response and save handyman entity in repository
     * @param creatingHandymanDto - DTO from request
     * @return - DTO of created handyman with data from landscape service
     */
    public HandymanResponseDto createHandyman(CreatingHandymanDto creatingHandymanDto) {
        CreatingUserDto creatingUserDto = mapper.map(creatingHandymanDto, CreatingUserDto.class);
        creatingUserDto.setUserTypeId(1);

        UserDto userDto = userClient.createUser(creatingUserDto);

        LocationDto locationDto = mapper.map(creatingHandymanDto, LocationDto.class);

        locationClient.createLocation(userDto.getId(),locationDto);

        Handyman creatingHandyman = mapper.map(creatingHandymanDto, Handyman.class);
        creatingHandyman.setId(userDto.getId());

        creatingHandyman = handymanRepository.save(creatingHandyman);

        return mapper.map(creatingHandyman, HandymanResponseDto.class);
    }

    /**
     * Delete handyman in repository but do not delete him in landscape servcie
     * @param userId - id of user in landscape service
     * @return - DTO of Handyman with extended data from landscape service
     */
    public ExtendedHandymanResponseDto deleteHandymanById(UUID userId) {
        ExtendedHandymanResponseDto responseDto = getHandymanById(userId);
        handymanRepository.deleteById(userId);
        return responseDto;
    }

    /**
     * Update handyman in repository
     * @param userId
     * @param handymanResponseDto
     * @return
     */
    public HandymanResponseDto updateHandymanById(UUID userId, HandymanResponseDto handymanResponseDto) {
        Handyman updatableHandyman = getHandymanFromRepository(userId);
        Handyman outsideHandyman = mapper.map(handymanResponseDto, Handyman.class);
        mapper.map(outsideHandyman, updatableHandyman);

        updatableHandyman = handymanRepository.save(updatableHandyman);

        return mapper.map(updatableHandyman, HandymanResponseDto.class);
    }

    /**
     *
     * @param userId
     * @param worksDto
     * @return
     */
    public HandymanResponseDto addHandymanWork(UUID userId, WorksDto worksDto) {
        HandymanResponseDto worksForHandymanResponseDto = mapper.map(worksDto, HandymanResponseDto.class);
        return updateHandymanById(userId, worksForHandymanResponseDto);
    }
}
