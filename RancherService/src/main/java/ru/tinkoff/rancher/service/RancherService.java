package ru.tinkoff.rancher.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.rancher.api.LandscapeLocationClient;
import ru.tinkoff.rancher.api.LandscapeUserClient;
import ru.tinkoff.rancher.dto.WorksDto;
import ru.tinkoff.rancher.dto.request.*;
import ru.tinkoff.rancher.dto.response.ExtendedRancherResponseDto;
import ru.tinkoff.rancher.dto.response.RancherResponseDto;
import ru.tinkoff.rancher.exeption.ResourseNotFoundException;
import ru.tinkoff.rancher.model.Rancher;
import ru.tinkoff.rancher.repository.RancherRepository;

import java.util.List;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class RancherService {
    private final RancherRepository rancherRepository;
    private final ModelMapper mapper;
    private final LandscapeUserClient userClient;
    private final LandscapeLocationClient locationClient;

    /**
     *
     * @return - list of all ranchers in repository
     */
    public List<Rancher> getAllRancher() {
        return rancherRepository.findAll();
    }

    /**
     *
     * @param userId - ranchers id
     * @return - rancher entity from repository
     */
    public Rancher getRancherFromRepository(UUID userId) {
        return rancherRepository.findById(userId).orElseThrow(
                () -> new ResourseNotFoundException("Rancher with id: " + userId + " not found")
        );
    }

    /**
     * Method gets data from landscape service and return it with data from repository in DTO
     * @param userId - id of user in landscape service
     * @return - DTO with extended data from landscape service
     */
    public ExtendedRancherResponseDto getRancherById(UUID userId) {
        ExtendedUserDto userDto = userClient.getExtendedUserById(userId);

        ExtendedRancherResponseDto extendedRancherResponseDto = mapper.map(userDto, ExtendedRancherResponseDto.class);
        Rancher rancher = getRancherFromRepository(userId);

        mapper.map(rancher, extendedRancherResponseDto);
        return extendedRancherResponseDto;
    }

    /**
     * Method creates user in landscape service and use data from response to create rancher
     * @param createingRancherDto - DTO with data to create rancher , also has data to create user in landscape service
     * @return - DTO with data about created rancher
     */
    public RancherResponseDto createRancher(CreatingRancherDto createingRancherDto) {
        CreatingUserDto creatingUserDto = mapper.map(createingRancherDto, CreatingUserDto.class);
        creatingUserDto.setUserTypeId(2);

        UserDto userDto = userClient.createUser(creatingUserDto);

        LocationDto locationDto = mapper.map(createingRancherDto, LocationDto.class);

        locationClient.createLocation(userDto.getId(),locationDto);

        Rancher creatingRancher = mapper.map(createingRancherDto, Rancher.class);
        creatingRancher.setId(userDto.getId());

        creatingRancher = rancherRepository.save(creatingRancher);

        return mapper.map(creatingRancher, RancherResponseDto.class);
    }

    /**
     * Method deletes rancher from repository and gets data from landscape service about user to get it back in DTO
     * @param userId - id of user in landscape service
     * @return - DTO with extended data from landscape service of created rancher
     */
    public ExtendedRancherResponseDto deleteRancherById(UUID userId) {
        ExtendedRancherResponseDto responseDto = getRancherById(userId);
        rancherRepository.deleteById(userId);
        return responseDto;
    }

    /**
     *
     * @param userId - id of user in landscape service
     * @param rancherResponseDto
     * @return
     */
    public RancherResponseDto updateRancherById(UUID userId, RancherResponseDto rancherResponseDto) {
        Rancher updatableRancher = getRancherFromRepository(userId);
        Rancher outsideRancher = mapper.map(rancherResponseDto, Rancher.class);
        mapper.map(outsideRancher, updatableRancher);

        updatableRancher = rancherRepository.save(updatableRancher);

        return mapper.map(updatableRancher, RancherResponseDto.class);
    }

    public RancherResponseDto addRancherWork(UUID userId, WorksDto worksDto) {
        RancherResponseDto worksOnRancherResponseDto = mapper.map(worksDto, RancherResponseDto.class);
        return updateRancherById(userId, worksOnRancherResponseDto);
    }
}
