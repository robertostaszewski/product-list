package com.products.Products.list.service;

import com.products.Products.list.repository.ProductListRepository;
import com.products.Products.list.repository.ProductRepository;

import java.util.function.Consumer;

public interface Command<T> {
    T execute(ProductListRepository productListRepository, ProductRepository productRepository);

    default Command<T> andThen(Consumer<T> consumer) {
        return new OnComplete<>(this, consumer);
    }
}
