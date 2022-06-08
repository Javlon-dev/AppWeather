package com.company.dto.rest.weather;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherDTO {

    private Long id;

    private String name;

    private WeatherInfoDTO[] weather;

    private WeatherMainDTO main;

    private WeatherWindDTO wind;

    private WeatherCoordDTO coord;

    private WeatherCloudsDTO clouds;

    private String base;

    private String cod;

    private Long timezone;

    private String message;

}
