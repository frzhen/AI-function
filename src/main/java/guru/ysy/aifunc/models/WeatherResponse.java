package guru.ysy.aifunc.models;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.math.BigDecimal;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/26 15:38
 * @Email: fred.zhen@gmail.com
 */
public record WeatherResponse(
        @JsonPropertyDescription("WindSpeed in KMH")
        BigDecimal windSpeed,

        @JsonPropertyDescription("Direction of Wind")
        Integer windDegree,

        @JsonPropertyDescription("Current Temperature in Celsius")
        Integer temp,

        @JsonPropertyDescription("Current Humidity")
        Integer humidity,

        @JsonPropertyDescription("Epoch time of sunset GMT ")
        Integer sunset,

        @JsonPropertyDescription("Epoch time of sunrise GMT ")
        Integer sunrise,

        @JsonPropertyDescription("Low Temperature in Celsius")
        Integer minTemp,

        @JsonPropertyDescription("Cloud Coverage Percentage")
        Integer cloudPct,

        @JsonPropertyDescription("Temperature feels like in Celsius")
        Integer feelsLike,

        @JsonPropertyDescription("High Temperature in Celsius")
        Integer maxTemp
) {
}
