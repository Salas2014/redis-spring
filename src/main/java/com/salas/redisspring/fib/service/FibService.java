package com.salas.redisspring.fib.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class FibService {

    @Cacheable(value = "fib:cal", key = "#index")
    public int getFib(int index, String name) {
        System.out.println("calculating fib for " + index);
        return fib(index);
    }

    private int fib(int index){
        if (index < 2)
            return index;
        return fib(index -1) + fib(index -2);
    }
}
