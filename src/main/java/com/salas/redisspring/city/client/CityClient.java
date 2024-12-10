package com.salas.redisspring.city.client;

import com.salas.redisspring.city.dto.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CityClient {
    private final WebClient webClient;

    public CityClient(@Value("${city.service.url}") String url) {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<City> getCity(String zipcode) {
        Mono<City> cityMono = this.webClient.get()
                .uri("{zipcode}", zipcode)
                .retrieve()
                .bodyToMono(City.class);
        return cityMono;
    }

    public Flux<City> getCites() {
        return this.webClient.get()
                .retrieve().bodyToFlux(City.class);
    }
}
