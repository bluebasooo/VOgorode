package ru.tinkoff.handyman.controller;


import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.handyman.dto.WorksDto;
import ru.tinkoff.handyman.dto.request.CreatingHandymanDto;
import ru.tinkoff.handyman.dto.response.ExtendedHandymanResponseDto;
import ru.tinkoff.handyman.dto.response.HandymanResponseDto;
import ru.tinkoff.handyman.model.Handyman;
import ru.tinkoff.handyman.service.HandymanService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/handyman")
public class HandymanController {

    private final HandymanService handymanService;

    /**
     *
     * @param userId - id of the user in landscape service
     * @return handyman with extended data from landscape service
     */
    @Timed
    @GetMapping("/getById/{userId}")
    public ExtendedHandymanResponseDto getById(@PathVariable UUID userId) {
        return handymanService.getHandymanById(userId);
    }

    /**
     *
     * @return all handyman from repository
     */
    @Timed
    @GetMapping("/all")
    public List<Handyman> getAll() {
        return handymanService.getAllHandyman();
    }


    /**
     *
     * @param creatingHandymanDto - DTO from request
     * @return DTO of created handyman with creating account in landscape service
     */
    @Timed
    @PostMapping("/create")
    public HandymanResponseDto createHandyman(@RequestBody CreatingHandymanDto creatingHandymanDto) {
        return handymanService.createHandyman(creatingHandymanDto);
    }

    /**
     *
     * @param userId - id of user in landscape service
     * @param handymanResponseDto - DTO from request
     * @return - DTO of updated handyman without extended data from landscape service
     */
    @Timed
    @PostMapping("/updateById/{userId}")
    public HandymanResponseDto updateById(@PathVariable UUID userId, @RequestBody HandymanResponseDto handymanResponseDto) {
        return handymanService.updateHandymanById(userId, handymanResponseDto);
    }

    /**
     *
     * @param userId
     * @param worksDto
     * @return
     */
    @Timed
    @PostMapping("/addWorkById/{userId}")
    public HandymanResponseDto addWorkById(@PathVariable UUID userId, @RequestBody WorksDto worksDto) {
        return handymanService.addHandymanWork(userId, worksDto);
    }

    /**
     *
     * @param userId - id of user in landscape service
     * @return - DTO of deleted user with extended data from landscape service
     */
    @Timed
    @DeleteMapping("/deleteById/{userId}")
    public ExtendedHandymanResponseDto deleteById(@PathVariable UUID userId) {
        return handymanService.deleteHandymanById(userId);
    }

    /**
     *
     * @param id - id of user in landscape service
     * @return - handyman entity from HandymanRepository
     */
    @Timed
    @GetMapping("/getFromRepo/{id}")
    public Handyman getFromRepo(@PathVariable UUID id) {
        return handymanService.getHandymanFromRepository(id);
    }

}
