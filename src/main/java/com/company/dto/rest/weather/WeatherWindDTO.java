package com.company.dto.rest.weather;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherWindDTO {

    private Double speed;

    private Double deg;

    private Double gust;

}
