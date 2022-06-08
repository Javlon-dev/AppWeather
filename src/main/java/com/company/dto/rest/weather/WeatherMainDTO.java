package com.company.dto.rest.weather;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherMainDTO {

    private Double temp;

    private Double feels_like;

    private Double pressure;

    private Double humidity;

    private Double sea_level;

    private Double grnd_level;

}
