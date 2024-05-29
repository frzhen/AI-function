package guru.ysy.aifunc.models;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/26 15:31
 * @Email: fred.zhen@gmail.com
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonClassDescription("Weather API Request")
public record WeatherRequest(
        @JsonProperty(required = true, value="location")
        @JsonPropertyDescription("The city and state e.g. San Francisco, CA")
        String location,

        @JsonProperty( required = false, value="state")
        @JsonPropertyDescription("Optional State for US cities only. Use full name of State")
        String state,

        @JsonProperty(required = false, value="country")
        @JsonPropertyDescription("Optional Country name")
        String country
        ) {
}
