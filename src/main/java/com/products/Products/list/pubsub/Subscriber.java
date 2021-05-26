package com.products.Products.list.pubsub;

public interface Subscriber {
    void onEvent();

    void subscribe(Publisher publisher);
}
