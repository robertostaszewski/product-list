package com.products.Products.list.service;

import com.products.Products.list.repository.ProductListRepository;
import com.products.Products.list.repository.ProductRepository;

import java.util.function.Consumer;

public class OnComplete<T> implements Command<T> {
    private final Command<T> command;
    private final Consumer<T> onComplete;

    public OnComplete(Command<T> command, Consumer<T> onComplete) {
        this.command = command;
        this.onComplete = onComplete;
    }

    @Override
    public T execute(ProductListRepository productListRepository, ProductRepository productRepository) {
        T t = command.execute(productListRepository, productRepository);
        onComplete.accept(t);
        return t;
    }
}
