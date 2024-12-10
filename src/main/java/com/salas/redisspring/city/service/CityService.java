package com.salas.redisspring.city.service;

import com.salas.redisspring.city.client.CityClient;
import com.salas.redisspring.city.dto.City;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityClient client;

    private RMapCacheReactive<String, City> cityMap;
    @Autowired
    private CityClient cityClient;

    public CityService(RedissonReactiveClient client) {
        cityMap = client.getMapCache("city", new TypedJsonJacksonCodec(String.class, City.class));
    }

    // get from cash, if empty go to service, get -> put to cache -> and the get
    public Mono<City> getCity(String zip) {
        return cityClient.getCity(zip)
                .onErrorResume(ex -> this.cityClient.getCity(zip));
    }


    @Scheduled(fixedRate = 10_000)
    public void updateCity() {
        this.cityClient.getCites()
                .collectList()
                .map(cities -> cities.stream().collect(Collectors.toMap(City::getZip, Function.identity())))
                .flatMap(cityMap::putAll)
                .subscribe();
    }
}
