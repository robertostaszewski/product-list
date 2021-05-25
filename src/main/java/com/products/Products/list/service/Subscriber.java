package com.products.Products.list.service;

public interface Subscriber {
    void onEvent();

    void subscribe(Publisher publisher);
}
