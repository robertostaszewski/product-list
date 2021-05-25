package com.products.Products.list.service;

public interface Publisher {
    void ping();

    void register(Subscriber subscriber);

    void remove(Subscriber subscriber);
}
