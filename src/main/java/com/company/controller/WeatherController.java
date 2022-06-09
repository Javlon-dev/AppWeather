package com.company.controller;

import com.company.dto.rest.WeatherDTO;
import com.company.service.WeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/weather")
@RequiredArgsConstructor
@Api(tags = "Weather")
public class WeatherController {

    private final WeatherService weatherService;


    /**
     * ANY
     */

    @ApiOperation(value = "Current Weather Info", notes = "Method used to show current weather information (for ANY)",
            authorizations = @Authorization(value = "JWT token"))
    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    @GetMapping("")
    public ResponseEntity<WeatherDTO> getCurrentWeather(@RequestParam(value = "location") String location) {
        log.info("Current Weather Info location={}", location);
        return ResponseEntity.ok(weatherService.getCurrentWeather(location));
    }

}
