package com.products.Products.list.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

public class SseSubscriber implements Subscriber {
    private final SseEmitter sseEmitter;

    public SseSubscriber(SseEmitter sseEmitter) {
        this.sseEmitter = sseEmitter;
    }

    @Override
    public void onEvent() {
        try {
            sseEmitter.send("ping");
        } catch (IOException e) {
            e.printStackTrace();
            sseEmitter.completeWithError(e);
        }
    }

    @Override
    public void subscribe(Publisher publisher) {
        publisher.register(this);
        sseEmitter.onCompletion(() -> {
            System.out.println("remove" + this);
            publisher.remove(this);
        });
    }
}
