package com.company.service.rest;

import com.company.dto.rest.weather.WeatherDTO;
import com.company.exception.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherRestService {

    private final RestTemplate restTemplate;

    @Value("${message.weather.url}")
    private String url;

    @Value("${message.weather.token}")
    private String token;

    @Value("${message.weather.location.path}")
    private String locationPath;


    public WeatherDTO currentWeatherInfo(String location, String email) {

        ResponseEntity<WeatherDTO> response;

        log.info("Request Preparation For Sending, Current Profile email={} location={}", email, location);

        try {
            response = restTemplate.getForEntity(url + token + locationPath + location, WeatherDTO.class);
        } catch (HttpClientErrorException e) {
            log.warn("Location Not Found email={} location={}", email, location);
            throw new ItemNotFoundException("Location Not Found!");
        }

        /*if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
            log.warn("Bad Request email={} location={}", email, location);
            throw new AppBadRequestException("Please Try Again Later, There's Something Wrong!");
        }*/

        return response.getBody();
    }

}
