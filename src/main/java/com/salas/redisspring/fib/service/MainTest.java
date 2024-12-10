package com.salas.redisspring.fib.service;

import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MainTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Thread thread = new Thread(() -> {
            new MyTask().run();
        });

        CompletableFuture<String> call = new MyTaskCallable().call();


        Thread thread2 = new Thread(() -> {
            new MyTaskCallable().call()
                    .thenApply(s -> s)
                    .exceptionally(ex -> "default")
                    .thenAccept(System.out::println);
        });


//        thread.start();
//        thread.join();

        thread2.start();
        thread2.join();
        System.out.println(" from main method " + Thread.currentThread().getName());
        Thread.sleep(2000);
    }


    static class MyTask implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " from here");
        }
    }

    static class MyTaskCallable {
        @SneakyThrows
        public CompletableFuture<String> call() {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                throw new RuntimeException();
            });
        }
    }
}
