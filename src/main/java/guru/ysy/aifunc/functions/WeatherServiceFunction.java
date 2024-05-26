package guru.ysy.aifunc.functions;

import guru.ysy.aifunc.models.WeatherRequest;
import guru.ysy.aifunc.models.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/26 15:47
 * @Email: fred.zhen@gmail.com
 */
@Slf4j
public class WeatherServiceFunction implements Function<WeatherRequest, WeatherResponse> {

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    @Value("${weather.api.key}")
    private String weatherApiKey;

    @Override
    public WeatherResponse apply(WeatherRequest request) {
        RestClient restClient = RestClient.builder()
                .baseUrl(weatherApiUrl)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set("X-Api-Key", weatherApiKey);
                    httpHeaders.set("Content-Type", "application/json");
                    httpHeaders.set("Accept", "application/json");
                }).build();

        return restClient.get().uri(uriBuilder -> {
            log.info("Building URI for weather request: {}", request);
            uriBuilder.queryParam("city", request.city());

            if (request.state() != null && !request.state().isBlank()) {
                uriBuilder.queryParam("state", request.state());
            }

            if (request.country() != null && !request.country().isBlank()) {
                uriBuilder.queryParam("country", request.country());
            }
            return uriBuilder.build();
        }).retrieve().body(WeatherResponse.class);
    }
}
