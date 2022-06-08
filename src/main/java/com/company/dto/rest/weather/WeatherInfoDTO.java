package com.company.dto.rest.weather;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherInfoDTO {

    private Long id;

    private String main;

    private String description;

}
