package com.products.Products.list.service;

public interface ListService {

    <T> T execute(Command<T> command);
}
