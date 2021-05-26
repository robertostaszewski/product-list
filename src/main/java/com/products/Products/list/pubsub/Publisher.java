package com.products.Products.list.pubsub;

public interface Publisher {
    void ping();

    void register(Subscriber subscriber);

    void remove(Subscriber subscriber);
}
