package ru.tinkoff.rancher.controller;


import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.rancher.dto.WorksDto;
import ru.tinkoff.rancher.dto.request.CreatingRancherDto;
import ru.tinkoff.rancher.dto.response.ExtendedRancherResponseDto;
import ru.tinkoff.rancher.dto.response.RancherResponseDto;
import ru.tinkoff.rancher.model.Rancher;
import ru.tinkoff.rancher.service.RancherService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/rancher")
public class RancherController {

    private final RancherService rancherService;

    /**
     *
     * @param userId - id of user in landscape service
     * @return - DTO of rancher with extended data from landscape service
     */
    @Timed
    @GetMapping("/getById/{userId}")
    public ExtendedRancherResponseDto getById(@PathVariable UUID userId) {
        return rancherService.getRancherById(userId);
    }

    /**
     *
     * @return all rancher from repository
     */
    @Timed
    @GetMapping("/all")
    public List<Rancher> getAll() {
        return rancherService.getAllRancher();
    }

    /**
     *
     * @param creatingRancherDto - DTO with data to creating rancher
     * @return - DTO with data about created rancher
     */
    @Timed
    @PostMapping("/create")
    public RancherResponseDto createRancher(@RequestBody CreatingRancherDto creatingRancherDto) {
        return rancherService.createRancher(creatingRancherDto);
    }

    /**
     *
     * @param userId - id of user in landscape service
     * @param rancherResponseDto - DTO with data to update rancher
     * @return - DTO with data about updated rancher
     */
    @Timed
    @PostMapping("/updateById/{userId}")
    public RancherResponseDto updateById(@PathVariable UUID userId, @RequestBody RancherResponseDto rancherResponseDto) {
        return rancherService.updateRancherById(userId, rancherResponseDto);
    }

    /**
     *
     * @param userId - id of user in landscape service
     * @param worksDto
     * @return
     */
    @Timed
    @PostMapping("/addWorkById/{userId}")
    public RancherResponseDto addWorkById(@PathVariable UUID userId, @RequestBody WorksDto worksDto) {
        return rancherService.addRancherWork(userId, worksDto);
    }

    /**
     *
     * @param userId - id of user in landscape service
     * @return - DTO about deleted rancher with extended data about user from landscape service
     */
    @Timed
    @DeleteMapping("/deleteById/{userId}")
    public ExtendedRancherResponseDto deleteById(@PathVariable UUID userId) {
        return rancherService.deleteRancherById(userId);
    }

    /**
     *
     * @param id - id of user in landscape service
     * @return - rancher entity from RancherRepository
     */
    @Timed
    @GetMapping("/getFromRepo/{id}")
    public Rancher getFromRepo(@PathVariable UUID id) {
        return rancherService.getRancherFromRepository(id);
    }

}
