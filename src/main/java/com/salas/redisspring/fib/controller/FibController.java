package com.salas.redisspring.fib.controller;

import com.salas.redisspring.fib.service.FibService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fib")
public class FibController {

    @Autowired
    private FibService service;

    @GetMapping("/{index}")
    public Mono<Integer> fetFib(@PathVariable int index) {
       return Mono.fromSupplier(() -> service.getFib(index));
    }

    @GetMapping("/{index}/clear")
    public Mono<Void> clear(@PathVariable int index) {
        return Mono.fromRunnable(() -> service.cashEvict(index));
    }
}
