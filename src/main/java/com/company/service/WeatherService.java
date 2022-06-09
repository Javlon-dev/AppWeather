package com.company.service;

import com.company.config.details.EntityDetails;
import com.company.dto.rest.WeatherDTO;
import com.company.entity.ProfileEntity;
import com.company.service.rest.WeatherRestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRestService weatherRestService;


    public WeatherDTO getCurrentWeather(String location) {
        ProfileEntity entity = EntityDetails.getProfile();
        return weatherRestService.currentWeatherInfo(location, entity.getEmail());
    }

}
