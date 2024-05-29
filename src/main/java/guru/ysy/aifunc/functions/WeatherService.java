package guru.ysy.aifunc.functions;

import guru.ysy.aifunc.models.WeatherRequest;
import guru.ysy.aifunc.models.WeatherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.function.Function;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/26 15:47
 * @Email: fred.zhen@gmail.com
 */
@Slf4j
@RequiredArgsConstructor
public class WeatherService implements Function<WeatherRequest, WeatherResponse> {

    public static final String WEATHER_URL = "https://api.api-ninjas.com/v1/weather";

    private final String weatherApiKey;

    @Override
    public WeatherResponse apply(WeatherRequest request) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Api-Key", weatherApiKey);

        log.info("Building URI for weather request: {}", request);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(WEATHER_URL)
                .queryParam("city", request.location());
        if (request.state() != null && !request.state().isBlank()) {
                uriBuilder.queryParam("state", request.state());
        }

        if (request.country() != null && !request.country().isBlank()) {
            uriBuilder.queryParam("country", request.country());
        }
        URI uri = uriBuilder.build().toUri();

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                WeatherResponse.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Weather API request failed: " + response.getStatusCode());
        }
    }
}
