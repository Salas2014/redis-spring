package com.salas.redisspring.fib.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FibService {

    @Cacheable(value = "fib:cal", key = "#index")
    public int getFib(int index) {
        System.out.println("calculating fib for " + index);
        return fib(index);
    }

    @CacheEvict(value = "fib:cal", key = "#index")
    public void cashEvict(int index) {
        System.out.println("evict for index " + index);
    }

    @Scheduled(fixedDelay = 10_000)
    @CacheEvict(value = "fib:cal", allEntries = true)
    public void allCashEvict() {
        System.out.println("all cash have been evicted");
    }

    private int fib(int index) {
        if (index < 2)
            return index;
        return fib(index - 1) + fib(index - 2);
    }
}
